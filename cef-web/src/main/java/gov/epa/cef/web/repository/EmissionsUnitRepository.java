package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.EmissionsUnit;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface EmissionsUnitRepository extends CrudRepository<EmissionsUnit, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Emissions Units for a facility
     * @param facilitySiteId
     * @return
     */
    List<EmissionsUnit> findByFacilitySiteIdOrderByUnitIdentifier(Long facilitySiteId);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.UnitProgramIds)
    @Query("select fs.eisProgramId from EmissionsUnit eu join eu.facilitySite fs where eu.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for an Emissions Unit 
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.UnitEmissionsReportIds)
    @Query("select r.id from EmissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where eu.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
