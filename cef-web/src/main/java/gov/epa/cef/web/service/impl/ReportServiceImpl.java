package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.report.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.ReportService;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private EmissionsReportRepository erRepo;
	
	/* (non-Javadoc)
	 * @see gov.epa.cef.web.service.impl.ReportService#findByFacilityId(java.lang.String)
	 */
	@Override
	public List<EmissionsReport> findByFacilityId(String facilityId) {
		
		return erRepo.findByFacilityId(facilityId);
	};

	/* (non-Javadoc)
	 * @see gov.epa.cef.web.service.impl.ReportService#findMostRecentByFacility(java.lang.String)
	 */
	@Override
	public EmissionsReport findMostRecentByFacility(String facilityId) {
		
		return erRepo.findByFacilityId(facilityId, new Sort(Sort.Direction.DESC, "year"))
				.stream().findFirst().orElse(null);
	}

}
