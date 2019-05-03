package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.report.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@Service
public class ReportService {
	
	@Autowired
	private EmissionsReportRepository erRepo;
	
	/**
	 * Find reports for a given facility
	 * @param facilityId {@link ProgramFacility}'s programId
	 * @return
	 */
	public List<EmissionsReport> findByFacilityId(String facilityId) {
		
		return erRepo.findByFacilityId(facilityId);
	};

}
