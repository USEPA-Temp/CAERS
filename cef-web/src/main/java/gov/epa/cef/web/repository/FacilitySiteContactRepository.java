package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.FacilitySiteContact;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilitySiteContactRepository extends CrudRepository<FacilitySiteContact, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @return
     */
    List<FacilitySiteContact> findByFacilitySiteId(Long facilitySiteId);
    
    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @param contactTypeCode
     * @return
     */
    List<FacilitySiteContact> findByFacilitySiteIdAndType(Long facilitySiteId, ContactTypeCode contactTypeCode);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.FacilitySiteContactProgramIds)
    @Query("select fs.eisProgramId from FacilitySiteContact c join c.facilitySite fs where c.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Facility Site Contact
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.FacilitySiteContactEmissionsReportIds)
    @Query("select r.id from FacilitySiteContact c join c.facilitySite fs join fs.emissionsReport r where c.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
