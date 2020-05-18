package gov.epa.cef.web.service;

import java.io.OutputStream;
import java.util.List;

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
    
    /***
     * Return list of report attachment records for the chosen report id
     * @param reportId
     * @return
     */
//    List<ReportAttachmentDto> findAttachmentsByEmissionsReportId(Long reportId);
    
    /**
     * Save a report attachment
     * @param dto
     * @return
     */
    ReportAttachmentDto saveAttachment(TempFile file, String comments, ReportAttachmentDto metadata);
    
    /**
     * Delete an report attachment record for a given id
     * @param id
     */
    void deleteAttachment(Long id);

}