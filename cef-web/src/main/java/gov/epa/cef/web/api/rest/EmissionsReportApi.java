package gov.epa.cef.web.api.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.exception.BulkReportValidationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.BulkUploadService;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.EmissionsReportStatusService;
import gov.epa.cef.web.service.EmissionsReportValidationService;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import gov.epa.cef.web.service.dto.EntityRefDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.validation.ValidationResult;
import gov.epa.cef.web.util.TempFile;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/emissionsReport")
public class EmissionsReportApi {

    Logger LOGGER = LoggerFactory.getLogger(EmissionsReportApi.class);

    private final EmissionsReportService emissionsReportService;

    private final EmissionsReportStatusService emissionsReportStatusService;

    private final ReportService reportService;

    private final EmissionsReportValidationService validationService;

    private final BulkUploadService uploadService;

    private final SecurityService securityService;

    private final ObjectMapper objectMapper;

    @Autowired
    EmissionsReportApi(SecurityService securityService,
                       EmissionsReportService emissionsReportService,
                       EmissionsReportStatusService emissionsReportStatusService,
                       ReportService reportService,
                       EmissionsReportValidationService validationService,
                       BulkUploadService uploadService,
                       ObjectMapper objectMapper) {

        this.securityService = securityService;
        this.emissionsReportService = emissionsReportService;
        this.emissionsReportStatusService = emissionsReportStatusService;
        this.reportService = reportService;
        this.validationService = validationService;
        this.uploadService = uploadService;

        this.objectMapper = objectMapper;
    }

