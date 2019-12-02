package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.EmissionsProcess;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmissionsProcessRepository extends CrudRepository<EmissionsProcess, Long>, ProgramIdRetriever {

    List<EmissionsProcess> findByReleasePointApptsReleasePointId(Long releasePointId, Sort sort);

    List<EmissionsProcess> findByEmissionsUnitId(Long emissionsUnitId, Sort sort);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.ProcessProgramIds)
    @Query("select fs.eisProgramId from EmissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where p.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
}
