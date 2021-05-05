package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.repository.ControlPathPollutantRepository;
import gov.epa.cef.web.repository.ControlPathRepository;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ControlPathService;
import gov.epa.cef.web.service.dto.ControlAssignmentDto;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.dto.ControlPathPollutantDto;

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
@RequestMapping("/api/controlPath")
public class ControlPathApi {

    private final ControlPathService controlPathService;

    private final SecurityService securityService;

    @Autowired
    ControlPathApi(SecurityService securityService, ControlPathService controlPathService) {

        this.securityService = securityService;
        this.controlPathService = controlPathService;
    }

    /**
     * Create a control path
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<ControlPathDto> createControlPath(@NotNull @RequestBody ControlPathDto dto) {

    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());

    	ControlPathDto result = controlPathService.create(dto);

    	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for a facility site
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facilitySite/{facilitySiteId}")
    public ResponseEntity<List<ControlPathDto>> retrieveControlPathsForFacilitySite(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        List<ControlPathDto> result = controlPathService.retrieveForFacilitySite(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve a control path by id
     * @param controlPathId
     * @return
     */
    @GetMapping(value = "/{controlPathId}")
    public ResponseEntity<ControlPathDto> retrieveControlPath(@NotNull @PathVariable Long controlPathId) {

        this.securityService.facilityEnforcer().enforceEntity(controlPathId, ControlPathRepository.class);

    	ControlPathDto result = controlPathService.retrieveById(controlPathId);

        return new ResponseEntity<ControlPathDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for a process
     * @param processId
     * @return
     */
    @GetMapping(value = "/process/{processId}")
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForEmissionsProcess(
        @NotNull @PathVariable Long processId) {

        this.securityService.facilityEnforcer().enforceEntity(processId, EmissionsProcessRepository.class);

        List<ControlPathDto> result = controlPathService.retrieveForEmissionsProcess(processId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Check if a control path was previously reported
     * @param controlPathId
     * @return
     */
    @GetMapping(value = "/{controlPathId}/reported")
    public ResponseEntity<Boolean> isPathPreviouslyReported(@NotNull @PathVariable Long controlPathId) {

        this.securityService.facilityEnforcer().enforceEntity(controlPathId, ControlPathRepository.class);

        Boolean result = controlPathService.isPathPreviouslyReported(controlPathId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Delete a Control Path for given id
     * @param controlPathId
     * @return
     */
    @DeleteMapping(value = "/{controlPathId}")
    public void deleteControlPath(@NotNull @PathVariable Long controlPathId) {

    	this.securityService.facilityEnforcer().enforceEntity(controlPathId, ControlPathRepository.class);

    	controlPathService.delete(controlPathId);
    }

    /**
     * Update a control path by id
     * @param controlPathId
     * @param dto
     * @return
     */
    @PutMapping(value = "/{controlPathId}")
    public ResponseEntity<ControlPathDto> updateControlPath(
    		@NotNull @PathVariable Long controlPathId, @NotNull @RequestBody ControlPathDto dto) {

    		this.securityService.facilityEnforcer().enforceEntity(controlPathId, ControlPathRepository.class);

    		ControlPathDto result = controlPathService.update(dto.withId(controlPathId));

    		return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for an emissions unit
     * @param unitId
     * @return
     */
    @GetMapping(value = "/unit/{unitId}")
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForEmissionsUnit(
        @NotNull @PathVariable Long unitId) {

        this.securityService.facilityEnforcer().enforceEntity(unitId, EmissionsUnitRepository.class);

        List<ControlPathDto> result = controlPathService.retrieveForEmissionsUnit(unitId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for a control device
     * @param pointId
     * @return
     */
    @GetMapping(value = "/controlDevice/{deviceId}")
    public ResponseEntity<List<ControlPathDto>> retrieveControlPathsForControlDevice(
        @NotNull @PathVariable Long deviceId) {

        this.securityService.facilityEnforcer().enforceEntity(deviceId, ControlRepository.class);;

        List<ControlPathDto> result = controlPathService.retrieveForControlDevice(deviceId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for an release point
     * @param pointId
     * @return
     */
    @GetMapping(value = "/releasePoint/{pointId}")
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForReleasePoint(
        @NotNull @PathVariable Long pointId) {

        this.securityService.facilityEnforcer().enforceEntity(pointId, ReleasePointRepository.class);;

        List<ControlPathDto> result = controlPathService.retrieveForReleasePoint(pointId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Create a Control Path Assignment
     * @param controlPathAssignment
     * @return
     */
    @PostMapping(value = "/controlAssignment/")
    public ResponseEntity<ControlAssignmentDto> createControlPathAssignment(
    		@NotNull @RequestBody ControlAssignmentDto dto) {

    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());

    	ControlAssignmentDto result = controlPathService.createAssignment(dto);

    	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Path Assignments for a Control Path
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/controlAssignment/{controlPathId}")
    public ResponseEntity<List<ControlAssignmentDto>> retrieveControlPathAssignmentsForControlPath(
        @NotNull @PathVariable Long controlPathId) {

        this.securityService.facilityEnforcer().enforceEntity(controlPathId, ControlPathRepository.class);

        List<ControlAssignmentDto> result = controlPathService.retrieveForControlPath(controlPathId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve parent control path for child control path
     * @param controlPathId
     * @return
     */
    @GetMapping(value = "/paretControlAssignment/{controlPathId}")
    public ResponseEntity<List<ControlAssignmentDto>> retrieveParentAssignmentsForControlPathChild(@NotNull @PathVariable Long controlPathId) {

        this.securityService.facilityEnforcer().enforceEntity(controlPathId, ControlPathRepository.class);

        List<ControlAssignmentDto> result = controlPathService.retrieveParentPathById(controlPathId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Delete a Control Path Assignment for given id
     * @param controlPathId
     * @return
     */
    @DeleteMapping(value = "/controlAssignment/{controlPathAssignmentId}")
    public void deleteControlPathAssignment(@NotNull @PathVariable Long controlPathAssignmentId) {

    	this.securityService.facilityEnforcer().enforceEntity(controlPathAssignmentId, ControlAssignmentRepository.class);

    	controlPathService.deleteAssignment(controlPathAssignmentId);
    }

    /**
     * Update a control path assignment by id
     * @param controlPathAssignmentId
     * @param dto
     * @return
     */
    @PutMapping(value = "/controlAssignment/{controlPathAssignmentId}")
    public ResponseEntity<ControlAssignmentDto> updateControlPathAssignment(
    		@NotNull @PathVariable Long controlPathAssignmentId, @NotNull @RequestBody ControlAssignmentDto dto) {

    		this.securityService.facilityEnforcer().enforceEntity(controlPathAssignmentId, ControlAssignmentRepository.class);

    		ControlAssignmentDto result = controlPathService.updateAssignment(dto.withId(controlPathAssignmentId));

    		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Create a Control Path Pollutant
     * @param dto
     * @return
     */
    @PostMapping(value = "/pollutant/")
    public ResponseEntity<ControlPathPollutantDto> createControlPathPollutant(@NotNull @RequestBody ControlPathPollutantDto dto) {
    	
    	this.securityService.facilityEnforcer().enforceFacilitySite(dto.getFacilitySiteId());
    	
    	ControlPathPollutantDto result = controlPathService.createPollutant(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Update a Control Path Pollutant by id
     * @param controlPathPollutantId
     * @param dto
     * @return
     */
    @PutMapping(value = "/pollutant/{controlPathPollutantId}")
    public ResponseEntity<ControlPathPollutantDto> updateControlPathPollutant(
    		@NotNull @PathVariable Long controlPathPollutantId, @NotNull @RequestBody ControlPathPollutantDto dto) {
    	
        	this.securityService.facilityEnforcer().enforceEntity(controlPathPollutantId, ControlPathPollutantRepository.class);
    	
    		ControlPathPollutantDto result = controlPathService.updateControlPathPollutant(dto.withId(controlPathPollutantId));
    		
    		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Delete a Control Path Pollutant for given id
     * @param controlPathPollutantId
     * @return
     */
    @DeleteMapping(value = "/pollutant/{controlPathPollutantId}")
    public void deleteControlPathPollutant(@NotNull @PathVariable Long controlPathPollutantId) {
    	
    	this.securityService.facilityEnforcer().enforceEntity(controlPathPollutantId, ControlPathPollutantRepository.class);
    	
    	controlPathService.deleteControlPathPollutant(controlPathPollutantId);
    }
}
