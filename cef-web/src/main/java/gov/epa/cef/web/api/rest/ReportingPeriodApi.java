package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ReportingPeriodService;
import gov.epa.cef.web.service.dto.ReportingPeriodBulkEntryDto;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import gov.epa.cef.web.service.dto.ReportingPeriodUpdateResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reportingPeriod")
public class ReportingPeriodApi {

    private final ReportingPeriodService reportingPeriodService;

    private final SecurityService securityService;

    @Autowired
    ReportingPeriodApi(SecurityService securityService,
                              ReportingPeriodService reportingPeriodService) {

        this.reportingPeriodService = reportingPeriodService;
        this.securityService = securityService;
    }

    /**
     * Create a new Reporting Period
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<ReportingPeriodDto> createReportingPeriod(
        @NotNull @RequestBody ReportingPeriodDto dto) {

        this.securityService.facilityEnforcer()
            .enforceEntity(dto.getEmissionsProcessId(), EmissionsProcessRepository.class);

        ReportingPeriodDto result = reportingPeriodService.create(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update an reporting period
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ReportingPeriodUpdateResponseDto> updateReportingPeriod(
        @NotNull @PathVariable Long id, @NotNull @RequestBody ReportingPeriodDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(id, ReportingPeriodRepository.class);

        ReportingPeriodUpdateResponseDto result = reportingPeriodService.update(dto.withId(id));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve a reporting period by id
     * @param periodId
     * @return
     */
    @GetMapping(value = "/{periodId}")
    public ResponseEntity<ReportingPeriodDto> retrieveReportingPeriod(@NotNull @PathVariable Long periodId) {

        this.securityService.facilityEnforcer().enforceEntity(periodId, ReportingPeriodRepository.class);

        ReportingPeriodDto result = reportingPeriodService.retrieveById(periodId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Reporting Periods for an emissions process
     * @param processId
     * @return
     */
    @GetMapping(value = "/process/{processId}")
    public ResponseEntity<Collection<ReportingPeriodDto>> retrieveReportingPeriodsForProcess(
        @NotNull @PathVariable Long processId) {

        this.securityService.facilityEnforcer().enforceEntity(processId, EmissionsProcessRepository.class);

        Collection<ReportingPeriodDto> result = reportingPeriodService.retrieveForEmissionsProcess(processId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Reporting Periods for bulk entry by Report Id
     * @param reportId
     * @return
     */
    @GetMapping(value = "/bulkEntry/{facilitySiteId}")
    public ResponseEntity<Collection<ReportingPeriodBulkEntryDto>> retrieveBulkEntryReportingPeriodsForFacilitySite(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceEntity(facilitySiteId, FacilitySiteRepository.class);

        Collection<ReportingPeriodBulkEntryDto> result = reportingPeriodService.retrieveBulkEntryReportingPeriodsForFacilitySite(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update the throughput for multiple Reporting Periods at once
     * @param dtos
     * @return
     */
    @PutMapping(value = "/bulkEntry")
    public ResponseEntity<Collection<ReportingPeriodUpdateResponseDto>> bulkUpdate(
        @NotNull @RequestBody List<ReportingPeriodBulkEntryDto> dtos) {

        List<Long> periodIds = dtos.stream().map(ReportingPeriodBulkEntryDto::getReportingPeriodId).collect(Collectors.toList());

        this.securityService.facilityEnforcer().enforceEntities(periodIds, ReportingPeriodRepository.class);

        Collection<ReportingPeriodUpdateResponseDto> result = reportingPeriodService.bulkUpdate(dtos);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
