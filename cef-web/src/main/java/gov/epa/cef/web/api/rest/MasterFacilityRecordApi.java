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

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;
import gov.epa.cef.web.service.MasterFacilityRecordService;
import gov.epa.cef.web.service.LookupService;

@RestController
@RequestMapping("/api/masterFacility")
public class MasterFacilityRecordApi {

    private final MasterFacilityRecordService mfrService;

    private final SecurityService securityService;

    private final LookupService lookupService;

    @Autowired
    MasterFacilityRecordApi(SecurityService securityService,
            MasterFacilityRecordService mfrService,
            LookupService lookupService) {

        this.securityService = securityService;
        this.mfrService = mfrService;
        this.lookupService = lookupService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MasterFacilityRecordDto> retrieveRecord(@NotNull @PathVariable Long id) {

        MasterFacilityRecordDto result = this.mfrService.findById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/program/{programSystemCode}")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public ResponseEntity<List<MasterFacilityRecordDto>> retrieveRecordsForProgram(
        @NotNull @PathVariable String programSystemCode) {

        List<MasterFacilityRecordDto> result =
            this.mfrService.findByProgramSystemCode(programSystemCode);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<MasterFacilityRecordDto>> search(@RequestBody MasterFacilityRecordDto criteria) {
        
        List<MasterFacilityRecordDto> result = this.mfrService.findByExample(criteria);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/program/my")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public ResponseEntity<List<MasterFacilityRecordDto>> retrieveRecordsForCurrentProgram() {

        List<MasterFacilityRecordDto> result =
            this.mfrService.findByProgramSystemCode(this.securityService.getCurrentProgramSystemCode());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update an existing master facility record
     * @param masterFacilityRecordId
     * @param dto
     * @return
     */
    @PutMapping(value = "/{masterFacilityRecordId}")
    public ResponseEntity<MasterFacilityRecordDto> updateMasterFacilityRecord(
    		@NotNull @PathVariable Long masterFacilityRecordId, @NotNull @RequestBody MasterFacilityRecordDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceEntity(masterFacilityRecordId, MasterFacilityRecordRepository.class);
    	
    	MasterFacilityRecordDto result = mfrService.update(dto.withId(masterFacilityRecordId));
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Create a new master facility record
     * @param dto
     * @return
     */
    @PostMapping(value = "/create")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public ResponseEntity<MasterFacilityRecordDto> createMasterFacilityRecord(@NotNull @RequestBody MasterFacilityRecordDto dto) {

    	MasterFacilityRecordDto result = mfrService.create(dto);
    	return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping(value = "/programSystemCodes")
    public ResponseEntity<List<CodeLookupDto>> retrieveProgramSystemCodes() {

        List<CodeLookupDto> result = this.mfrService.findDistinctProgramSystems();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    @GetMapping(value = "/userProgramSystemCode")
    public ResponseEntity<ProgramSystemCode> retrieveProgramSystemCodeForCurrentUser() {
        ProgramSystemCode result = this.lookupService.retrieveProgramSystemTypeCodeEntityByCode(this.securityService.getCurrentProgramSystemCode());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/isDuplicateAgencyId/{agencyFacilityId}/{programSystemCode}")
    public ResponseEntity<Boolean> isDuplicateAgencyId(@NotNull @PathVariable String agencyFacilityId, @NotNull @PathVariable String programSystemCode) {

        Boolean result = this.mfrService.isDuplicateAgencyId(agencyFacilityId, programSystemCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
