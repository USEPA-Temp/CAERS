package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.dto.FacilityNAICSDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;

public interface FacilitySiteService {

    /**
     * Create a new facilitySite
     * @param facilitySite
     * @return
     */
	FacilitySiteDto create(FacilitySite facilitySite);

    /**
     * Find facility by ID
     * @param id
     * @return
     */
    FacilitySiteDto findById(Long id);

    /**
     * Retrieve facility by emissions report
     * @param emissionsReportId
     * @return
     */
    FacilitySiteDto findByReportId(Long emissionsReportId);

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
     * Update existing facility NAICS
     * @param dto
     * @return
     */
    FacilityNAICSDto updateNaics(FacilityNAICSDto dto);

    /**
     * Delete Facility NAICS by id
     * @param facilityNaicsId
     */
    void deleteFacilityNaics(Long facilityNaicsId);


    /**
     * Transform from DTO to new instance FacilitySite
     * @return
     */
    FacilitySite transform(FacilitySiteDto dto);
}
