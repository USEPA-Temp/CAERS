package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.EmissionBulkEntryHolderDto;
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
     * Retrieve Emission by id and generate missing variables
     * @param id
     * @return
     */
    public EmissionDto retrieveWithVariablesById(Long id);

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
     * Retrieve Emissions grouped by Reporting Period for Bulk Entry
     * @param facilitySiteId
     * @return
     */
    public List<EmissionBulkEntryHolderDto> retrieveBulkEntryEmissionsForFacilitySite(Long facilitySiteId);

    /**
     * Update the total emissions for the provided emissions and recalculate all emissions for the facility
     * @param facilitySiteId
     * @param dtos
     * @return
     */
    public List<EmissionBulkEntryHolderDto> bulkUpdate(Long facilitySiteId, List<EmissionDto> dtos);

    /**
     * Recalculate the total emissions in tons for all emissions in a report without changing any other values
     * @param reportId
     * @return
     */
    public List<EmissionDto> recalculateEmissionTons(Long reportId);

    /**
     * Calculate total emissions for an emission. Also calculates emission factor if it uses a formula
     * This method should be used when the Reporting Period in the database should be used for calculations 
     * and you have an EmissionDto, probably with values that differ from the ones in the database.
     * @param dto
     * @return
     */
    public EmissionDto calculateTotalEmissions(EmissionDto dto);

    /**
     * Calculate total emissions for an emission and reporting period. Also calculates emission factor if it uses a formula
     * This method should be used when you need to specify a Reporting Period with a different throughput or UoM than the 
     * one in the database. 
     * @param emission
     * @param rp
     * @return
     */
    public Emission calculateTotalEmissions(Emission emission, ReportingPeriod rp);

    /**
     * Find Emission by TRI Facility ID and CAS Number.
     * This method is the second version of the interface to TRIMEweb so that TRI users can
     * see what emissions have been reported to the Common Emissions Form for the current
     * facility and chemical that they are working on. This version takes a TRIFID and looks 
     * up the EIS ID in CAERS and then finds any existing emissions.
     *
     * @param frsFacilityId
     * @param pollutantCasId
     * @return
     */
    public EmissionsByFacilityAndCASDto findEmissionsByTrifidAndCAS(String trifid, String pollutantCasId);
}
