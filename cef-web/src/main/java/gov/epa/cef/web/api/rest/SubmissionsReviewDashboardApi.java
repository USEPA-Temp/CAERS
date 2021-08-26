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

import java.util.List; 

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.service.SubmissionsReviewDasboardService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;

@RestController
@RequestMapping("/api/submissionsReview")
public class SubmissionsReviewDashboardApi {

    @Autowired
    private SubmissionsReviewDasboardService submissionsReviewDasboardService;

    /**
     * Retrieve submissions for the current user's SLT based on year and status
     * @param reportYear
     * @param reportStatus
     * @return
     */
    @GetMapping(value = "/dashboard")
    @ResponseBody
    public ResponseEntity<List<SubmissionsReviewDashboardDto>> retrieveReviewerSubmissions(
            @RequestParam(required = false) Short reportYear,
            @RequestParam(required = false) ReportStatus reportStatus) {

        List<SubmissionsReviewDashboardDto> result = submissionsReviewDasboardService.retrieveReviewerFacilityReports(reportYear, reportStatus);
        return new ResponseEntity<List<SubmissionsReviewDashboardDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve submissions based on year, status, and agency
     * @param agency
     * @param reportYear
     * @param reportStatus
     * @return
     */
    @GetMapping(value = "/dashboard/{agency}")
    @ResponseBody
    public ResponseEntity<List<SubmissionsReviewDashboardDto>> retrieveAgencySubmissions(
            @NotNull @PathVariable String agency,
            @RequestParam(required = false) Short reportYear,
            @RequestParam(required = false) ReportStatus reportStatus) {

        List<SubmissionsReviewDashboardDto> result = submissionsReviewDasboardService.retrieveFacilityReports(reportYear, reportStatus, agency);
        return new ResponseEntity<List<SubmissionsReviewDashboardDto>>(result, HttpStatus.OK);
    }
}
