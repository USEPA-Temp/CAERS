package gov.epa.cef.web.service;

import gov.epa.cef.web.api.rest.EmissionsReportApi.ReviewDTO;
import gov.epa.cef.web.domain.EmissionsReport;

import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.dto.EmissionsReportStarterDto;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface EmissionsReportService {

    /**
     * Creates an emissions report from scratch
     * @param reportDto
     * @return
     */
    EmissionsReportDto createEmissionReport(EmissionsReportStarterDto reportDto);

    /**
     * Find reports for a given facility
     * @param masterFacilityRecordId
     * @return
     */
    List<EmissionsReportDto> findByMasterFacilityRecordId(Long masterFacilityRecordId);

    /**
     * Find reports for a given facility and add a new emissions report record in memory for the current year if addReportForCurrentYear is true
     * @param masterFacilityRecordId
     * @return
     */
    List<EmissionsReportDto> findByMasterFacilityRecordId(Long masterFacilityRecordId, boolean addReportForCurrentYear);

    /**
     * Find report by ID
     * @param id
     * @return
     */
    EmissionsReportDto findById(Long id);

    /**
     * Find report by ID
     * @param id
     * @return
     */
    Optional<EmissionsReport> retrieve(long id);

    /**
     * Find report by facility id and year
     * @return
     */
    Optional<EmissionsReport> retrieveByMasterFacilityRecordIdAndYear(@NotBlank Long masterFacilityRecordId, int year);

    /**
     * Find the most recent report for a given facility
     * @param masterFacilityRecordId
     * @return
     */
    EmissionsReportDto findMostRecentByMasterFacilityRecordId(Long masterFacilityRecordId);


    String submitToCromerr(Long emissionsReportId, String activityId) throws ApplicationException;


    /**
     * Create a copy of the emissions report for the current year based on the specified facility and year.  The copy of the report is NOT saved to the database.
     * @param masterFacilityRecordId
     * @param currentReportYear The year of the report that is being created
     * @return
     */
    EmissionsReportDto createEmissionReportCopy(Long masterFacilityRecordId, short currentReportYear);

    /**
     * Save the emissions report to the database.
     * @param emissionsReport
     * @return
     */
    EmissionsReportDto saveAndAuditEmissionsReport(EmissionsReport emissionsReport, ReportAction reportAction);

    /**
     * Delete specified emissions report from database
     * @param id
     */
    void delete(Long id);

	List<EmissionsReportDto> acceptEmissionsReports(List<Long> reportIds, String comments);

	List<EmissionsReportDto> rejectEmissionsReports(ReviewDTO reviewDTO);

    /**
     * Update an existing Emissions Report from a DTO
     */
    EmissionsReportDto updateSubmitted(long reportId, boolean submitted);

}
