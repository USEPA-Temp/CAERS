package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.EmissionsUnit;

public interface EmissionsUnitService {

    /**
     * Retrieve Emissions Unit by its id
     * @param unitId 
     * @return
     */	
    EmissionsUnit retrieveUnitById(Long unitId);

}