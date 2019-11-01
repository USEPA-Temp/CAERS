package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.EmissionsUnitDto;

import java.util.List;

public interface EmissionsUnitService {

    /**
     * Retrieve Emissions Unit by its id
     * @param unitId
     * @return
     */
    EmissionsUnitDto retrieveUnitById(Long unitId);

    /**
     * Retrieves Emissions Units for a facility
     * @param facilitySiteId
     * @return
     */
    List<EmissionsUnitDto> retrieveEmissionUnitsForFacility(Long facilitySiteId);
    
    /**
     * Delete an Emissions Unit for a given id
     * @param unitId
     */
    void delete(Long unitId);

}
