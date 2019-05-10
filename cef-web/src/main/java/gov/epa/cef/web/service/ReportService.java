package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.domain.report.EmissionsReport;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

public interface ReportService {

	/**
	 * Find reports for a given facility
	 * @param facilityId {@link ProgramFacility}'s programId
	 * @return
	 */
	List<EmissionsReport> findByFacilityId(String facilityId);

	/**
	 * Find the most recent report for a given facility
	 * @param facilityId {@link ProgramFacility}'s programId 
	 * @return
	 */
	EmissionsReport findMostRecentByFacility(String facilityId);

}