/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.EmissionsReportRepository;
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
     * Retrieve a facility site by report ID
     * @param reportId
     * @return
     */
    @GetMapping(value = "/report/{reportId}")
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySiteByReportId(
        @NotNull @PathVariable Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        FacilitySiteDto result = facilityService.findByReportId(reportId);

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
