package gov.epa.cef.web.api.rest;

import java.util.List; 

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.UserFeedbackService;
import gov.epa.cef.web.service.dto.UserFeedbackDto;


@RestController
@RequestMapping("/api/userFeedback")
public class UserFeedbackApi {

    
    @Autowired
    private UserFeedbackService userFeedbackService;
    
    /**
     * Create a feedback submission
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<UserFeedbackDto> createUserFeedbackSubmission(
    		@NotNull @RequestBody UserFeedbackDto dto) {
    	    	
    	UserFeedbackDto result = userFeedbackService.create(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve a feedback submission by report ID
     * @param pointId
     * @return
     */
    @GetMapping(value = "/{reportId}")
    public ResponseEntity<UserFeedbackDto> retrieveUserFeedback(@NotNull @PathVariable Long reportId) {

    	UserFeedbackDto result = userFeedbackService.retrieveById(reportId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Update a User Feedback by ID
     * @param pointId
     * @param dto
     * @return
     */
    @PutMapping(value = "/{reportId}")
    public ResponseEntity<UserFeedbackDto> updateUserFeedback(
        @NotNull @PathVariable Long reportId, @NotNull @RequestBody UserFeedbackDto dto) {

    	UserFeedbackDto result = userFeedbackService.update(dto.withId(reportId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}