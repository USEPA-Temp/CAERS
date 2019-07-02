package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;

public interface SubmissionsReviewDasboardService {
    
    /**
     * Retrieves all the facilities reports for review 
     * 
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReports();

}
