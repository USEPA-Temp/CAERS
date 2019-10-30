package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.FacilitySiteContact;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilitySiteContactRepository extends CrudRepository<FacilitySiteContact, Long>, ProgramIdRetriever {

    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @return
     */
    List<FacilitySiteContact> findByFacilitySiteId(Long facilitySiteId);

    /**
     *
     * @param id
     * @return EIS Program ID
     */
    @Cacheable(value = CacheName.FacilitySiteContactProgramIds)
    @Query("select fs.eisProgramId from FacilitySiteContact c join c.facilitySite fs where c.id = :id")
    Optional<String> retrieveEisProgramIdById(@Param("id") Long id);
}
