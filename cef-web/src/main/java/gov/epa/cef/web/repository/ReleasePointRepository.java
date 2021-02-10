package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ReleasePoint;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReleasePointRepository extends CrudRepository<ReleasePoint, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Release Points for a facility
     * @param facilitySiteId
     * @return
     */
    List<ReleasePoint> findByFacilitySiteIdOrderByReleasePointIdentifier(Long facilitySiteId);

    /**
     * Find Release Points with the specified identifier, master facility record id, and year
     * @param identifier
     * @param mfrId
     * @param year
     * @return
     */
    @Query("select rp from ReleasePoint rp join rp.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr "
            + "where rp.releasePointIdentifier = :identifier and mfr.id = :mfrId and r.year = :year")
    List<ReleasePoint> retrieveByIdentifierFacilityYear(@Param("identifier") String identifier, @Param("mfrId") Long mfrId, @Param("year") Short year);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.ReleasePointProgramIds)
    @Query("select mfr.eisProgramId from ReleasePoint rp join rp.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where rp.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    @Cacheable(value = CacheName.ReleasePointMasterIds)
    @Query("select mfr.id from ReleasePoint rp join rp.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where rp.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Release Point
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ReleasePointEmissionsReportIds)
    @Query("select r.id from ReleasePoint rp join rp.facilitySite fs join fs.emissionsReport r where rp.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
