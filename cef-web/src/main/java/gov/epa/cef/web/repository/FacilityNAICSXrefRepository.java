package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.FacilityNAICSXref;

public interface FacilityNAICSXrefRepository extends CrudRepository<FacilityNAICSXref, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Facility NAICS for a facility site
     * 
     * @param facilitySiteId
     * @return
     */
    List<FacilityNAICSXref> findByFacilitySiteId(Long facilitySiteId);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.FacilityNAICSProgramIds)
    @Query("select mfr.eisProgramId from FacilityNAICSXref fn join fn.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where fn.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    @Cacheable(value = CacheName.FacilityNAICSMasterIds)
    @Query("select mfr.id from FacilityNAICSXref fn join fn.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where fn.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Facility NAICS Xref
     * 
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.FacilityNAICSEmissionsReportIds)
    @Query("select er.id from FacilityNAICSXref fn join fn.facilitySite fs join fs.emissionsReport er where fn.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);

}
