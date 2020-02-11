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
