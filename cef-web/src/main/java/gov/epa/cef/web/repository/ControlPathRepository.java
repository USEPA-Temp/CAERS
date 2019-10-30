package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ControlPath;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ControlPathRepository extends CrudRepository<ControlPath, Long>, ProgramIdRetriever {

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

    /**
     *
     * @param id
     * @return EIS Program ID
     */
	@Cacheable(value = CacheName.ControlPathProgramIds)
    @Query("select fs.eisProgramId from ControlPath cp join cp.facilitySite fs where cp.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
}
