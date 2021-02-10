package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.EmissionsUnit;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
     * Find Emissions Units with the specified identifier, master facility record id, and year
     * @param identifier
     * @param mfrId
     * @param year
     * @return
     */
    @Query("select eu from EmissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where eu.unitIdentifier = :identifier and mfr.id = :mfrId and r.year = :year")
    List<EmissionsUnit> retrieveByIdentifierFacilityYear(@Param("identifier") String identifier, @Param("mfrId") Long mfrId, @Param("year") Short year);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.UnitProgramIds)
    @Query("select mfr.eisProgramId from EmissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where eu.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    @Cacheable(value = CacheName.UnitMasterIds)
    @Query("select mfr.id from EmissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where eu.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for an Emissions Unit 
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.UnitEmissionsReportIds)
    @Query("select r.id from EmissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where eu.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
