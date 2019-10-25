package gov.epa.cef.web.api.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.EmissionsReportValidationService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EntityRefDto;
import gov.epa.cef.web.service.validation.ValidationResult;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
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

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/emissionsReport")
public class EmissionsReportApi {

    private final EmissionsReportService emissionsReportService;

    private final EmissionsReportValidationService validationService;

    private final SecurityService securityService;

    @Autowired
    EmissionsReportApi(SecurityService securityService,
                       EmissionsReportService emissionsReportService,
                       EmissionsReportValidationService validationService) {

        this.securityService = securityService;
        this.emissionsReportService = emissionsReportService;
        this.validationService = validationService;
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
    public ResponseEntity<EmissionsReportDto> create(@NotNull @PathVariable String facilityEisProgramId,
                                                     @NotNull @RequestBody EmissionsReportStarterDto reportDto) {

        this.securityService.facilityEnforcer().enforceProgramId(facilityEisProgramId);

        reportDto.setEisProgramId(facilityEisProgramId);

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

        this.securityService.facilityEnforcer().enforceEntities(reportIds, EmissionsReportRepository.class);

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

        this.securityService.facilityEnforcer().enforceEntities(reportIds, EmissionsReportRepository.class);

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
    public ResponseEntity<EmissionsReportDto> retrieveCurrentReportForFacility(
        @NotNull @PathVariable String facilityEisProgramId) {

        this.securityService.facilityEnforcer().enforceProgramId(facilityEisProgramId);

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
    public ResponseEntity<EmissionsReportDto> retrieveReport(@NotNull @PathVariable Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        EmissionsReportDto result = emissionsReportService.findById(reportId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/validation",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidationResult> validateReport(@NotNull @RequestBody EntityRefDto entityRefDto) {

        this.securityService.facilityEnforcer()
            .enforceEntity(entityRefDto.requireNonNullId(), EmissionsReportRepository.class);

        ValidationResult result =
            this.validationService.validateAndUpdateStatus(entityRefDto.getId());

        return ResponseEntity.ok()
            .cacheControl(CacheControl.noCache().sMaxAge(0, TimeUnit.SECONDS).mustRevalidate())
            .body(result);
    }


    /**
     * Retrieve reports for a given facility
     *
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    @GetMapping(value = "/facility/{facilityEisProgramId}")
    public ResponseEntity<List<EmissionsReportDto>> retrieveReportsForFacility(
        @NotNull @PathVariable String facilityEisProgramId) {

        this.securityService.facilityEnforcer().enforceProgramId(facilityEisProgramId);

        List<EmissionsReportDto> result =
            emissionsReportService.findByFacilityEisProgramId(facilityEisProgramId, true);

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
    @RolesAllowed(AppRole.ROLE_CERTIFIER)
    public ResponseEntity<String> submitToCromerr(
        @NotBlank @RequestParam String activityId, @NotNull @RequestParam Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        String documentId = emissionsReportService.submitToCromerr(reportId, activityId);

        return new ResponseEntity<>(documentId, HttpStatus.OK);
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
