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
package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.dto.ReportDownloadDto;
import gov.epa.cef.web.service.dto.ReportHistoryDto;
import gov.epa.cef.web.service.dto.ReportSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportApi {

    private final ReportService reportService;

    private final SecurityService securityService;

    @Autowired
    ReportApi( SecurityService securityService, ReportService reportService) {

        this.reportService = reportService;
        this.securityService = securityService;
    }

    /***
     * Return list of report summary records with total emissions summed per pollutant for the chosen facility and year
     * @param facilitySiteId
     * @param year
     * @return
     */
    @GetMapping(value = "/emissionsSummary/year/{year}/facilitySiteId/{facilitySiteId}")
    public ResponseEntity<List<ReportSummaryDto>> retrieveEmissionsSummary(
        @NotNull @PathVariable Long facilitySiteId, @NotNull @PathVariable Short year) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        List<ReportSummaryDto> reportSummary = reportService.findByReportYearAndFacilitySiteId(year, facilitySiteId);

        return new ResponseEntity<>(reportSummary, HttpStatus.OK);
    }
    
    /***
     * Return list of report history records for the chosen report
     * @param reportId
     * @return
     */
    @GetMapping(value = "/reportHistory/report/{reportId}/facilitySiteId/{facilitySiteId}")
    public ResponseEntity<List<ReportHistoryDto>> retrieveReportHistory(
    		@NotNull @PathVariable Long reportId, @NotNull @PathVariable Long facilitySiteId) {

    		this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

    		List<ReportHistoryDto> reportHistory = reportService.findByEmissionsReportId(reportId);

        return new ResponseEntity<>(reportHistory, HttpStatus.OK);
    }
    
    /***
     * Return DownloadReportDto
     * @param facilitySiteId
     * @param reportId
     * @return
     */
    @GetMapping(value = "/downloadReport/reportId/{reportId}/facilitySiteId/{facilitySiteId}")
    public ResponseEntity<List<ReportDownloadDto>> retrieveDownloadReportDto(
        @NotNull @PathVariable Long facilitySiteId, @NotNull @PathVariable Long reportId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        List<ReportDownloadDto> reportDownloadDto = reportService.retrieveReportDownloadDtoByReportId(reportId);

        return new ResponseEntity<>(reportDownloadDto, HttpStatus.OK);
    }
}
