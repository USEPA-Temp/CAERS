package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.domain.AttachmentMIMEType;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.domain.ReportHistory;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.exception.ReportAttachmentValidationException;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.service.ReportAttachmentService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;
import gov.epa.cef.web.service.dto.bulkUpload.WorksheetError;
import gov.epa.cef.web.service.mapper.ReportAttachmentMapper;
import gov.epa.cef.web.service.mapper.ReportHistoryMapper;
import gov.epa.cef.web.service.mapper.ReportSummaryMapper;
import gov.epa.cef.web.util.TempFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Collections;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
@Transactional
public class ReportAttachmentServiceImpl implements ReportAttachmentService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportAttachmentRepository reportAttachmentsRepo;
    
    @Autowired
    private ReportHistoryRepository reportHistoryRepo;

    @Autowired
    private ReportSummaryMapper reportSummaryMapper;

    @Autowired
    private ReportHistoryMapper reportHistoryMapper;
    
    @Autowired
    private ReportAttachmentMapper reportAttachmentMapper;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private CefConfig cefConfig;
    
    /***
     * Return attachment for the chosen attachment id
     * @param id
     * @return
     */
    public ReportAttachmentDto findAttachmentById(Long id) {
		ReportAttachment attachment = reportAttachmentsRepo.findById(id).orElse(null);
		
		return reportAttachmentMapper.toDto(attachment);
	}
    
    /***
     * Write file to output stream
     * @param id
     * @return
     */    
    public void writeFileTo (Long fileId, OutputStream outputStream) {
    	
    	reportAttachmentsRepo.findById(fileId).ifPresent(file -> {
    		
    		if (file.getAttachment() != null) {
    			try (InputStream inputStream = file.getAttachment().getBinaryStream()) {
    				ByteStreams.copy(inputStream, outputStream);
    				
    			} catch (SQLException | IOException e) {
    				throw new IllegalStateException(e);
    			}
    		}
    	});
		
	}
    
    /**
     * Save a report attachment to the database.
     * @param file
     * @return
     */
    public ReportAttachmentDto saveAttachment(@NotNull TempFile file, @NotNull ReportAttachmentDto metadata) {

    	Preconditions.checkArgument(file != null, "File can not be null");
    	
    	ReportAttachment attachment = reportAttachmentMapper.fromDto(metadata);
    	attachment.getEmissionsReport().setId(metadata.getReportId());

		 try {
		
			 attachment.setAttachment(file.createBlob());
		
		 } catch (IOException e) {
		
		     throw new IllegalStateException(e);
		 }
		 
		 if(file.length() > Long.valueOf(this.cefConfig.getMaxFileSize().replaceAll("[^0-9]", ""))*1048576) {
	         	String msg = String.format("The selected file size exceeds the maximum file upload size %d MB.",
	         			Long.valueOf(this.cefConfig.getMaxFileSize().replaceAll("[^0-9]", "")));

         	throw new ReportAttachmentValidationException(
                     Collections.singletonList(WorksheetError.createSystemError(msg)));
          }
         
         boolean acceptedType = false;
         String fileName = metadata.getFileName();
		 	for (AttachmentMIMEType type : AttachmentMIMEType.values()) {
	            if (type.label().equals(metadata.getFileType())) {
	            	acceptedType = true;
	            	break;
	            }
	         }
	        
	        if (!acceptedType) {
	        	String msg = String.format("The file extension '%s' is not in an accepted file format.",
	        			(fileName.substring(fileName.indexOf('.'), fileName.length())));

	        	throw new ReportAttachmentValidationException(
	                    Collections.singletonList(WorksheetError.createSystemError(msg)));
	        }
		 
		ReportAttachment result = reportAttachmentsRepo.save(attachment);
		
		reportService.createReportHistory(attachment.getEmissionsReport().getId(), ReportAction.ATTACHMENT, attachment.getComments(), result);
		
		return reportAttachmentMapper.toDto(result);

    }
    
    /**
     * Delete report attachment record for a given id
     * @param id
     */
    public void deleteAttachment(Long id) {
    	ReportAttachment attachment = reportAttachmentsRepo.findById(id)
    			.orElseThrow(() -> new NotExistException("Report Attachment", id));
    	
    	String comment = "\"" + attachment.getFileName() + "\" was deleted.";
    	
    	reportService.createReportHistory(attachment.getEmissionsReport().getId(), ReportAction.ATTACHMENT_DELETED, comment);
    	ReportHistory history = reportHistoryRepo.findByAttachmentId(attachment.getId());
    	
    	reportService.updateReportHistoryAttachment(history.getId(), true);
        reportAttachmentsRepo.deleteById(id);
    }
}
