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

import gov.epa.cef.web.repository.ReleasePointApptRepository;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.ReleasePointApptDto;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointApptBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
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

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/releasePoint")
public class ReleasePointApi {

    private final ReleasePointService releasePointService;

    private final SecurityService securityService;

    private UserService userService;

    private final FacilitySiteService facilityService;


    @Autowired
    ReleasePointApi(SecurityService securityService,
                    ReleasePointService releasePointService, 
                    UserService userService,
                    FacilitySiteService facilityService) {

        this.releasePointService = releasePointService;
        this.securityService = securityService;
        this.userService = userService;
        this.facilityService = facilityService;
    }

    /**
     * Create a release point
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<ReleasePointDto> createReleasePoint(
    		@NotNull @RequestBody ReleasePointDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());
    	
    	ReleasePointDto result = releasePointService.create(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve a release point by ID
     * @param pointId
     * @return
     */
    @GetMapping(value = "/{pointId}")
    public ResponseEntity<ReleasePointDto> retrieveReleasePoint(@NotNull @PathVariable Long pointId) {

        this.securityService.facilityEnforcer().enforceEntity(pointId, ReleasePointRepository.class);

        ReleasePointDto result = releasePointService.retrieveById(pointId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve versions of this rp from the last year reported
     * @param pointId
     * @return
     */
    @GetMapping(value = "/{pointId}/previous")
    public ResponseEntity<ReleasePointDto> retrievePreviousReleasePoint(@NotNull @PathVariable Long pointId) {

        this.securityService.facilityEnforcer().enforceEntity(pointId, ReleasePointRepository.class);

        ReleasePointDto result = releasePointService.retrievePreviousById(pointId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Release Points for a facility
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facility/{facilitySiteId}")
    public ResponseEntity<List<ReleasePointDto>> retrieveFacilityReleasePoints(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        List<ReleasePointDto> result = releasePointService.retrieveByFacilitySiteId(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Update an Release Point by ID
     * @param pointId
     * @param dto
     * @return
     */
    @PutMapping(value = "/{pointId}")
    public ResponseEntity<ReleasePointDto> updateReleasePoint(
        @NotNull @PathVariable Long pointId, @NotNull @RequestBody ReleasePointDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(pointId, ReleasePointRepository.class);

        ReleasePointDto result = releasePointService.update(dto.withId(pointId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Delete a Release Point for a given ID
     * @param pointId
     * @return
     */
    @DeleteMapping(value = "/{pointId}")
    public void deleteReleasePoint(@PathVariable Long pointId) {

        this.securityService.facilityEnforcer().enforceEntity(pointId, ReleasePointRepository.class);

        releasePointService.delete(pointId);
    }
    
    /**
     * Delete a Release Point Apportionment for a given ID
     * @param releasePointApptId
     * @return
     */
    @DeleteMapping(value = "/appt/{releasePointApptId}")
    public void deleteReleasePointAppt(@PathVariable Long releasePointApptId) {

        this.securityService.facilityEnforcer().enforceEntity(releasePointApptId, ReleasePointApptRepository.class);

        releasePointService.deleteAppt(releasePointApptId);
    }
    
    /**
     * Create a Release Point Apportionment
     * @param emissionsProcessId
     * @return
     */
    @PostMapping(value = "/appt/")
    public ResponseEntity<ReleasePointApptDto> createReleasePointAppt(
    		@NotNull @RequestBody ReleasePointApptDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());
    	
    	ReleasePointApptDto result = releasePointService.createAppt(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Update an Release Point Apportionment by ID
     * @param apportionmentId
     * @param dto
     * @return
     */
    @PutMapping(value = "/appt/{apportionmentId}")
    public ResponseEntity<ReleasePointApptDto> updateReleasePointAppt(
        @NotNull @PathVariable Long apportionmentId, @NotNull @RequestBody ReleasePointApptDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(apportionmentId, ReleasePointApptRepository.class);

        ReleasePointApptDto result = releasePointService.updateAppt(dto.withId(apportionmentId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    

    /***
     * Retrieve a CSV of all of the release points based on the reviewer's program system code and the given inventory year
     * @param year
     * @return
     */
    @GetMapping(value = "/list/csv/{year}")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public void getSltReleasePoints(@PathVariable Short year, HttpServletResponse response) {

        UserDto user = userService.getCurrentUser();
        String programSystemCode = user.getProgramSystemCode();

        List<Long> facilityIds = facilityService.getFacilityIds(programSystemCode, year);
        this.securityService.facilityEnforcer().enforceFacilitySites(facilityIds);

    	List<ReleasePointBulkUploadDto> csvRows = releasePointService.retrieveReleasePoints(programSystemCode, year);
    	CsvBuilder<ReleasePointBulkUploadDto> csvBuilder = new CsvBuilder<ReleasePointBulkUploadDto>(ReleasePointBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the release points based on the given program system code and inventory year
     * @param programSystemCode 
     * @param year
     * @return
     */
    @GetMapping(value = "/list/csv/{programSystemCode}/{year}")
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN, AppRole.ROLE_ADMIN})
    public void getSltReleasePoints(@PathVariable String programSystemCode, @PathVariable Short year, HttpServletResponse response) {

    	List<ReleasePointBulkUploadDto> csvRows = releasePointService.retrieveReleasePoints(programSystemCode, year);
    	CsvBuilder<ReleasePointBulkUploadDto> csvBuilder = new CsvBuilder<ReleasePointBulkUploadDto>(ReleasePointBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }


    /***
     * Retrieve a CSV of all of the release point apportionments based on the reviewer's program system code and the given inventory year
     * @param year
     * @return
     */
    @GetMapping(value = "/appt/list/csv/{year}")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public void getSltReleasePointApportionments(@PathVariable Short year, HttpServletResponse response) {

        UserDto user = userService.getCurrentUser();
        String programSystemCode = user.getProgramSystemCode();

        List<Long> facilityIds = facilityService.getFacilityIds(programSystemCode, year);
        this.securityService.facilityEnforcer().enforceFacilitySites(facilityIds);

    	List<ReleasePointApptBulkUploadDto> csvRows = releasePointService.retrieveReleasePointAppts(programSystemCode, year);
    	CsvBuilder<ReleasePointApptBulkUploadDto> csvBuilder = new CsvBuilder<ReleasePointApptBulkUploadDto>(ReleasePointApptBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the release point apportionments based on the given program system code and inventory year
     * @param programSystemCode 
     * @param year
     * @return
     */
    @GetMapping(value = "/appt/list/csv/{programSystemCode}/{year}")
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN, AppRole.ROLE_ADMIN})
    public void getSltReleasePointApportionments(@PathVariable String programSystemCode, @PathVariable Short year, HttpServletResponse response) {

    	List<ReleasePointApptBulkUploadDto> csvRows = releasePointService.retrieveReleasePointAppts(programSystemCode, year);
    	CsvBuilder<ReleasePointApptBulkUploadDto> csvBuilder = new CsvBuilder<ReleasePointApptBulkUploadDto>(ReleasePointApptBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
}
