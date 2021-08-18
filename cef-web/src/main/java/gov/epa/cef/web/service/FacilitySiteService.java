/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
