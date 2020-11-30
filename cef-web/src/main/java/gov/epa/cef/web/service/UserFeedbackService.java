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
     * Retrieves all User Feedback for year and program system
     * @return
     */
    List<UserFeedbackDto> retrieveAllByYearAndProgramSystem(Short year, String programSystem);
    
    /**
     * Retrieve stats for selected year and program system code
     * @return
     */
    IUserFeedbackStatsDto retrieveStatsByYearAndProgramSystem(Short year, String programSystem);
    
    /**
     * Retrieve available years
     * @return
     */
    List<Short> retrieveAvailableYears();
    
    /**
     * Retrieve available program system codes
     * @return
     */
    List<String> retrieveAvailableProgramSystems();
		
}
