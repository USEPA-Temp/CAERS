package gov.epa.cef.web.api.rest;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.domain.facility.Facility;
import gov.epa.cef.web.security.ApplicationSecurityUtils;
import gov.epa.cef.web.service.RegistrationService;
import gov.epa.cef.web.service.FacilityService;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@RestController
@RequestMapping("/api/facility")
public class FacilityApi {

    private static final Logger logger = LoggerFactory.getLogger(FacilityApi.class);

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private ApplicationSecurityUtils applicationSecurityUtils;

    /**
     * Retrieve a facility by program ID
     * @param programId
     * @return
     */
    @GetMapping(value = "/{programId}")
    @ResponseBody
    public ResponseEntity<ProgramFacility> retrieveFacility(@PathVariable String programId) {

        ProgramFacility result = registrationService.retrieveFacilityByProgramId(programId);

        return new ResponseEntity<ProgramFacility>(result, HttpStatus.OK);
    }

    /**
     * Retrieve the specified user's facilities.
     * @param userRoleId
     * @return
     */
    @GetMapping(value = "/user/{userRoleId}")
    @ResponseBody
    public ResponseEntity<Collection<ProgramFacility>> retrieveFacilitiesForUser(@PathVariable Long userRoleId) {

        Collection<ProgramFacility> result = registrationService.retrieveFacilities(userRoleId);

        return new ResponseEntity<Collection<ProgramFacility>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve the currently authenticated user's facilities.
     * This can be called before the user is loaded into the app.
     * @return
     */
    @GetMapping(value = "/user/my")
    @ResponseBody
    public ResponseEntity<Collection<ProgramFacility>> retrieveFacilitiesForCurrentUser() {

        Collection<ProgramFacility> result = registrationService.retrieveFacilities(applicationSecurityUtils.getCurrentApplicationUser().getUserRoleId());

        return new ResponseEntity<Collection<ProgramFacility>>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/state/{stateCode}")
    @ResponseBody
    public ResponseEntity<Collection<Facility>> retrieveFacilitiesByState(@PathVariable String stateCode) {
        Collection<Facility> result = facilityService.findByState(stateCode);
        return new ResponseEntity<Collection<Facility>>(result, HttpStatus.OK);		
    }
}
