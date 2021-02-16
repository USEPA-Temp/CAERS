package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.Control;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ControlRepository extends CrudRepository<Control, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    List<Control> findByFacilitySiteIdOrderByIdentifier(Long facilitySiteId);

    @Cacheable(value = CacheName.ControlMasterIds)
    @Query("select mfr.id from Control c join c.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where c.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Control
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ControlEmissionsReportIds)
    @Query("select r.id from Control c join c.facilitySite fs join fs.emissionsReport r where c.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
