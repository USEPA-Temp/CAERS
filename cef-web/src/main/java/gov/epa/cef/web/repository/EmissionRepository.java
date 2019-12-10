package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.Emission;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmissionRepository extends CrudRepository<Emission, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.EmissionProgramIds)
    @Query("select fs.eisProgramId from Emission e join e.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where e.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
    * Retrieve Emissions Report id for an Emission
    * @param id
    * @return Emissions Report id
    */
   @Cacheable(value = CacheName.EmissionEmissionsReportIds)
   @Query("select r.id from Emission e join e.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where e.id = :id")
   Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
