package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ReportingPeriod;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportingPeriodRepository extends CrudRepository<ReportingPeriod, Long>, ProgramIdRetriever {

    /**
     * Retrieve Reporting Periods for an emissions process
     * @param processId
     * @return
     */
    List<ReportingPeriod> findByEmissionsProcessId(Long processId);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Override
    @Cacheable(value = CacheName.ReportingPeriodProgramIds)
    @Query("select fs.eisProgramId from ReportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where rp.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
}
