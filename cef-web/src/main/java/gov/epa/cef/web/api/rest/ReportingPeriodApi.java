package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ReportingPeriodService;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;

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
     * Update an reporting period
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ReportingPeriodDto> updateReportingPeriod(
        @NotNull @PathVariable Long id, @NotNull @RequestBody ReportingPeriodDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(id, ReportingPeriodRepository.class);

        ReportingPeriodDto result = reportingPeriodService.update(dto.withId(id));

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
}
