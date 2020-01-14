package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;

public interface SubmissionsReviewDasboardService {
    
    /**
     * Retrieves all the facilities reports for review 
     * 
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsForCurrentUserAgencyForTheCurrentFiscalYear();
    
    /**
     * Retrieves all the facilities reports for review by report status
     * 
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByReportStatus(ReportStatus reportStatus);
    
    /**
     * Retrieves all the facilities reports for review based on reportYear and reportStatus
     * @param reportYear
     * @param reportStatus
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndReportStatus(Short reportYear, ReportStatus reportStatus);
    
    /**
     * Retrieves all the facilities reports for review for current reporting year
     * @param reportYear
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYear(Short reportYear);
    
    
}
