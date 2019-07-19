package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.dto.ReportSummaryDto;

@RestController
@RequestMapping("/api/reportSummary")
public class ReportApi {

    @Autowired
    private ReportService reportService;


    /***
     * Return list of report summary records with total emissions summed per pollutant for the chosen facility and year
     * @param facilitySiteId
     * @param year
     * @return
     */
    @GetMapping(value = "/emissionsSummary/year/{year}/facilitySiteId/{facilitySiteId}")
    @ResponseBody
    public ResponseEntity<List<ReportSummaryDto>> retrieveEmissionsSummary(@PathVariable Long facilitySiteId, @PathVariable Short year) {
        List<ReportSummaryDto> reportSummary = reportService.findByReportYearAndFacilitySiteId(year, facilitySiteId);

        return new ResponseEntity<List<ReportSummaryDto>>(reportSummary, HttpStatus.OK);
    }
    
}
