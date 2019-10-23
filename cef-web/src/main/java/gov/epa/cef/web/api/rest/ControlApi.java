package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ControlService;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/control")
public class ControlApi {

    private final ControlService controlService;

    private final SecurityService securityService;

    @Autowired
    public ControlApi(SecurityService securityService, ControlService controlService) {

        this.controlService = controlService;
        this.securityService = securityService;
    }

    /**
     * Retrieve a control by id
     * @param controlId
     * @return
     */
    @GetMapping(value = "/{controlId}")
    @ResponseBody
    public ResponseEntity<ControlPostOrderDto> retrieveControl(@PathVariable Long controlId) {

        ControlPostOrderDto result = controlService.retrieveById(controlId);

        this.securityService.facilityEnforcer().enforce(Collections.singletonList(result));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facilitySite/{facilitySiteId}")
    @ResponseBody
    public ResponseEntity<List<ControlPostOrderDto>> retrieveControlsForFacilitySite(@PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforce(facilitySiteId);

        List<ControlPostOrderDto> result = controlService.retrieveForFacilitySite(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/components/{controlId}")
    @ResponseBody
    public ResponseEntity<List<EmissionsReportItemDto>> retrieveControlComponents(@PathVariable Long controlId) {

        this.securityService.facilityEnforcer().enforce(controlService.retrieveById(controlId));

    	List<EmissionsReportItemDto> result = controlService.retrieveControlComponents(controlId);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
