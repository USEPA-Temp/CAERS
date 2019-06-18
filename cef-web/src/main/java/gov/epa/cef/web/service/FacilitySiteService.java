package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.dto.FacilitySiteDto;

public interface FacilitySiteService {

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
     * Find common form facilities for a given state
     * @param state Two-character state code
     * @return
     */	
    List<FacilitySiteDto> findByState(String state);

}