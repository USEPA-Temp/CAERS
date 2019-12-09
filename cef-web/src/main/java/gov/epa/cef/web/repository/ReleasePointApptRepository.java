package gov.epa.cef.web.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ReleasePointAppt;

public interface ReleasePointApptRepository extends CrudRepository<ReleasePointAppt, Long>, ProgramIdRetriever {
	
	/**
    *
    * @param id
    * @return EIS Program ID
    */
	@Cacheable(value = CacheName.ReleasePointApptProgramIds)
	@Query("select fs.eisProgramId from ReleasePointAppt rpa join rpa.releasePoint rp join rp.facilitySite fs where rpa.id = :id")
	Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

}
