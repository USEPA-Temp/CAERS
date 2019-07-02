package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;

public interface EmissionService {
    
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
    EmissionsByFacilityAndCASDto findEmissionsByFacilityAndCAS(String frsFacilityId, String pollutantCasId);

}
