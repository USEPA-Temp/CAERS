package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ControlService;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/control")
public class ControlApi {

    private final ControlService controlService;

    private final SecurityService securityService;

    @Autowired
    ControlApi(ControlService controlService,
               SecurityService securityService) {

        this.controlService = controlService;
        this.securityService = securityService;
    }

    /**
     * Retrieve a control by id
     * @param controlId
     * @return
     */
    @GetMapping(value = "/{controlId}")
    public ResponseEntity<ControlPostOrderDto> retrieveControl(@PathVariable Long controlId) {

        this.securityService.facilityEnforcer().enforceEntity(controlId, ControlRepository.class);

        ControlPostOrderDto result = controlService.retrieveById(controlId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facilitySite/{facilitySiteId}")
    public ResponseEntity<List<ControlPostOrderDto>> retrieveControlsForFacilitySite(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        List<ControlPostOrderDto> result = controlService.retrieveForFacilitySite(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/components/{controlId}")
    public ResponseEntity<List<EmissionsReportItemDto>> retrieveControlComponents(
        @NotNull @PathVariable Long controlId) {

        this.securityService.facilityEnforcer().enforceEntity(controlId, ControlRepository.class);

    	List<EmissionsReportItemDto> result = controlService.retrieveControlComponents(controlId);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
