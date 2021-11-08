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

import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.EmissionBulkEntryHolderDto;
import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionFormulaVariableBulkUploadDto;
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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/emission")
public class EmissionApi {

    private final EmissionService emissionService;

    private final SecurityService securityService;

    private UserService userService;
    
    private FacilitySiteService facilityService;


    @Autowired
    EmissionApi(SecurityService securityService,
                EmissionService emissionService, 
                UserService userService,
                FacilitySiteService facilityService) {

        this.securityService = securityService;
        this.emissionService = emissionService;
        this.userService = userService;
        this.facilityService = facilityService;
    }

    /**
     * Create a new Emission
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<EmissionDto> createEmission(@NotNull @RequestBody EmissionDto dto) {

        this.securityService.facilityEnforcer()
            .enforceEntity(dto.getReportingPeriodId(), ReportingPeriodRepository.class);

        EmissionDto result = emissionService.create(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve an Emission
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmissionDto> retrieveEmission(@NotNull @PathVariable Long id) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionRepository.class);

        EmissionDto result = emissionService.retrieveById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve an Emission and generate missing variables
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/variables")
    public ResponseEntity<EmissionDto> retrieveWithVariablesById(@NotNull @PathVariable Long id) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionRepository.class);

        EmissionDto result = emissionService.retrieveWithVariablesById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update an existing Emission
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<EmissionDto> updateEmission(
        @NotNull @PathVariable Long id, @NotNull @RequestBody EmissionDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionRepository.class);

        EmissionDto result = emissionService.update(dto.withId(id));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Delete an Emission for given id
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void deleteEmission(@NotNull @PathVariable Long id) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionRepository.class);

        emissionService.delete(id);
    }

    /**
     * Retrieve Reporting Periods for bulk entry by Report Id
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/bulkEntry/{facilitySiteId}")
    public ResponseEntity<Collection<EmissionBulkEntryHolderDto>> retrieveBulkEntryEmissionsForFacilitySite(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceEntity(facilitySiteId, FacilitySiteRepository.class);

        Collection<EmissionBulkEntryHolderDto> result = emissionService.retrieveBulkEntryEmissionsForFacilitySite(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update the total emissions for multiple Emissions at once and recalculate all Emissions for this facility
     * @param dtos
     * @return
     */
    @PutMapping(value = "/bulkEntry/{facilitySiteId}")
    public ResponseEntity<Collection<EmissionBulkEntryHolderDto>> bulkUpdate(
        @NotNull @PathVariable Long facilitySiteId, @RequestBody List<EmissionDto> dtos) {

        this.securityService.facilityEnforcer().enforceEntity(facilitySiteId, FacilitySiteRepository.class);

        List<Long> emissionIds = dtos.stream().map(EmissionDto::getId).collect(Collectors.toList());
        this.securityService.facilityEnforcer().enforceEntities(emissionIds, EmissionRepository.class);

        Collection<EmissionBulkEntryHolderDto> result = emissionService.bulkUpdate(facilitySiteId, dtos);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Calculate total emissions and emission factor
     * @param dto
     * @return
     */
    @PostMapping(value = "/calculate")
    public ResponseEntity<EmissionDto> calculateTotalEmissions(@NotNull @RequestBody EmissionDto dto) {


        EmissionDto result = emissionService.calculateTotalEmissions(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    

    /***
     * Retrieve a CSV of all of the emissions based on the reviewer's program system code and the given inventory year
     * @param year
     * @return
     */
    @GetMapping(value = "/list/csv/{year}")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public void getSltEmissions(@PathVariable Short year, HttpServletResponse response) {

        UserDto user = userService.getCurrentUser();
        String programSystemCode = user.getProgramSystemCode();

        List<Long> facilityIds = facilityService.getFacilityIds(programSystemCode, year);
        this.securityService.facilityEnforcer().enforceFacilitySites(facilityIds);

    	List<EmissionBulkUploadDto> csvRows = emissionService.retrieveEmissions(programSystemCode, year);
    	CsvBuilder<EmissionBulkUploadDto> csvBuilder = new CsvBuilder<EmissionBulkUploadDto>(EmissionBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the emissions based on the given program system code and inventory year
     * @param programSystemCode 
     * @param year
     * @return
     */
    @GetMapping(value = "/list/csv/{programSystemCode}/{year}")
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN, AppRole.ROLE_ADMIN})
    public void getSltEmissions(@PathVariable String programSystemCode, @PathVariable Short year, HttpServletResponse response) {

    	List<EmissionBulkUploadDto> csvRows = emissionService.retrieveEmissions(programSystemCode, year);
    	CsvBuilder<EmissionBulkUploadDto> csvBuilder = new CsvBuilder<EmissionBulkUploadDto>(EmissionBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the emission formula variables based on the reviewer's program system code and the given inventory year
     * @param year
     * @return
     */
    @GetMapping(value = "/formulaVariables/list/csv/{year}")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public void getSltEmissionFormulaVariables(@PathVariable Short year, HttpServletResponse response) {

        UserDto user = userService.getCurrentUser();
        String programSystemCode = user.getProgramSystemCode();

        List<Long> facilityIds = facilityService.getFacilityIds(programSystemCode, year);
        this.securityService.facilityEnforcer().enforceFacilitySites(facilityIds);

    	List<EmissionFormulaVariableBulkUploadDto> csvRows = emissionService.retrieveEmissionFormulaVariables(programSystemCode, year);
    	CsvBuilder<EmissionFormulaVariableBulkUploadDto> csvBuilder = new CsvBuilder<EmissionFormulaVariableBulkUploadDto>(EmissionFormulaVariableBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
    

    /***
     * Retrieve a CSV of all of the emission formula variables based on the given program system code and inventory year
     * @param programSystemCode 
     * @param year
     * @return
     */
    @GetMapping(value = "/formulaVariables/list/csv/{programSystemCode}/{year}")
    @RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN, AppRole.ROLE_ADMIN})
    public void getSltEmissionFormulaVariables(@PathVariable String programSystemCode, @PathVariable Short year, HttpServletResponse response) {

    	List<EmissionFormulaVariableBulkUploadDto> csvRows = emissionService.retrieveEmissionFormulaVariables(programSystemCode, year);
    	CsvBuilder<EmissionFormulaVariableBulkUploadDto> csvBuilder = new CsvBuilder<EmissionFormulaVariableBulkUploadDto>(EmissionFormulaVariableBulkUploadDto.class, csvRows);
    	
    	WebUtils.WriteCsv(response, csvBuilder);
    }
}
