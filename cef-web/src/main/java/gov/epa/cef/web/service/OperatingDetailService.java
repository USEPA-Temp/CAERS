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

import java.util.List;

import gov.epa.cef.web.service.dto.OperatingDetailDto;
import gov.epa.cef.web.service.dto.bulkUpload.OperatingDetailBulkUploadDto;

public interface OperatingDetailService {

    /**
     * Update an Operating Detail
     * @param dto
     * @return
     */
    OperatingDetailDto update(OperatingDetailDto dto);

    /**
     * Retrieve a list of operating details for the given program system code and emissions report year
     * @param programSystemCode
     * @param emissionsReportYear
     * @return
     */
    List<OperatingDetailBulkUploadDto> retrieveOperatingDetails(String programSystemCode, Short emissionsReportYear);

}