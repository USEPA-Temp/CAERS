package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ControlPathPollutant;

public interface ControlPathPollutantRepository extends CrudRepository<ControlPathPollutant, Long>, ProgramIdRetriever, ReportIdRetriever {

	/**
     * Retrieve Control Path Pollutants for a control
     * @param facilitySiteId
     * @return
     */
    List<ControlPathPollutant> findByControlPathId(Long controlPathId);

   @Cacheable(value = CacheName.ControlPathPollutantMasterIds)
   @Query("select mfr.id from ControlPathPollutant cpp join cpp.controlPath cp join cp.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where cpp.id = :id")
   Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);
   
   /**
    * Retrieve Emissions Report id for a Control Path Pollutant
    * @param id
    * @return Emissions Report id
    */
   @Cacheable(value = CacheName.ControlPathPollutantEmissionsReportIds)
   @Query("select r.id from ControlPathPollutant cpp join cpp.controlPath cp join cp.facilitySite fs join fs.emissionsReport r where cpp.id = :id")
   Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
