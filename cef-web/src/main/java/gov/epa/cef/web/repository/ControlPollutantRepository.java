package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName; 
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.Pollutant;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

public interface ControlPollutantRepository extends CrudRepository<ControlPollutant, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Control Pollutants for a control
     * @param facilitySiteId
     * @return
     */
    List<ControlPollutant> findByControlId(Long controlId);

    /**
    *
    * @param id
    * @return EIS Program ID
    */
   @Cacheable(value = CacheName.ControlPollutantProgramIds)
   @Query("select fs.eisProgramId from ControlPollutant cp join cp.control c join c.facilitySite fs where cp.id = :id")
   Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
   
   /**
    * Retrieve Emissions Report id for a Control Pollutant
    * @param id
    * @return Emissions Report id
    */
   @Cacheable(value = CacheName.ControlPollutantEmissionsReportIds)
   @Query("select r.id from ControlPollutant cp join cp.control c join c.facilitySite fs join fs.emissionsReport r where cp.id = :id")
   Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}

