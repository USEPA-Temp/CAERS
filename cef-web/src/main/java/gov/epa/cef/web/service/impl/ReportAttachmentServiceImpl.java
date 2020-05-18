package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.domain.ReportHistory;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.service.ReportAttachmentService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;
import gov.epa.cef.web.service.mapper.ReportAttachmentMapper;
import gov.epa.cef.web.service.mapper.ReportHistoryMapper;
import gov.epa.cef.web.service.mapper.ReportSummaryMapper;
import gov.epa.cef.web.util.TempFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import javax.transaction.Transactional;

@Service
@Transactional
public class ReportAttachmentServiceImpl implements ReportAttachmentService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportAttachmentRepository reportAttachmentsRepo;
    
    @Autowired
    private ReportHistoryRepository reportHistoryRepo;

    @Autowired
    ReportSummaryMapper reportSummaryMapper;

    @Autowired
    ReportHistoryMapper reportHistoryMapper;
    
    @Autowired
    ReportAttachmentMapper reportAttachmentMapper;
    
    @Autowired
    private ReportService reportService;
    
    
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
    public ReportAttachmentDto saveAttachment(TempFile file, String comments, ReportAttachmentDto metadata) {
    	ReportAttachment attachment = reportAttachmentMapper.fromDto(metadata);
    	attachment.getEmissionsReport().setId(metadata.getReportId());
    	
    	 if (file != null) {

             try {

            	 attachment.setAttachment(file.createBlob());

             } catch (IOException e) {

                 throw new IllegalStateException(e);
             }
         }
    	
		ReportAttachment result = reportAttachmentsRepo.save(attachment);
		
		reportService.createReportHistory(attachment.getEmissionsReport().getId(), ReportAction.ATTACHMENT, comments, result);
		
		return reportAttachmentMapper.toDto(result);

    }
    
    /**
     * Delete report attachment record for a given id
     * @param id
     */
    public void deleteAttachment(Long id) {
    	ReportAttachment attachment = reportAttachmentsRepo.findById(id).orElse(null);
    	
    	String comment = "\"" + attachment.getFileName() + "\" was been deleted.";
    	
    	reportService.createReportHistory(attachment.getEmissionsReport().getId(), ReportAction.ATTACHMENT_DELETED, comment);
    	ReportHistory history = reportHistoryRepo.findByAttachmentId(attachment.getId());
    	
    	reportService.updateReportHistoryAttachment(history.getId(), true);
        reportAttachmentsRepo.deleteById(id);
    }
}
