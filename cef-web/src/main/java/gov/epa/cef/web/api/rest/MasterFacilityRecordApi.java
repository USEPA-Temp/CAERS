package gov.epa.cef.web.api.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;
import gov.epa.cef.web.service.MasterFacilityRecordService;

@RestController
@RequestMapping("/api/masterFacility")
public class MasterFacilityRecordApi {

    private final MasterFacilityRecordService mfrService;

    private final SecurityService securityService;

    @Autowired
    MasterFacilityRecordApi(SecurityService securityService,
            MasterFacilityRecordService mfrService) {

        this.securityService = securityService;
        this.mfrService = mfrService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MasterFacilityRecordDto> retrieveRecord(@NotNull @PathVariable Long id) {

        MasterFacilityRecordDto result = this.mfrService.findById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/program/{programSystemCode}")
    public ResponseEntity<List<MasterFacilityRecordDto>> retrieveRecordsForProgram(
        @NotNull @PathVariable String programSystemCode) {

//        this.securityService.facilityEnforcer().enforceProgramId(programSystemCode);

        List<MasterFacilityRecordDto> result =
            this.mfrService.findByProgramSystemCode(programSystemCode);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/my")
    public ResponseEntity<List<MasterFacilityRecordDto>> retrieveRecordsForCurrentUser() {

//        this.securityService.facilityEnforcer().enforceProgramId(programSystemCode);

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

}
