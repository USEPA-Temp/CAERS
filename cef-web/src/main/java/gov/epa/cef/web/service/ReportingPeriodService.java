package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.ReportingPeriodDto;

public interface ReportingPeriodService {

    /**
     * Update a Reporting Period
     * @param dto
     * @return
     */
    public ReportingPeriodDto update(ReportingPeriodDto dto);

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