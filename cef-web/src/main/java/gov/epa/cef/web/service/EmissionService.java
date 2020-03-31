package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.ReportingPeriod;
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
     * Delete an Emission for a given id
     * @param id
     */
    public void delete(Long id);

    /**
     * Calculate total emissions for an emission. Also calculates emission factor if it uses a formula
     * @param dto
     * @return
     */
    public EmissionDto calculateTotalEmissions(EmissionDto dto);

    /**
     * Calculate total emissions for an emission and reporting period. Also calculates emission factor if it uses a formula
     * @param emission
     * @param rp
     * @return
     */
    public Emission calculateTotalEmissions(Emission emission, ReportingPeriod rp);

    /**
     * Find Emission by Facility and CAS Number.
     * This method is primarily intended to provide the interface to TRIMEweb so that TRI users can
     * see what emissions have been reported to the Common Emissions Form for the current
     * facility and chemical that they are working on.
     *
     * @param frsFacilityId
     * @param pollutantCasId
     * @return
     */
    public EmissionsByFacilityAndCASDto findEmissionsByFacilityAndCAS(String frsFacilityId, String pollutantCasId);

}
