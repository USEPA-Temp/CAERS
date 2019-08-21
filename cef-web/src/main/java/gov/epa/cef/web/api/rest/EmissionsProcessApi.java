package gov.epa.cef.web.api.rest;

import java.util.Collection;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.EmissionsProcessService;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;
import gov.epa.cef.web.service.dto.EmissionsProcessSaveDto;

@RestController
@RequestMapping("/api/emissionsProcess")
public class EmissionsProcessApi {

    @Autowired
    private EmissionsProcessService processService;

    /**
     * Create a new Emissions Process
     * @param dto
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<EmissionsProcessDto> createEmissionsProcess(@RequestBody EmissionsProcessSaveDto dto) {

        EmissionsProcessDto result = processService.create(dto);

        return new ResponseEntity<EmissionsProcessDto>(result, HttpStatus.OK);
    }

    /**
     * Update an Emissions Process
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<EmissionsProcessDto> updateEmissionsProcess(@PathVariable Long id, @RequestBody EmissionsProcessSaveDto dto) {

        EmissionsProcessDto result = processService.update(dto);

        return new ResponseEntity<EmissionsProcessDto>(result, HttpStatus.OK);
    }

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
     * @param releasePointId
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
    
    /**
     * Delete an Emissions Processes for given id
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void deleteEmissionsProcess(@PathVariable Long id) {
        processService.delete(id);
    }
    
}
