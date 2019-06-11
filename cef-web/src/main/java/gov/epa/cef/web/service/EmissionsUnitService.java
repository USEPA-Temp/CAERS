package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.EmissionsUnitDto;

public interface EmissionsUnitService {

    /**
     * Retrieve Emissions Unit by its id
     * @param unitId 
     * @return
     */	
    EmissionsUnitDto retrieveUnitById(Long unitId);

    /**
     * Retrieves Emissions Units for a facility
     * @param facilityId
     * @return
     */
    List<EmissionsUnitDto> retrieveEmissionUnitsForFacility(Long facilitySiteId);

}