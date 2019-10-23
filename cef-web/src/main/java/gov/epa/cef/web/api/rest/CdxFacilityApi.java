package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.RegistrationService;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * API for retrieving facility information managed by CDX.
 * @author tfesperm
 *
 */
@RestController
@RequestMapping("/api/facility/cdx")
public class CdxFacilityApi {

    private final RegistrationService registrationService;

    private final SecurityService securityService;

    @Autowired
    CdxFacilityApi(SecurityService securityService, RegistrationService registrationService) {

        this.registrationService = registrationService;
        this.securityService = securityService;
    }

    /**
     * Retrieve a facility by program ID
     * @param programId
     * @return
     */
    @GetMapping(value = "/{programId}")
    @ResponseBody
    public ResponseEntity<ProgramFacility> retrieveFacility(@NotNull @PathVariable String programId) {

        this.securityService.facilityEnforcer().enforceProgramId(programId);

        ProgramFacility result = registrationService.retrieveFacilityByProgramId(programId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve the specified user's facilities.
     * @param userRoleId
     * @return
     */
    @GetMapping(value = "/user/{userRoleId}")
    @ResponseBody
    @RolesAllowed(AppRole.ROLE_REVIEWER)
    public ResponseEntity<Collection<ProgramFacility>> retrieveFacilitiesForUser(@PathVariable Long userRoleId) {

        Collection<ProgramFacility> result = registrationService.retrieveFacilities(userRoleId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve the currently authenticated user's facilities.
     * This can be called before the user is loaded into the app.
     * @return
     */
    @GetMapping(value = "/user/my")
    @ResponseBody
    public ResponseEntity<Collection<ProgramFacility>> retrieveFacilitiesForCurrentUser() {

        Collection<ProgramFacility> result = this.registrationService.retrieveFacilities(
            this.securityService.getCurrentApplicationUser().getUserRoleId());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
