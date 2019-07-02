package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.SubmissionsReviewDasboardService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;

@RestController
@RequestMapping("/api/submissionsReview")
public class SubmissionsReviewDashboardApi {

    
    @Autowired
    private SubmissionsReviewDasboardService submissionsReviewDasboardService;
    
    /**
     * Retrieve the currently authenticated user
     * @return
     */
    @GetMapping(value = "/dashboard")
    @ResponseBody
    public ResponseEntity<List<SubmissionsReviewDashboardDto>> retrieveFacilitiesReportsForReview() {
        List<SubmissionsReviewDashboardDto> result =submissionsReviewDasboardService.retrieveFacilitiesReports();
        return new ResponseEntity<List<SubmissionsReviewDashboardDto>>(result, HttpStatus.OK);
    }
    
    
}
