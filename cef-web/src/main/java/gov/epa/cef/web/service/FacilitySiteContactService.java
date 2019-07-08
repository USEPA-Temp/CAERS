package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.FacilitySiteContactDto;

public interface FacilitySiteContactService {

    /**
     * Retrieve Facility Site Contact by id
     * @param id 
     * @return
     */
    FacilitySiteContactDto retrieveById(Long id);

    /**
     * Retrieves Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @return
     */
    List<FacilitySiteContactDto> retrieveForFacilitySite(Long facilitySiteId);

}