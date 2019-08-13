package gov.epa.cef.web.api.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.ReportingPeriodService;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;

@RestController
@RequestMapping("/api/reportingPeriod")
public class ReportingPeriodApi {

    @Autowired
    private ReportingPeriodService reportingPeriodService;

    /**
     * Update an reporting period
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ReportingPeriodDto> updateReportingPeriod(@PathVariable Long id, @RequestBody ReportingPeriodDto dto) {

        ReportingPeriodDto result = reportingPeriodService.update(dto);

        return new ResponseEntity<ReportingPeriodDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve a reporting period by id
     * @param periodId
     * @return
     */
    @GetMapping(value = "/{periodId}")
    @ResponseBody
    public ResponseEntity<ReportingPeriodDto> retrieveReportingPeriod(@PathVariable Long periodId) {

        ReportingPeriodDto result = reportingPeriodService.retrieveById(periodId);
        return new ResponseEntity<ReportingPeriodDto>(result, HttpStatus.OK);
    }
 
    /**
     * Retrieve Reporting Periods for an emissions process
     * @param processId
     * @return
     */
    @GetMapping(value = "/process/{processId}")
    @ResponseBody
    public ResponseEntity<Collection<ReportingPeriodDto>> retrieveReportingPeriodsForProcess(@PathVariable Long processId) {

        Collection<ReportingPeriodDto> result = reportingPeriodService.retrieveForEmissionsProcess(processId);
        return new ResponseEntity<Collection<ReportingPeriodDto>>(result, HttpStatus.OK);
    }
}
