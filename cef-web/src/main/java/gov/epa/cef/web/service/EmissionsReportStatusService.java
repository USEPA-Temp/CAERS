package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.repository.ReportIdRetriever;
import gov.epa.cef.web.service.dto.EmissionsReportDto;

public interface EmissionsReportStatusService {

	/**
     * Begin Advanced QA for the specified reports, move from Submitted to Advanced QA
     * @param reportIds
     * @return
     */
    List<EmissionsReportDto> advancedQAEmissionsReports(List<Long> reportIds);
    
    /**
     * Approve the specified reports and move to approved
     * @param reportIds
     * @return
     */
    List<EmissionsReportDto> acceptEmissionsReports(List<Long> reportIds);

    /**
     * Reject the specified reports. Sets report status to in progress and validation status to unvalidated.
     * @param reportIds
     * @return
     */
    List<EmissionsReportDto> rejectEmissionsReports(List<Long> reportIds);

    /**
     * Reset report status. Sets report status to in progress and validation status to unvalidated.
     * @param reportIds
     * @return
     */
    List<EmissionsReportDto> resetEmissionsReport(List<Long> reportIds);

    /**
     * Find the report for an entity using the provided repository class and reset the report status.
     * Sets report status to in progress and validation status to unvalidated.
     * @param entityIds
     * @param repoClazz
     * @return
     */
    <T extends ReportIdRetriever> List<EmissionsReportDto> resetEmissionsReportForEntity(List<Long> entityIds,
            Class<T> repoClazz);

}