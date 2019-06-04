package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.domain.Facility;

public interface FacilityService {

    /**
     * Find facility by ID
     * @param id
     * @return
     */
    Facility findById(Long id);

    /**
     * Retrieve facility by frs facility and emissions report
     * @param frsFacilityId
     * @param emissionsReportId
     * @return
     */
    Facility findByFrsIdAndReportId(String frsFacilityId, Long emissionsReportId);

    /**
     * Find common form facilities for a given state
     * @param state Two-character state code
     * @return
     */	
    List<Facility> findByState(String state);

}