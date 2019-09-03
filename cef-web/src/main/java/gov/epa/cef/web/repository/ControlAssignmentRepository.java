package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.ControlAssignment;

public interface ControlAssignmentRepository extends CrudRepository<ControlAssignment, Long> {

    /**
     * Retrieve Control Assignments for an emissions process
     * @param processId
     * @return
     */
	@Query("SELECT DISTINCT ca FROM ControlAssignment ca INNER JOIN ca.controlPath cp INNER JOIN cp.releasePointAppts rpa INNER JOIN rpa.emissionsProcess ep WHERE ep.id = :processId")
    List<ControlAssignment> findByEmissionsProcessId(@Param("processId") Long processId);

    /**
     * Retrieve Control Assignments for an emissions unit
     * @param unitId
     * @return
     */
	@Query("SELECT DISTINCT ca FROM ControlAssignment ca INNER JOIN ca.controlPath cp INNER JOIN cp.releasePointAppts rpa INNER JOIN rpa.emissionsProcess ep INNER JOIN ep.emissionsUnit eu WHERE eu.id = :unitId")
    List<ControlAssignment> findByEmissionsUnitId(@Param("unitId") Long unitId);

    /**
     * Retrieve Control Assignments for a release point
     * @param pointId
     * @return
     */
	@Query("SELECT DISTINCT ca FROM ControlAssignment ca INNER JOIN ca.controlPath cp INNER JOIN cp.releasePointAppts rpa INNER JOIN rpa.releasePoint rp WHERE rp.id = :pointId")
    List<ControlAssignment> findByReleasePointId(@Param("pointId") Long pointId);

}
