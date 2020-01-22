package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.ControlPathRepository;
import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ControlPathService;
import gov.epa.cef.web.service.dto.ControlPathDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * Retrieve Control Paths for a facility site
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
    @PreAuthorize("hasPermission(#unitId, 'EmissionsUnitRepository', null)")
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForEmissionsUnit(
        @NotNull @PathVariable Long unitId) {

        this.securityService.facilityEnforcer().enforceEntity(unitId, EmissionsUnitRepository.class);

        List<ControlPathDto> result = controlPathService.retrieveForEmissionsUnit(unitId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for an release point
     * @param pointId
     * @return
     */
    @GetMapping(value = "/releasePoint/{pointId}")
    @PreAuthorize("hasPermission(#unitId, 'ReleasePointRepository', null)")
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForReleasePoint(
        @NotNull @PathVariable Long pointId) {

        List<ControlPathDto> result = controlPathService.retrieveForReleasePoint(pointId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
