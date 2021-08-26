/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.client.soap.VirusScanClient;
import gov.epa.cef.web.exception.ReportAttachmentValidationException;
import gov.epa.cef.web.exception.VirusScanException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ReportAttachmentService;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import gov.epa.cef.web.util.TempFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@RestController
@RequestMapping("api/reports/{reportId}/attachments")
public class ReportAttachmentApi {

    private final ReportAttachmentService reportAttachmentService;
    
    private final ReportAttachmentRepository attachmentRepo;
    
    private final EmissionsReportRepository erRepo;

    private final SecurityService securityService;
    
    private final VirusScanClient virusScanClient;
    
    private ObjectMapper objectMapper;
    
    Logger LOGGER = LoggerFactory.getLogger(ReportAttachmentApi.class);

    @Autowired
    ReportAttachmentApi( SecurityService securityService,
    		ReportAttachmentService reportAttachmentService,
    		ReportAttachmentRepository attachmentRepo,
    		EmissionsReportRepository erRepo,
    		VirusScanClient virusScanClient,
    		ObjectMapper objectMapper) {

    	this.reportAttachmentService = reportAttachmentService;
    	this.attachmentRepo = attachmentRepo;
    	this.erRepo = erRepo;
        this.securityService = securityService;
        this.virusScanClient = virusScanClient;
        this.objectMapper = objectMapper;
    }
    

    @GetMapping(value = "/{id}")
    public ResponseEntity<StreamingResponseBody> downloadAttachment(
    		@NotNull @PathVariable Long reportId,
    		@NotNull @PathVariable Long id) {
    	this.securityService.facilityEnforcer().enforceEntity(id, ReportAttachmentRepository.class);
    	
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
     * @param reportId
     * @param file
     * @param dto
     * @return
     */
    @PostMapping(value = "/uploadAttachment")
    public ResponseEntity<ReportAttachmentDto> uploadAttachment(
    	@NotBlank @RequestPart("file") MultipartFile file,
	    @NotNull @RequestPart("metadata") ReportAttachmentDto reportAttachment,
	    @NotNull @PathVariable Long reportId)  {
    	
    	this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);
    	
    	ReportAttachmentDto result = null;
    	HttpStatus status = HttpStatus.NO_CONTENT;
    	
    	try (TempFile tempFile = TempFile.from(file.getInputStream(), file.getOriginalFilename())) {

            LOGGER.debug("Attachment filename {}", tempFile.getFileName());
            LOGGER.debug("ReportAttachmentsDto {}", reportAttachment);

            this.virusScanClient.scanFile(tempFile);
            
            
            String.format("%s %s",
            		securityService.getCurrentApplicationUser().getFirstName(),
            		securityService.getCurrentApplicationUser().getLastName());
            
            Path path = Paths.get(file.getOriginalFilename());
        	reportAttachment.setFileName(path.getFileName().toString());
            reportAttachment.setFileType(file.getContentType());
            reportAttachment.setReportId(reportAttachment.getReportId());
            reportAttachment.setAttachment(tempFile);
            
            result = reportAttachmentService.saveAttachment(tempFile, reportAttachment);

            status = HttpStatus.OK;
            
        } catch (VirusScanException e) {

        	String msg = String.format("The uploaded file, '%s', is suspected of containing a threat " +
                    "such as a virus or malware and was deleted. The scanner responded with: '%s'.",
                file.getOriginalFilename(), e.getMessage());
            
            throw new ReportAttachmentValidationException(
                    Collections.singletonList(WorksheetError.createSystemError(msg)));

        } catch (IOException e) {

            String msg = String.format("There was an issue during file upload. Please try again. If you continue to experience issues, "
                    + "please ensure that your file is not infected with a virus and reach out to the Helpdesk.",
                file.getOriginalFilename(), e.getMessage());
            
            throw new ReportAttachmentValidationException(
                    Collections.singletonList(WorksheetError.createSystemError(msg)));
        }
    	
    	return new ResponseEntity<>(result, status);
    	
    }
    
    @ExceptionHandler(value = ReportAttachmentValidationException.class)
    public ResponseEntity<JsonNode> uploadValidationError(ReportAttachmentValidationException exception) {

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("failed", true);
        ArrayNode arrayNode = objectNode.putArray("errors");
        exception.getErrors().forEach(error -> arrayNode.add(objectMapper.convertValue(error, JsonNode.class)));

        return ResponseEntity.badRequest().body(objectNode);
    }
    
    /**
     * Delete a report attachment record for given id
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{attachmentId}")
    public void deleteAttachment(@NotNull @PathVariable Long reportId, @NotNull @PathVariable Long attachmentId) {
        this.securityService.facilityEnforcer().enforceEntity(attachmentId, ReportAttachmentRepository.class);

        reportAttachmentService.deleteAttachment(attachmentId);
    }
}
