package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.ControlPathService;
import gov.epa.cef.web.service.dto.ControlPathDto;

@RestController
@RequestMapping("/api/controlPath")
public class ControlPathApi {

    @Autowired
    private ControlPathService controlPathService;

    /**
     * Retrieve a control path by id
     * @param controlAssignmentId
     * @return
     */
    @GetMapping(value = "/{controlPathId}")
    @ResponseBody
    public ResponseEntity<ControlPathDto> retrieveAssignmentControl(@PathVariable Long controlPathId) {

    	ControlPathDto result = controlPathService.retrieveById(controlPathId);
        return new ResponseEntity<ControlPathDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for an emissions process
     * @param processId
     * @return
     */
    @GetMapping(value = "/process/{processId}")
    @ResponseBody
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForEmissionsProcess(@PathVariable Long processId) {

        List<ControlPathDto> result = controlPathService.retrieveForEmissionsProcess(processId);
        return new ResponseEntity<List<ControlPathDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for an emissions unit
     * @param unitId
     * @return
     */
    @GetMapping(value = "/unit/{unitId}")
    @ResponseBody
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForEmissionsUnit(@PathVariable Long unitId) {

        List<ControlPathDto> result = controlPathService.retrieveForEmissionsUnit(unitId);
        return new ResponseEntity<List<ControlPathDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Paths for an release point
     * @param pointId
     * @return
     */
    @GetMapping(value = "/releasePoint/{pointId}")
    @ResponseBody
    public ResponseEntity<List<ControlPathDto>> retrieveControlAssignmentsForReleasePoint(@PathVariable Long pointId) {

        List<ControlPathDto> result = controlPathService.retrieveForReleasePoint(pointId);
        return new ResponseEntity<List<ControlPathDto>>(result, HttpStatus.OK);
    }
}
