package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.client.soap.VirusScanClient;
import gov.epa.cef.web.exception.VirusScanException;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ReportAttachmentService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;
import gov.epa.cef.web.util.TempFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.IOException;

@RestController
@RequestMapping("/api/reportAttachments")
public class ReportAttachmentApi {

    private final ReportAttachmentService reportAttachmentService;

    private final SecurityService securityService;
    
    private final VirusScanClient virusScanClient;
    
    Logger LOGGER = LoggerFactory.getLogger(ReportAttachmentApi.class);

    @Autowired
    ReportAttachmentApi( SecurityService securityService,
    		ReportService reportService,
    		ReportAttachmentService reportAttachmentService,
    		VirusScanClient virusScanClient) {

    	this.reportAttachmentService = reportAttachmentService;
        this.securityService = securityService;
        this.virusScanClient = virusScanClient;
    }
    

    @GetMapping(value = "/facilitySiteId/{facilitySiteId}/{id}")
    public ResponseEntity<StreamingResponseBody> downloadAttachment(
    		@NotNull @PathVariable Long facilitySiteId,
    		@NotNull @PathVariable Long id) {
    	
    	this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);
    	
    	ReportAttachmentDto result = reportAttachmentService.findAttachmentById(id);
    	
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(result.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.getFileName() + "\"")
                .body(outputStream -> {
                		reportAttachmentService.writeFileTo(id, outputStream);
                });
    }
    
    
    /**
     * Save a report attachment for the chosen report
     * @param facilitySiteId
     * @param reportId
     * @param file
     * @param dto
     * @return
     */
    @PostMapping(value = "/facilitySiteId/{facilitySiteId}/uploadAttachment")
    public ResponseEntity<ReportAttachmentDto> uploadAttachment(
	    @NotNull @PathVariable Long facilitySiteId,
	    @NotBlank @RequestPart("file") MultipartFile file,
	    @NotNull @RequestPart("metadata") ReportAttachmentDto dto) {

    	this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);
    	
    	ReportAttachmentDto result = null;
    	HttpStatus status = HttpStatus.NO_CONTENT;
    	
    	try (TempFile tempFile = TempFile.from(file.getInputStream(), file.getOriginalFilename())) {

            LOGGER.debug("Attachment filename {}", tempFile.getFileName());
            LOGGER.debug("ReportAttachmentsDto {}", dto);

            this.virusScanClient.scanFile(tempFile);
            
            String.format("%s %s",
            		securityService.getCurrentApplicationUser().getFirstName(),
            		securityService.getCurrentApplicationUser().getLastName());
            
            dto.setFileName(file.getOriginalFilename());
            dto.setFileType(file.getContentType());
            System.out.println(dto.getReportId());
            dto.setReportId(dto.getReportId());
            dto.setAttachment(tempFile);
                        
            result = reportAttachmentService.saveAttachment(tempFile, dto.getComments(), dto);

            status = HttpStatus.OK;

        } catch (VirusScanException e) {

            String.format("The uploaded file, '%s', is suspected of containing a threat " +
                    "such as a virus or malware and was deleted. The scanner responded with: '%s'.",
                file.getOriginalFilename(), e.getMessage());

        } catch (IOException e) {

            throw new IllegalStateException(e);
        }
    	
    	return new ResponseEntity<>(result, status);
    	
    }
    
    
    /**
     * Delete a report attachment record for given id
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void deleteAttachment(@PathVariable Long id) {

        this.securityService.facilityEnforcer().enforceEntity(id, ReportAttachmentRepository.class);

        reportAttachmentService.deleteAttachment(id);
    }
}
