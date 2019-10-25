package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.Control;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ControlRepository extends CrudRepository<Control, Long>, ProgramIdRetriever {

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    List<Control> findByFacilitySiteId(Long facilitySiteId);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.ControlProgramIds)
    @Query("select fs.eisProgramId from Control c join c.facilitySite fs where c.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
}
