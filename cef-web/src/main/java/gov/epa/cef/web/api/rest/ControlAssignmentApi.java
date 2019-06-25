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

import gov.epa.cef.web.service.ControlAssignmentService;
import gov.epa.cef.web.service.dto.ControlAssignmentDto;

@RestController
@RequestMapping("/api/controlAssignment")
public class ControlAssignmentApi {

    @Autowired
    private ControlAssignmentService controlService;

    /**
     * Retrieve a control assignment by id
     * @param controlAssignmentId
     * @return
     */
    @GetMapping(value = "/{controlAssignmentId}")
    @ResponseBody
    public ResponseEntity<ControlAssignmentDto> retrieveAssignmentControl(@PathVariable Long controlAssignmentId) {

        ControlAssignmentDto result = controlService.retrieveById(controlAssignmentId);
        return new ResponseEntity<ControlAssignmentDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Assignments for a facility site
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facilitySite/{facilitySiteId}")
    @ResponseBody
    public ResponseEntity<List<ControlAssignmentDto>> retrieveControlAssignmentsForFacilitySite(@PathVariable Long facilitySiteId) {

        List<ControlAssignmentDto> result = controlService.retrieveForFacilitySite(facilitySiteId);
        return new ResponseEntity<List<ControlAssignmentDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Assignments for an emissions process
     * @param processId
     * @return
     */
    @GetMapping(value = "/process/{processId}")
    @ResponseBody
    public ResponseEntity<List<ControlAssignmentDto>> retrieveControlAssignmentsForEmissionsProcess(@PathVariable Long processId) {

        List<ControlAssignmentDto> result = controlService.retrieveForEmissionsProcess(processId);
        return new ResponseEntity<List<ControlAssignmentDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Assignments for an emissions unit
     * @param unitId
     * @return
     */
    @GetMapping(value = "/unit/{unitId}")
    @ResponseBody
    public ResponseEntity<List<ControlAssignmentDto>> retrieveControlAssignmentsForEmissionsUnit(@PathVariable Long unitId) {

        List<ControlAssignmentDto> result = controlService.retrieveForEmissionsUnit(unitId);
        return new ResponseEntity<List<ControlAssignmentDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Control Assignments for an release point
     * @param pointId
     * @return
     */
    @GetMapping(value = "/releasePoint/{pointId}")
    @ResponseBody
    public ResponseEntity<List<ControlAssignmentDto>> retrieveControlAssignmentsForReleasePoint(@PathVariable Long pointId) {

        List<ControlAssignmentDto> result = controlService.retrieveForReleasePoint(pointId);
        return new ResponseEntity<List<ControlAssignmentDto>>(result, HttpStatus.OK);
    }
}
