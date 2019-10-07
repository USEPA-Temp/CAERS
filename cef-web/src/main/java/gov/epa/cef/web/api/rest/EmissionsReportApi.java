package gov.epa.cef.web.api.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/emissionsReport")
public class EmissionsReportApi {

    private final EmissionsReportService emissionsReportService;

    @Autowired
    EmissionsReportApi(EmissionsReportService emissionsReportService) {

        this.emissionsReportService = emissionsReportService;
    }

    /**
     * Creates an Emissions Report from either previous report or data from FRS
     *
     * @param facilityEisProgramId
     * @param reportDto
     * @return
     */
    @PostMapping(value = "/facility/{facilityEisProgramId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmissionsReportDto> create(@PathVariable String facilityEisProgramId,
                                                     @NotNull @RequestBody EmissionsReportStarterDto reportDto) {

        if (reportDto.getYear() == null) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT, "Reporting Year must be set.");
        }

        EmissionsReportDto result = reportDto.isSourceFrs()
            ? this.emissionsReportService.createEmissionReportFromFrs(facilityEisProgramId, reportDto.getYear())
            : this.emissionsReportService.createEmissionReportCopy(facilityEisProgramId, reportDto.getYear());

        /*
        If the new report should copy data from FRS then we return ACCEPTED to the UI to indicate a
        pull of FRS data is possible. The ACCEPTED status allows the UI to notify the user that a
        process that might take some time is about to be initiated.

        HTTP Status 202/ACCEPTED indicates that request has been accepted for processing,
        but the processing has not been completed.
        */
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (result != null) {
            if (result.getId() == null) {
                status = HttpStatus.ACCEPTED;
            } else {
                status = HttpStatus.OK;
            }
        }

        return new ResponseEntity<>(result, status);
    }

    /**
     * Approve the specified reports and move to approved
     * @param reportIds
     * @return
     */
    @PostMapping(value = "/accept")
    public ResponseEntity<List<EmissionsReportDto>> acceptReports(@NotNull @RequestBody List<Long> reportIds) {

        List<EmissionsReportDto> result = emissionsReportService.acceptEmissionsReports(reportIds);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Reject the specified reports and move back to in progress
     * @param reportIds
     * @return
     */
    @PostMapping(value = "/reject")
    public ResponseEntity<List<EmissionsReportDto>> rejectReports(@NotNull @RequestBody List<Long> reportIds) {

        List<EmissionsReportDto> result = emissionsReportService.rejectEmissionsReports(reportIds);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve current report for a given facility
     *
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    @GetMapping(value = "/facility/{facilityEisProgramId}/current")
    public ResponseEntity<EmissionsReportDto> retrieveCurrentReportForFacility(@PathVariable String facilityEisProgramId) {

        EmissionsReportDto result = emissionsReportService.findMostRecentByFacilityEisProgramId(facilityEisProgramId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve report by ID
     *
     * @param reportId
     * @return
     */
    @GetMapping(value = "/{reportId}")
    public ResponseEntity<EmissionsReportDto> retrieveReport(@PathVariable Long reportId) {

        EmissionsReportDto result = emissionsReportService.findById(reportId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve reports for a given facility
     *
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    @GetMapping(value = "/facility/{facilityEisProgramId}")
    public ResponseEntity<List<EmissionsReportDto>> retrieveReportsForFacility(@PathVariable String facilityEisProgramId) {

        List<EmissionsReportDto> result = emissionsReportService.findByFacilityEisProgramId(facilityEisProgramId, true);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Submits a report to CROMERR after the user authenticates through the widgets and generates and activityId
     *
     * @param activityId CROMERR Activity ID
     * @param reportId   Emissions Report Id
     * @return
     */
    @GetMapping(value = "/submitToCromerr")
    public ResponseEntity<String> submitToCromerr(@RequestParam String activityId, @RequestParam Long reportId) {

        String documentId = emissionsReportService.submitToCromerr(reportId, activityId);

        return new ResponseEntity<String>(documentId, HttpStatus.OK);
    }

    static class EmissionsReportStarterDto {

        enum SourceType {
            previous, frs
        }

        private String eisProgramId;

        private SourceType source;

        private Short year;

        public String getEisProgramId() {

            return eisProgramId;
        }

        public void setEisProgramId(String eisProgramId) {

            this.eisProgramId = eisProgramId;
        }

        public SourceType getSource() {

            return source;
        }

        public void setSource(SourceType source) {

            this.source = source;
        }

        public Short getYear() {

            return year;
        }

        public void setYear(Short year) {

            this.year = year;
        }

        @JsonIgnore
        boolean isSourceFrs() {

            return this.source != null && this.source.equals(SourceType.frs);
        }
    }

}
