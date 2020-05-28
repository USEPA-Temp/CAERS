package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.service.dto.ReportDownloadDto;
import gov.epa.cef.web.service.dto.ReportHistoryDto;
import gov.epa.cef.web.service.dto.ReportSummaryDto;

public interface ReportService {

    /***
     * Return list of report summary records with total emissions summed per pollutant for the chosen facility and year
     * @param reportYear
     * @param facilitySiteId
     * @return
     */
    List<ReportSummaryDto> findByReportYearAndFacilitySiteId(Short reportYear, Long facilitySiteId);
    
    /***
     * Return list of report history records for the chosen report id
     * @param reportId
     * @return
     */
    List<ReportHistoryDto> findByEmissionsReportId(Long reportId);
    
    /**
     * Create Report History record 
     * @param userIds
     * @param reportAction
     * @param comments
     * @param reportAttachment
     */
    void createReportHistory(List<Long> reportIds, ReportAction reportAction, String comments, ReportAttachment reportAttachment);
    
    /**
     * Create Report History records for specified rejected reports
     * @param reportIds
     * @param reportAction
     * @param comments
     * @param attachmentId
     */
    void createRejectReportHistory(List<Long> reportIds, ReportAction reportAction, String comments, Long attachmentIds);
    
    /**
     * Create Report History record 
     * @param userIds
     * @param reportAction
     * @param comments
     */
    void createReportHistory(List<Long> reportIds, ReportAction reportAction, String comments);
    
    /**
     * Create Report History record 
     * @param userIds
     * @param reportAction
     */
    void createReportHistory(List<Long> reportIds, ReportAction reportAction);
    
    /**
     * Create Report History record
     * @param reportId
     * @param reportAction
     * @param comments
     * @param reportAttachment
     */
    void createReportHistory(Long reportId, ReportAction reportAction, String comments, ReportAttachment reportAttachment);

    /**
     * Create Report History record 
     * @param userId
     * @param reportAction
     * @param comments
     */
    void createReportHistory(Long reportId, ReportAction reportAction, String comments);
    
    /**
     * Create Report History record 
     * @param userId
     * @param reportAction
     */
    void createReportHistory(Long reportId, ReportAction reportAction);
    
    /**
     * Update Report History record to indicate attachment was deleted
     * @param id
     * @param deleted
     */
    void updateReportHistoryDeletedAttachment(Long id, boolean deleted);
    
    /***
     * Return ReportDownloadDto for the chosen report id
     * @param reportId
     * @return
     */
    List<ReportDownloadDto> retrieveReportDownloadDtoByReportId(Long reportId);

}