    /**
     * Creates an Emissions Report from either previous report or data from FRS
     *
     * @param facilityEisProgramId
     * @param reportDto
     * @return
     */
    @PostMapping(value = "/facility/{facilityEisProgramId}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmissionsReportDto> create(
        @NotNull @PathVariable String facilityEisProgramId,
        @NotBlank @RequestPart("workbook") MultipartFile workbook,
        @NotNull @RequestPart("metadata") EmissionsReportStarterDto reportDto) {

        this.securityService.facilityEnforcer().enforceProgramId(facilityEisProgramId);

        reportDto.setEisProgramId(facilityEisProgramId);

        if (reportDto.getYear() == null) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT, "Reporting Year must be set.");
        }

        EmissionsReportDto result = null;
        HttpStatus status = HttpStatus.NO_CONTENT;

        try (TempFile tempFile = TempFile.from(workbook.getInputStream(), workbook.getOriginalFilename())) {

            LOGGER.debug("Workbook filename {}", tempFile.getFileName());
            LOGGER.debug("ReportDto {}", reportDto);

            result = this.uploadService.saveBulkWorkbook(reportDto, tempFile);

            status = HttpStatus.OK;

        } catch (IOException e) {

            throw new IllegalStateException(e);
        }

        return new ResponseEntity<>(result, status);
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

        EmissionsReportDto result = createEmissionsReportDto(reportDto);

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

    private EmissionsReportDto createEmissionsReportDto(EmissionsReportStarterDto reportDto) {
        EmissionsReportDto result;

        String facilityEisProgramId = reportDto.getEisProgramId();

        switch (reportDto.getSource()) {
	        case previous:
	        	result = this.emissionsReportService.createEmissionReportCopy(facilityEisProgramId, reportDto.getYear());
	        	break;
	        case frs:
	        	result = this.emissionsReportService.createEmissionReportFromFrs(facilityEisProgramId, reportDto.getYear());
	        	break;
	        case fromScratch:
	        	result = this.emissionsReportService.createEmissionReport(reportDto);
	        	break;
	        default:
	        	result = null;
        }

        return result;
    }

    /**
     * Approve the specified reports and move to approved
     * @param reviewDTO
     * @return
     */
    @PostMapping(value = "/accept")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public ResponseEntity<List<EmissionsReportDto>> acceptReports(@NotNull @RequestBody ReviewDTO reviewDTO) {

        this.securityService.facilityEnforcer().enforceEntities(reviewDTO.reportIds, EmissionsReportRepository.class);

        List<EmissionsReportDto> result = emissionsReportService.acceptEmissionsReports(reviewDTO.reportIds, reviewDTO.comments);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Reject the specified reports and move back to in progress
     * @param reviewDTO
     * @return
     */
    @PostMapping(value = "/reject")
    @RolesAllowed(value = {AppRole.ROLE_REVIEWER})
    public ResponseEntity<List<EmissionsReportDto>> rejectReports(@NotNull @RequestBody ReviewDTO reviewDTO) {

        this.securityService.facilityEnforcer().enforceEntities(reviewDTO.reportIds, EmissionsReportRepository.class);

        List<EmissionsReportDto> result = emissionsReportService.rejectEmissionsReports(reviewDTO.reportIds, reviewDTO.comments);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Reset report status. Sets report status to in progress and validation status to unvalidated.
     * @param reportIds
     * @return
     */
    @PostMapping(value = "/reset")
    @RolesAllowed(value = {AppRole.ROLE_NEI_CERTIFIER})
    public ResponseEntity<List<EmissionsReportDto>> resetReports(@NotNull @RequestBody List<Long> reportIds) {

        this.securityService.facilityEnforcer().enforceEntities(reportIds, EmissionsReportRepository.class);

        List<EmissionsReportDto> result = emissionsReportStatusService.resetEmissionsReport(reportIds);

        this.reportService.createReportHistory(reportIds, ReportAction.REOPENED);

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

        return ResponseEntity.ok().body(result);
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
     * Delete a report for given id
     * @param reportId
     * @return
     */
    @DeleteMapping(value = "/{reportId}")
    public void deleteReport(@PathVariable Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        emissionsReportService.delete(reportId);
    }

    /**
     * Submits a report to CROMERR after the user authenticates through the widgets and generates and activityId
     *
     * @param activityId CROMERR Activity ID
     * @param reportId   Emissions Report Id
     * @return
     */
    @GetMapping(value = "/submitToCromerr")
    @RolesAllowed(AppRole.ROLE_NEI_CERTIFIER)
    public ResponseEntity<String> submitToCromerr(
        @NotBlank @RequestParam String activityId, @NotNull @RequestParam Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        String documentId = emissionsReportService.submitToCromerr(reportId, activityId);

        this.reportService.createReportHistory(reportId, ReportAction.SUBMITTED);

        return new ResponseEntity<>(documentId, HttpStatus.OK);
    }


    /**
     * Inserts a new emissions report, facility, sub-facility components, and emissions based on a JSON input string
     *
     * @param reportUpload
     * @return
     */
    @PostMapping(value = "/upload",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    // @RolesAllowed(value = {AppRole.ROLE_ADMIN})
    public ResponseEntity<EmissionsReportDto> uploadReport(@NotNull @RequestBody EmissionsReportBulkUploadDto reportUpload) {
        EmissionsReportDto savedReport = uploadService.saveBulkEmissionsReport(reportUpload);
        this.reportService.createReportHistory(savedReport.getId(), ReportAction.CREATED);
        return new ResponseEntity<>(savedReport, HttpStatus.OK);
    }

    /**
     * Testing method for generating upload JSON for a report
     * @param reportId
     * @return
     */
    @GetMapping(value = "/export/{reportId}")
    public ResponseEntity<EmissionsReportBulkUploadDto> exportReport(
        @NotNull @PathVariable Long reportId) {

        EmissionsReportBulkUploadDto result =
            uploadService.generateBulkUploadDto(reportId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(value = BulkReportValidationException.class)
    public ResponseEntity<JsonNode> bulkUploadValidationError(BulkReportValidationException exception) {

        ObjectNode objectNode = this.objectMapper.createObjectNode();
        objectNode.put("failed", true);
        ArrayNode arrayNode = objectNode.putArray("errors");
        exception.getErrors().forEach(error -> arrayNode.add(error));

        return ResponseEntity.badRequest().body(objectNode);
    }

    static class ReviewDTO {
    	private List<Long> reportIds;

    	private String comments;

		public List<Long> getReportIds() {
			return reportIds;
		}
		public void setReportIds(List<Long> reportIds) {
			this.reportIds = reportIds;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
    }
}
