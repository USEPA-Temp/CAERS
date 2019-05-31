package gov.epa.cef.web.service;

import java.util.Collection;

import gov.epa.cef.web.domain.EmissionsUnit;

public interface EmissionsUnitService {

    /**
     * Retrieve Emissions Unit by its id
     * @param unitId 
     * @return
     */	
    EmissionsUnit retrieveUnitById(Long unitId);

    /**
     * Retrieve Emissions Units by facility id
     * @param facilityId
     * @return
     */
    Collection<EmissionsUnit> retrieveByFacilityId(Long facilityId);

}