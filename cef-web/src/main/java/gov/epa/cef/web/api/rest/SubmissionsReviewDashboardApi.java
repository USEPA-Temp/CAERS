package gov.epa.cef.web.api.rest;

import java.util.List; 

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * Retrieve the submissions under review for the reviewer program system and for the current fiscal year
     * @return
     */
    @GetMapping(value = "/dashboard/byStatus/{reportStatus}")
    @ResponseBody
    public ResponseEntity<List<SubmissionsReviewDashboardDto>> retrieveFacilitiesReportsForReview(@NotNull @PathVariable ReportStatus reportStatus) {
        List<SubmissionsReviewDashboardDto> result =submissionsReviewDasboardService.retrieveFacilitiesReportsByReportStatusAndProgramSystemCode(reportStatus);
        return new ResponseEntity<List<SubmissionsReviewDashboardDto>>(result, HttpStatus.OK);
    }
    
    @GetMapping(value = "/dashboard")
    @ResponseBody
    public ResponseEntity<List<SubmissionsReviewDashboardDto>> retrieveFacilitiesReportsForReview() {        
        List<SubmissionsReviewDashboardDto> result =submissionsReviewDasboardService.retrieveFacilitiesReportsForCurrentUserProgramSystemForTheCurrentFiscalYear();       
        return new ResponseEntity<List<SubmissionsReviewDashboardDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve the submissions under review for the inputed year/report status
     * @return
     */
    @GetMapping(value = "/dashboard/byStatusAndYear/{reportYear}/{reportStatus}")
    @ResponseBody
    public ResponseEntity<List<SubmissionsReviewDashboardDto>> retrieveFacilitiesReportsForReview(@NotNull @PathVariable Short reportYear, @NotNull @PathVariable ReportStatus reportStatus) {

        List<SubmissionsReviewDashboardDto> result =submissionsReviewDasboardService.retrieveFacilitiesReportsByYearAndReportStatusAndProgramSystemCode(reportYear, reportStatus);
        return new ResponseEntity<List<SubmissionsReviewDashboardDto>>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve all submissions under review for the current reporting year
     * @return
     */
    @GetMapping(value = "/dashboard/forCurrentReportingYear/{reportYear}")
    @ResponseBody
    public ResponseEntity<List<SubmissionsReviewDashboardDto>> retrieveFacilitiesReportsForReview(@NotNull @PathVariable Short reportYear) {

        List<SubmissionsReviewDashboardDto> result =submissionsReviewDasboardService.retrieveFacilitiesReportsByYearAndProgramSystemCode(reportYear);
        return new ResponseEntity<List<SubmissionsReviewDashboardDto>>(result, HttpStatus.OK);
    }
}
