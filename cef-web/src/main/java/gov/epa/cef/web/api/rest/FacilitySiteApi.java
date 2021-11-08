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
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.FacilityNAICSDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilityNAICSBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.util.CsvBuilder;
import gov.epa.cef.web.util.WebUtils;

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

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
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
    
    private UserService userService;

    @Autowired
    FacilitySiteApi(SecurityService securityService,
                    FacilitySiteService facilityService,
                    UserService userService) {

        this.securityService = securityService;
        this.facilityService = facilityService;
        this.userService = userService;
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
    

    /***
     * Retrieve a CSV of all of the facilities based on the reviewer's program system code and the given inventory year
     * @param year
     * @return
     */
    @GetMapping(value = "/list/csv/{year}")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public void getSltFacilities(@PathVariable Short year, HttpServletResponse response) {

        UserDto user = userService.getCurrentUser();
        String programSystemCode = user.getProgramSystemCode();

        List<Long> facilityIds = facilityService.getFacilityIds(programSystemCode, year);
        this.securityService.facilityEnforcer().enforceFacilitySites(facilityIds);

    	List<FacilitySiteBulkUploadDto> csvRows = facilityService.retrieveFacilities(programSystemCode, year);
    	CsvBuilder<FacilitySiteBulkUploadDto> csvBuilder = new CsvBuilder<FacilitySiteBulkUploadDto>(FacilitySiteBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the facilities based on the given program system code and inventory year
     * @param programSystemCode 
     * @param year
     * @return
     */
    @GetMapping(value = "/list/csv/{programSystemCode}/{year}")
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN, AppRole.ROLE_ADMIN})
    public void getSltFacilities(@PathVariable String programSystemCode, @PathVariable Short year, HttpServletResponse response) {

    	List<FacilitySiteBulkUploadDto> csvRows = facilityService.retrieveFacilities(programSystemCode, year);
    	CsvBuilder<FacilitySiteBulkUploadDto> csvBuilder = new CsvBuilder<FacilitySiteBulkUploadDto>(FacilitySiteBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the facility's NAICS codes based on the reviewer's program system code and the given inventory year
     * @param year
     * @return
     */
    @GetMapping(value = "/naics/list/csv/{year}")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public void getSltFacilityNaicsCodes(@PathVariable Short year, HttpServletResponse response) {

        UserDto user = userService.getCurrentUser();
        String programSystemCode = user.getProgramSystemCode();

        List<Long> facilityIds = facilityService.getFacilityIds(programSystemCode, year);
        this.securityService.facilityEnforcer().enforceFacilitySites(facilityIds);

    	List<FacilityNAICSBulkUploadDto> csvRows = facilityService.retrieveFacilityNaics(programSystemCode, year);
    	CsvBuilder<FacilityNAICSBulkUploadDto> csvBuilder = new CsvBuilder<FacilityNAICSBulkUploadDto>(FacilityNAICSBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the facility's NAICS codes based on the given program system code and inventory year
     * @param programSystemCode 
     * @param year
     * @return
     */
    @GetMapping(value = "/naics/list/csv/{programSystemCode}/{year}")
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN, AppRole.ROLE_ADMIN})
    public void getSltFacilityNaicsCodes(@PathVariable String programSystemCode, @PathVariable Short year, HttpServletResponse response) {

    	List<FacilityNAICSBulkUploadDto> csvRows = facilityService.retrieveFacilityNaics(programSystemCode, year);
    	CsvBuilder<FacilityNAICSBulkUploadDto> csvBuilder = new CsvBuilder<FacilityNAICSBulkUploadDto>(FacilityNAICSBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
}
