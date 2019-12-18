package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.dto.FacilityNAICSDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.client.frs.iptquery.model.ProgramFacility;

import java.util.Optional;

public interface FacilitySiteService {

    /**
     * Create a FacilitySite record from FRS information
     * @param report
     * @return
     */
    FacilitySite copyFromFrs(EmissionsReport report);

    /**
     * Find facility by ID
     * @param id
     * @return
     */
    FacilitySiteDto findById(Long id);

    /**
     * Retrieve facility by EIS program id and emissions report
     * @param eisProgramId
     * @param emissionsReportId
     * @return
     */
    FacilitySiteDto findByEisProgramIdAndReportId(String eisProgramId, Long emissionsReportId);


    /**
     * Retrieve facility information from FRS
     * @param facilityEisProgramId
     * @return
     */
    Optional<ProgramFacility> retrieveFromFrs(String facilityEisProgramId);
    
    /**
     * Update facility information
     * @param dto
     * @return
     */
    FacilitySiteDto update(FacilitySiteDto dto);
    
    /**
     * Create Facility NAICS 
     * @param dto
     */
    FacilityNAICSDto createNaics(FacilityNAICSDto dto);
    
    /**
     * Delete Facility NAICS by id
     * @param facilityNaicsId
     */
    void deleteFacilityNaics(Long facilityNaicsId);
}
