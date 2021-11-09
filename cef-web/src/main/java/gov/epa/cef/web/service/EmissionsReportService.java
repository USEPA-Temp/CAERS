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

import gov.epa.cef.web.api.rest.EmissionsReportApi.ReviewDTO;
import gov.epa.cef.web.domain.EmissionsReport;

import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.dto.EmissionsReportAgencyDataDto;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface EmissionsReportService {

    /**
     * Creates an emissions report from scratch
     * @param reportDto
     * @return
     */
    EmissionsReportDto createEmissionReport(EmissionsReportStarterDto reportDto);

    /**
     * Find reports for a given facility
     * @param masterFacilityRecordId
     * @return
     */
    List<EmissionsReportDto> findByMasterFacilityRecordId(Long masterFacilityRecordId);

    /**
     * Find reports for a given facility and add a new emissions report record in memory for the current year if addReportForCurrentYear is true
     * @param masterFacilityRecordId
     * @return
     */
    List<EmissionsReportDto> findByMasterFacilityRecordId(Long masterFacilityRecordId, boolean addReportForCurrentYear);

    /**
     * Find report by ID
     * @param id
     * @return
     */
    EmissionsReportDto findById(Long id);

    /**
     * Find report by ID
     * @param id
     * @return
     */
    Optional<EmissionsReport> retrieve(long id);

    /**
     * Find report by facility id and year
     * @return
     */
    Optional<EmissionsReport> retrieveByMasterFacilityRecordIdAndYear(@NotBlank Long masterFacilityRecordId, int year);

    /**
     * Find the most recent report for a given facility
     * @param masterFacilityRecordId
     * @return
     */
    EmissionsReportDto findMostRecentByMasterFacilityRecordId(Long masterFacilityRecordId);

    /**
     * Find all agencies with reports and which years they have reports for
     */
    List<EmissionsReportAgencyDataDto> findAgencyReportedYears();

    String submitToCromerr(Long emissionsReportId, String activityId) throws ApplicationException;


    /**
     * Create a copy of the emissions report for the current year based on the specified facility and year.  The copy of the report is NOT saved to the database.
     * @param masterFacilityRecordId
     * @param currentReportYear The year of the report that is being created
     * @return
     */
    EmissionsReportDto createEmissionReportCopy(EmissionsReportStarterDto reportDto);

    /**
     * Save the emissions report to the database.
     * @param emissionsReport
     * @return
     */
    EmissionsReportDto saveAndAuditEmissionsReport(EmissionsReport emissionsReport, ReportAction reportAction);

    /**
     * Delete specified emissions report from database
     * @param id
     */
    void delete(Long id);
    
    List<EmissionsReportDto> beginAdvancedQAEmissionsReports(List<Long> reportIds);

	List<EmissionsReportDto> acceptEmissionsReports(List<Long> reportIds, String comments);

	List<EmissionsReportDto> rejectEmissionsReports(ReviewDTO reviewDTO);

    /**
     * Update an existing Emissions Report from a DTO
     */
    EmissionsReportDto updateSubmitted(long reportId, boolean submitted);

}
