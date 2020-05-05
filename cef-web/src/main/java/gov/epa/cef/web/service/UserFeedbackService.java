package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.UserFeedbackDto;

public interface UserFeedbackService {
	
	/**
     * Create a new User Feedback Submission
     * @param dto
     * @return
     */
	UserFeedbackDto create(UserFeedbackDto dto);
	
    /**
     * Retrieve a feedback submission by report ID
     * @param pointId
     * @return
     */
	UserFeedbackDto retrieveById(Long reportId);
	
}
