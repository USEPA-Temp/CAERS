package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.UserFeedbackDto;
import gov.epa.cef.web.service.dto.IUserFeedbackStatsDto;

public interface UserFeedbackService {
	
	/**
     * Create a new User Feedback Submission
     * @param dto
     * @return
     */
	UserFeedbackDto create(UserFeedbackDto dto);
	
	void removeReportFromUserFeedback(Long reportId);
	
    /**
     * Retrieves all User Feedback for year and agency
     * @return
     */
    List<UserFeedbackDto> retrieveAllByYearAndAgency(Short year, String agency);
    
    /**
     * Retrieve stats for selected year and agency code
     * @return
     */
    IUserFeedbackStatsDto retrieveStatsByYearAndAgency(Short year, String agency);
    
    /**
     * Retrieve available years
     * @return
     */
    List<Short> retrieveAvailableYears();
    
    /**
     * Retrieve available agency codes
     * @return
     */
    List<String> retrieveAvailableAgencies();
		
}
