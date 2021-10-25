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
package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.domain.AttachmentMIMEType;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.domain.ReportHistory;
import gov.epa.cef.web.domain.SLTConfigProperty;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.exception.ReportAttachmentValidationException;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.repository.SLTConfigRepository;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.ReportAttachmentService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.UserService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private SLTConfigRepository sltConfigRepo;

    @Autowired
    private ReportSummaryMapper reportSummaryMapper;

    @Autowired
    private ReportHistoryMapper reportHistoryMapper;
    
    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;
    
    @Autowired
    private ReportAttachmentMapper reportAttachmentMapper;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private CefConfig cefConfig;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmissionsReportService emissionsReportService;
    
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
        
        boolean acceptedTypeSlt = false;
    	String programSystemCode = emissionsReportService.findById(metadata.getReportId()).getProgramSystemCode().getCode();
	    String fileType = AttachmentMIMEType.fromLabel(metadata.getFileType()).code().toLowerCase();
	    String fileExtensionString = fileName.substring(fileName.indexOf('.') + 1, fileName.length());
	    final String csv = "csv";
	    List<SLTConfigProperty> permittedUploadTypes = sltConfigRepo.getPermittedReportUploadTypes(programSystemCode);
	    ArrayList<String> permittedUploadTypeExtensions = new ArrayList<String>();
	    
	    // for each type in upload types, add to list, check list
	    for (SLTConfigProperty uploadType : permittedUploadTypes) {
	    	String uploadTypeName = uploadType.getSltPropertyDetails().getName();
	    	// index + 1 to remove the period when comparing extensions
	    	String uploadTypeExtension = uploadTypeName.substring(uploadTypeName.indexOf('.') + 1, uploadTypeName.length());
	    	permittedUploadTypeExtensions.add(uploadTypeExtension);
	    	// statement after OR in place for handling CSV files - txt csv and xlsx csv are interpreted as different but need to be treated the same
	    	if (fileType.equals(uploadTypeExtension) || (permittedUploadTypeExtensions.contains(fileExtensionString) && fileExtensionString.equals(csv))) {
	    		acceptedTypeSlt = true;
	        }
         }
	    
	    if (!acceptedTypeSlt) {
	    	final String joinedPermittedUploadTypes = String.join(", ", permittedUploadTypeExtensions);
        	String msg = String.format("The file extension '%s' is not an accepted file format for your agency. The following file extensions are acceptable for use: %s",
        			(fileName.substring(fileName.indexOf('.'), fileName.length())),
        			 joinedPermittedUploadTypes);

        	throw new ReportAttachmentValidationException(
                    Collections.singletonList(WorksheetError.createSystemError(msg)));
        }
		 
		ReportAttachment result = reportAttachmentsRepo.save(attachment);
		
		if (!this.userService.getCurrentUser().getRole().equalsIgnoreCase("Reviewer")) {
			reportService.createReportHistory(attachment.getEmissionsReport().getId(), ReportAction.ATTACHMENT, attachment.getComments(), result);
		}
		
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
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(attachment.getId()), ReportAttachmentRepository.class);

    	reportService.updateReportHistoryDeletedAttachment(history.getId(), true);
        reportAttachmentsRepo.deleteById(id);
    }
}
