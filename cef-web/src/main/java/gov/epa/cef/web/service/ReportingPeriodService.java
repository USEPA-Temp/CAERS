package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.EmissionBulkEntryHolderDto;
import gov.epa.cef.web.service.dto.ReportingPeriodBulkEntryDto;
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

    /**
     * Retrieve Reporting Periods for Bulk Entry for a specific facility site
     * @param facilitySiteId
     * @return
     */
    public List<ReportingPeriodBulkEntryDto> retrieveBulkEntryReportingPeriodsForFacilitySite(Long facilitySiteId);

    /**
     * Update the throughput for multiple Reporting Periods at once
     * @param facilitySiteId
     * @param dtos
     * @return
     */
    public List<EmissionBulkEntryHolderDto> bulkUpdate(Long facilitySiteId, List<ReportingPeriodBulkEntryDto> dtos);

}