package gov.epa.cef.web.api.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.service.UserFeedbackService;
import gov.epa.cef.web.service.dto.UserFeedbackDto;
import gov.epa.cef.web.service.dto.IUserFeedbackStatsDto;


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
    public ResponseEntity<UserFeedbackDto> createUserFeedbackSubmission(@NotNull @RequestBody UserFeedbackDto dto) {
    	    	
    	UserFeedbackDto result = userFeedbackService.create(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve all feedback for selected year and agency code
     * @return
     */
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
    @GetMapping(value = "/byYearAndAgency")
    @ResponseBody
    public ResponseEntity<List<UserFeedbackDto>> retrieveAllByYearAndAgency(@NotNull @RequestParam(value = "year") Short year, @NotNull @RequestParam(value = "agency") String agency) {

    	List<UserFeedbackDto> result =userFeedbackService.retrieveAllByYearAndAgency(year, agency);
        return new ResponseEntity<List<UserFeedbackDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve stats for selected year and agency code
     * @return
     */
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
    @GetMapping(value = "/stats/byYearAndAgency")
    @ResponseBody
    public ResponseEntity<IUserFeedbackStatsDto> retrieveStatsByYearAndAgency(@NotNull @RequestParam(value = "year") Short year, @NotNull @RequestParam(value = "agency") String agency) {
    	
    	IUserFeedbackStatsDto result = userFeedbackService.retrieveStatsByYearAndAgency(year, agency);
        return new ResponseEntity<IUserFeedbackStatsDto>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve available year and agency codes
     * @return
     */
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
    @GetMapping(value = "/years")
    @ResponseBody
    public ResponseEntity<List<Short>> retrieveAvailableYears() {
    	
    	List<Short> result =userFeedbackService.retrieveAvailableYears();
        return new ResponseEntity<List<Short>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve available year and agency codes
     * @return
     */
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
    @GetMapping(value = "/agencies")
    @ResponseBody
    public ResponseEntity<List<String>> retrieveAvailableAgencies() {
    	
    	List<String> result =userFeedbackService.retrieveAvailableAgencies();
        return new ResponseEntity<List<String>>(result, HttpStatus.OK);
    }
    
}