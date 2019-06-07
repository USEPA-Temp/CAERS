package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.FacilitySiteContact;

public interface FacilitySiteContactRepository extends CrudRepository<FacilitySiteContact, Long> {

    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @return
     */
    List<FacilitySiteContact> findByFacilityId(Long facilitySiteId);

}
