package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.EmissionsProcess;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmissionsProcessRepository extends CrudRepository<EmissionsProcess, Long>, ProgramIdRetriever, ReportIdRetriever {

    List<EmissionsProcess> findByReleasePointApptsReleasePointIdOrderByEmissionsProcessIdentifier(Long releasePointId);

    List<EmissionsProcess> findByEmissionsUnitIdOrderByEmissionsProcessIdentifier(Long emissionsUnitId);

    /**
     * Find Emissions Process with the specified identifier, parent identifier, EIS program id, and year
     * @param identifier
     * @param parentIdentifier
     * @param eisProgramId
     * @param year
     * @return
     */
    @Query("select ep from EmissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r "
            + "where ep.emissionsProcessIdentifier = :identifier and eu.unitIdentifier = :parentIdentifier and fs.eisProgramId = :eisProgramId and r.year = :year")
    List<EmissionsProcess> retrieveByIdentifierParentFacilityYear(@Param("identifier") String identifier, @Param("parentIdentifier") String parentIdentifier, 
                                                                      @Param("eisProgramId") String eisProgramId, @Param("year") Short year);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.ProcessProgramIds)
    @Query("select fs.eisProgramId from EmissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where p.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for an Emissions Process
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ProcessEmissionsReportIds)
    @Query("select r.id from EmissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where p.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
