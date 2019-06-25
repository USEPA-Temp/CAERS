package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.ControlAssignment;

public interface ControlAssignmentRepository extends CrudRepository<ControlAssignment, Long> {

    /**
     * Retrieve Control Assignments for an emissions process
     * @param processId
     * @return
     */
    List<ControlAssignment> findByEmissionsProcessId(Long processId);

    /**
     * Retrieve Control Assignments for an emissions unit
     * @param unitId
     * @return
     */
    List<ControlAssignment> findByEmissionsUnitId(Long unitId);

    /**
     * Retrieve Control Assignments for a release point
     * @param pointId
     * @return
     */
    List<ControlAssignment> findByReleasePointId(Long pointId);

}
