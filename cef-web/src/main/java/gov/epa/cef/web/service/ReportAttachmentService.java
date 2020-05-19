package gov.epa.cef.web.service;

import java.io.OutputStream;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;
import gov.epa.cef.web.util.TempFile;

public interface ReportAttachmentService {
	
	/***
     * Return attachment for the chosen attachment id
     * @param id
     * @return
     */
	 ReportAttachmentDto findAttachmentById(Long id);
	
	/***
     * Write file to output stream
     * @param id
     * @return
     */
	void writeFileTo(Long id, OutputStream outputStream);
    
    /**
     * Save a report attachment
     * @param dto
     * @return
     */
    ReportAttachmentDto saveAttachment(TempFile file, ReportAttachmentDto metadata);
    
    /**
     * Delete an report attachment record for a given id
     * @param id
     */
    void deleteAttachment(Long id);

}