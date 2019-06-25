package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.ControlAssignmentDto;

public interface ControlAssignmentService {

    /**
     * Retrieve Control Assignment by its id
     * @param id
     * @return
     */
    ControlAssignmentDto retrieveById(Long id);

    /**
     * Retrieve Control Assignments for an emissions process
     * @param processId
     * @return
     */
    List<ControlAssignmentDto> retrieveForEmissionsProcess(Long processId);

    /**
     * Retrieve Control Assignments for an emissions unit
     * @param unitId
     * @return
     */
    List<ControlAssignmentDto> retrieveForEmissionsUnit(Long unitId);

    /**
     * Retrieve Control Assignments for a release point
     * @param pointId
     * @return
     */
    List<ControlAssignmentDto> retrieveForReleasePoint(Long pointId);

}