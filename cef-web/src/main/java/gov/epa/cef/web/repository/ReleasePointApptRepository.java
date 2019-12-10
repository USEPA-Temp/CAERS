package gov.epa.cef.web.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ReleasePointAppt;

public interface ReleasePointApptRepository extends CrudRepository<ReleasePointAppt, Long>, ProgramIdRetriever, ReportIdRetriever {
	
	/**
    *
    * @param id
    * @return EIS Program ID
    */
	@Cacheable(value = CacheName.ReleasePointApptProgramIds)
	@Query("select fs.eisProgramId from ReleasePointAppt rpa join rpa.releasePoint rp join rp.facilitySite fs where rpa.id = :id")
	Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Release Point Appt
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ReleasePointApptEmissionsReportIds)
    @Query("select r.id from ReleasePointAppt rpa join rpa.releasePoint rp join rp.facilitySite fs join fs.emissionsReport r where rpa.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);

}
