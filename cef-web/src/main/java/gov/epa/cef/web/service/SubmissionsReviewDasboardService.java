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

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;

public interface SubmissionsReviewDasboardService {
    
    /**
     * Retrieves all the facilities reports for review 
     * 
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsForCurrentUserProgramSystemForTheCurrentFiscalYear();
    
    /**
     * Retrieves all the facilities reports for review by report status
     * 
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByReportStatusAndProgramSystemCode(ReportStatus reportStatus);
    
    /**
     * Retrieves all the facilities reports for review based on reportYear and reportStatus
     * @param reportYear
     * @param reportStatus
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndReportStatusAndProgramSystemCode(Short reportYear, ReportStatus reportStatus);
    
    /**
     * Retrieves all the facilities reports for review for current reporting year
     * @param reportYear
     */
    List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndProgramSystemCode(Short reportYear);
    
    
}
