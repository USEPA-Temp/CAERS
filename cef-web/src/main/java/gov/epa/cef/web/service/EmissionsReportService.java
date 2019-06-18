package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.EmissionsReportDto;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

public interface EmissionsReportService {

    /**
     * Find reports for a given facility
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    List<EmissionsReportDto> findByFacilityEisProgramId(String facilityEisProgramId);

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

}