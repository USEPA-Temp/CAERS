package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import gov.epa.cef.web.service.dto.ReportingPeriodUpdateResponseDto;

public interface ReportingPeriodService {

    /**
     * Create a new Reporting Period
     * @param dto
     * @return
     */
    public ReportingPeriodDto create(ReportingPeriodDto dto);

    /**
     * Update a Reporting Period
     * @param dto
     * @return
     */
    public ReportingPeriodUpdateResponseDto update(ReportingPeriodDto dto);

    /**
     * Retrieve Reporting Period by id
     * @param id
     * @return
     */
    ReportingPeriodDto retrieveById(Long id);

    /**
     * Retrieve Reporting Periods for an emissions process
     * @param processId
     * @return
     */
    List<ReportingPeriodDto> retrieveForEmissionsProcess(Long processId);

}