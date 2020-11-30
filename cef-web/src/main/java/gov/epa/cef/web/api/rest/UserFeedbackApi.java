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
     * Retrieve all feedback for selected year and program system code
     * @return
     */
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
    @GetMapping(value = "/byYearAndProgramSystemCode")
    @ResponseBody
    public ResponseEntity<List<UserFeedbackDto>> retrieveAllByYearAndProgramSystem(@NotNull @RequestParam(value = "year") Short year, 
            @NotNull @RequestParam(value = "programSystemCode") String programSystemCode) {

    	List<UserFeedbackDto> result =userFeedbackService.retrieveAllByYearAndProgramSystem(year, programSystemCode);
        return new ResponseEntity<List<UserFeedbackDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve stats for selected year and program system code
     * @return
     */
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
    @GetMapping(value = "/stats/byYearAndProgramSystemCode")
    @ResponseBody
    public ResponseEntity<IUserFeedbackStatsDto> retrieveStatsByYearAndProgramSystem(@NotNull @RequestParam(value = "year") Short year, 
            @NotNull @RequestParam(value = "programSystemCode") String programSystemCode) {
    	
    	IUserFeedbackStatsDto result = userFeedbackService.retrieveStatsByYearAndProgramSystem(year, programSystemCode);
        return new ResponseEntity<IUserFeedbackStatsDto>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve available year and program system codes
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
     * Retrieve available year and program system codes
     * @return
     */
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
    @GetMapping(value = "/programSystemCodes")
    @ResponseBody
    public ResponseEntity<List<String>> retrieveAvailableProgramSystems() {
    	
    	List<String> result =userFeedbackService.retrieveAvailableProgramSystems();
        return new ResponseEntity<List<String>>(result, HttpStatus.OK);
    }
    
}