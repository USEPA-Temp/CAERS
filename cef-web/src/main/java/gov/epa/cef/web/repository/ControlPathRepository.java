package gov.epa.cef.web.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import gov.epa.cef.web.domain.ControlPath;

public interface ControlPathRepository extends CrudRepository<ControlPath, Long> {

    /**
     * Retrieve Control Paths for an emissions process
     * @param processId
     * @return
     */
	@Query("SELECT DISTINCT cp FROM ControlPath cp INNER JOIN cp.releasePointAppts rpa INNER JOIN rpa.emissionsProcess ep WHERE ep.id = :processId")
    List<ControlPath> findByEmissionsProcessId(@Param("processId") Long processId);

    /**
     * Retrieve Control Paths for an emissions unit
     * @param unitId
     * @return
     */
	@Query("SELECT DISTINCT cp FROM ControlPath cp INNER JOIN cp.releasePointAppts rpa INNER JOIN rpa.emissionsProcess ep INNER JOIN ep.emissionsUnit eu WHERE eu.id = :unitId")
    List<ControlPath> findByEmissionsUnitId(@Param("unitId") Long unitId);

    /**
     * Retrieve Control Paths for a release point
     * @param pointId
     * @return
     */
	@Query("SELECT DISTINCT cp FROM ControlPath cp INNER JOIN cp.releasePointAppts rpa INNER JOIN rpa.releasePoint rp WHERE rp.id = :pointId")
    List<ControlPath> findByReleasePointId(@Param("pointId") Long pointId);

}
