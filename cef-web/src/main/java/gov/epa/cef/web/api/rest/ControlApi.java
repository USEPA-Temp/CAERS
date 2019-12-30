package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.ControlPollutantRepository;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ControlService;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.ControlPollutantDto;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * Create a control
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<ControlDto> createControl(@NotNull @RequestBody ControlDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());
    	
    	ControlDto result = controlService.create(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
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
    
    /**
     * Update a control by id
     * @param controlId
     * @param dto
     * @return
     */
    @PutMapping(value = "/{controlId}")
    public ResponseEntity<ControlDto> updateControl(
    		@NotNull @PathVariable Long controlId, @NotNull @RequestBody ControlDto dto) {
    	
    		this.securityService.facilityEnforcer().enforceEntity(controlId, ControlRepository.class);
    	
    		ControlDto result = controlService.update(dto.withId(controlId));
    		
    		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Delete a Control for given id
     * @param controlId
     * @return
     */
    @DeleteMapping(value = "/{controlId}")
    public void deleteControl(@NotNull @PathVariable Long controlId) {
    	
    	this.securityService.facilityEnforcer().enforceEntity(controlId, ControlRepository.class);
    	
    	controlService.delete(controlId);
    }
    
    /**
     * Create a Control Pollutant
     * @param dto
     * @return
     */
    @PostMapping(value = "/pollutant/")
    public ResponseEntity<ControlPollutantDto> createControlPollutant(@NotNull @RequestBody ControlPollutantDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());
    	
    	ControlPollutantDto result = controlService.createPollutant(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Update a Control Point by id
     * @param controlPointId
     * @param dto
     * @return
     */
    @PutMapping(value = "/pollutant/{controlPollutantId}")
    public ResponseEntity<ControlPollutantDto> updateControlPollutant(
    		@NotNull @PathVariable Long controlPollutantId, @NotNull @RequestBody ControlPollutantDto dto) {
    	
        	this.securityService.facilityEnforcer().enforceEntity(controlPollutantId, ControlPollutantRepository.class);
    	
    		ControlPollutantDto result = controlService.updateControlPollutant(dto.withId(controlPollutantId));
    		
    		return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
