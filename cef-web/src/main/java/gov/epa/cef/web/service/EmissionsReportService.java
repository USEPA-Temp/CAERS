package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

import java.util.List;

public interface EmissionsReportService {

    /**
     * Creates an emissions report from FRS data
     * @param facilityEisProgramId
     * @param year
     * @return
     */
    EmissionsReportDto createEmissionReportFromFrs(String facilityEisProgramId, short year);

    
    /**
     * Creates an emissions report from scratch
     * @param facilityEisProgramId
     * @param frsFacilityId
     * @param stateCode
     * @param year
     * @return
     */
    EmissionsReportDto createEmissionReport(String facilityEisProgramId, short year, String frsFacilityId, String stateCode);
    
    /**
     * Find reports for a given facility
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReportDto> findByFacilityEisProgramId(String facilityEisProgramId);

    /**
     * Find reports for a given facility and add a new emissions report record in memory for the current year if addReportForCurrentYear is true
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReportDto> findByFacilityEisProgramId(String facilityEisProgramId, boolean addReportForCurrentYear);

    /**
     * Find report by ID
     * @param id
     * @return
     */
    EmissionsReportDto findById(Long id);

    /**
     * Find the most recent report for a given facility
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    EmissionsReportDto findMostRecentByFacilityEisProgramId(String facilityEisProgramId);


    String submitToCromerr(Long emissionsReportId, String activityId) throws ApplicationException;


    /**
     * Create a copy of the emissions report for the current year based on the specified facility and year.  The copy of the report is NOT saved to the database.
     * @param facilityEisProgramId
     * @param currentReportYear The year of the report that is being created
     * @return
     */
    EmissionsReportDto createEmissionReportCopy(String facilityEisProgramId, short currentReportYear);

    /**
     * Save the emissions report to the database.
     * @param emissionsReport
     * @return
     */
    EmissionsReportDto saveEmissionReport(EmissionsReport emissionsReport);

    /**
     * Delete specified emissions report from database
     * @param id
     */
    void delete(Long id);
}
