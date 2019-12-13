package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.ReleasePointApptRepository;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.dto.ReleasePointApptDto;
import gov.epa.cef.web.service.dto.ReleasePointDto;
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
@RequestMapping("/api/releasePoint")
public class ReleasePointApi {

    private final ReleasePointService releasePointService;

    private final SecurityService securityService;

    @Autowired
    ReleasePointApi(SecurityService securityService,
                    ReleasePointService releasePointService) {

        this.releasePointService = releasePointService;
        this.securityService = securityService;
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
    	
    	ReleasePointApptDto result = releasePointService.createAppt(dto);
    	
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
