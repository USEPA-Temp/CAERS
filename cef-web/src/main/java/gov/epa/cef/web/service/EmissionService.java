package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;

public interface EmissionService {
    
    /**
     * Create a new Emission
     * @param dto
     * @return
     */
    public EmissionDto create(EmissionDto dto);
    
    /**
     * Retrieve Emission by id
     * @param id 
     * @return
     */
    public EmissionDto retrieveById(Long id);
    
    /**
     * Update an existing Emission
     * @param dto
     * @return
     */
    public EmissionDto update(EmissionDto dto);
    
    /**
     * Find Emission by Facility and CAS Number.
     * This method is primarily intended to provide the interface to TRIMEweb so that TRI users can
     * see what emissions have been reported to the Common Emissions Form for the current
     * facility and chemical that they are working on.
     * 
     * @param frsFacilityId
     * @param casNumber
     * @return
     */
    public EmissionsByFacilityAndCASDto findEmissionsByFacilityAndCAS(String frsFacilityId, String pollutantCasId);
    
    /**
     * Delete an Emission for a given id
     * @param id
     */
    public void delete(Long id);

}
