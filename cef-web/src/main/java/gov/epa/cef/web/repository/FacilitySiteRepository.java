package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.FacilitySite;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilitySiteRepository extends CrudRepository<FacilitySite, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve facilities by emissions report
     * @param emissionsReportId
     * @return
     */
    List<FacilitySite> findByEmissionsReportId(Long emissionsReportId);

    /***
     * Retrieve the common form facilities based on a given state code
     * @param stateCode : 2 character state code
     * @return
     */
    List<FacilitySite> findByStateCode(String stateCode);

    @Cacheable(value = CacheName.FacilityMasterIds)
    @Query("select mfr.id from FacilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where fs.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Facility Site
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.FacilityEmissionsReportIds)
    @Query("select r.id from FacilitySite fs join fs.emissionsReport r where fs.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
