package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.FacilitySiteContactDto;

public interface FacilitySiteContactService {

	/**
     * Create a new Facility Site Contact
     * @param dto
     * @return
     */
	FacilitySiteContactDto create(FacilitySiteContactDto dto);
	
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
    
    /**
     * Update an existing Facility Site Contact by id
     * @param dto
     * @return
     */
    FacilitySiteContactDto update(FacilitySiteContactDto dto);
    
    /**
     * Delete Facility Site Contact by id
     * @param id
     */
    void delete(Long id);

}