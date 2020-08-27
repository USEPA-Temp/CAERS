package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.UserFeedbackDto;
import gov.epa.cef.web.service.dto.UserFeedbackStatsDto;

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
    UserFeedbackStatsDto.FeedbackStats retrieveStatsByYearAndAgency(Short year, String agency);
    
    /**
     * Retrieve available year and agency codes
     * @return
     */
    UserFeedbackStatsDto retrieveAvailableStats();
		
}
