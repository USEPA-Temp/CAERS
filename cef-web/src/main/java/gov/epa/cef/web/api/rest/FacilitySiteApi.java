package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.FacilityNAICSXrefRepository;

import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilityNAICSDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
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

/**
 * API for retrieving facility site information related to reports.
 * @author tfesperm
 *
 */
@RestController
@RequestMapping("/api/facilitySite")
public class FacilitySiteApi {

    private final FacilitySiteService facilityService;

    private final SecurityService securityService;

    @Autowired
    FacilitySiteApi(SecurityService securityService,
                    FacilitySiteService facilityService) {

        this.securityService = securityService;
        this.facilityService = facilityService;
    }
    
    /**
     * Update an existing facility site by ID
     * @param facilitySiteId
     * @param dto
     * @return
     */
    @PutMapping(value = "/{facilitySiteId}")
    public ResponseEntity<FacilitySiteDto> updateFacilitySite(
    		@NotNull @PathVariable Long facilitySiteId, @NotNull @RequestBody FacilitySiteDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceEntity(facilitySiteId, FacilitySiteRepository.class);
    	
    	FacilitySiteDto result = facilityService.update(dto.withId(facilitySiteId));
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve a facility site by ID
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/{facilitySiteId}")
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySite(@NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        FacilitySiteDto  facilitySiteDto= facilityService.findById(facilitySiteId);
        return new ResponseEntity<>(facilitySiteDto, HttpStatus.OK);
    }

    /**
     * Retrieve a facility site by eis program ID and report ID
     * @param reportId
     * @param eisProgramId
     * @return
     */
    @GetMapping(value = "/report/{reportId}/facility/{eisProgramId}")
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySiteByProgramIdAndReportId(
        @NotNull @PathVariable Long reportId, @NotNull @PathVariable String eisProgramId) {

        this.securityService.facilityEnforcer().enforceProgramId(eisProgramId);

        FacilitySiteDto result = facilityService.findByEisProgramIdAndReportId(eisProgramId, reportId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Create a Facility NAICS
     * @param dto
     * @return
     */
    @PostMapping(value = "/naics/")
    public ResponseEntity<FacilityNAICSDto> createFacilityNAICS(
    		@NotNull @RequestBody FacilityNAICSDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());
    	
    	FacilityNAICSDto result = facilityService.createNaics(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
   /**
    * Update a Facility NAICS
    * @param facilityNaicsId
    * @param dto
    * @return
    */
   @PutMapping(value = "/naics/{facilityNaicsId}")
   public ResponseEntity<FacilityNAICSDto> updateFacilityNAICS(
   		@NotNull @PathVariable Long facilityNaicsId, @NotNull @RequestBody FacilityNAICSDto dto) {
   	
	   	this.securityService.facilityEnforcer().enforceEntity(facilityNaicsId, FacilityNAICSXrefRepository.class);
	   	
	   	FacilityNAICSDto result = facilityService.updateNaics(dto.withId(facilityNaicsId));
	   	
	   	return new ResponseEntity<>(result, HttpStatus.OK);
   }
    
    /**
     * Delete a Facility NAICS for a given ID
     * @param facilityNaicsId
     * @return
     */
    @DeleteMapping(value = "/naics/{facilityNaicsId}")
    public void deleteFacilityNAICS(@PathVariable Long facilityNaicsId) {
    	
    	this.securityService.facilityEnforcer().enforceEntity(facilityNaicsId, FacilityNAICSXrefRepository.class);
    	
    	facilityService.deleteFacilityNaics(facilityNaicsId);
    }
}
