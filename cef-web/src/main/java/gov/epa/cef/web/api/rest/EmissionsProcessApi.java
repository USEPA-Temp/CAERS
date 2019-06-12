package gov.epa.cef.web.api.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.EmissionsProcessService;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;

@RestController
@RequestMapping("/api/emissionsProcess")
public class EmissionsProcessApi {

    @Autowired
    private EmissionsProcessService processService;

    /**
     * Retrieve Emissions Process by id
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<EmissionsProcessDto> retrieveEmissionsProcess(@PathVariable Long id) {

        EmissionsProcessDto result = processService.retrieveById(id);
        return new ResponseEntity<EmissionsProcessDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Emissions Processes for a release point
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/releasePoint/{releasePointId}")
    @ResponseBody
    public ResponseEntity<Collection<EmissionsProcessDto>> retrieveEmissionsProcessesForReleasePoint(@PathVariable Long releasePointId) {

        Collection<EmissionsProcessDto> result = processService.retrieveForReleasePoint(releasePointId);
        return new ResponseEntity<Collection<EmissionsProcessDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Emissions Processes for an emissions unit
     * @param emissionsUnitid
     * @return
     */
    @GetMapping(value = "/emissionsUnit/{emissionsUnitId}")
    @ResponseBody
    public ResponseEntity<Collection<EmissionsProcessDto>> retrieveEmissionsProcessesForEmissionsUnit(@PathVariable Long emissionsUnitId) {

        Collection<EmissionsProcessDto> result = processService.retrieveForEmissionsUnit(emissionsUnitId);
        return new ResponseEntity<Collection<EmissionsProcessDto>>(result, HttpStatus.OK);
    }
    
}
