package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.domain.report.EmissionsReport;
import gov.epa.cef.web.service.ReportService;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@RestController
@RequestMapping("/api/report")
public class ReportApi {
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * Retrieve reports for a given facility
	 * @param facilityId {@link ProgramFacility}'s programId
	 * @return
	 */
	@GetMapping(value = "/facility/{facilityId}")
	@ResponseBody
	public ResponseEntity<List<EmissionsReport>> retrieveReportsForFacility(@PathVariable String facilityId) {

		List<EmissionsReport> result = reportService.findByFacilityId(facilityId);
		
		return new ResponseEntity<List<EmissionsReport>>(result, HttpStatus.OK);
	}

	/**
	 * Retrieve current report for a given facility
	 * @param facilityId {@link ProgramFacility}'s programId
	 * @return
	 */
	@GetMapping(value = "/facility/{facilityId}/current")
	@ResponseBody
	public ResponseEntity<EmissionsReport> retrieveCurrentReportForFacility(@PathVariable String facilityId) {

		EmissionsReport result = reportService.findMostRecentByFacility(facilityId);
		
		return new ResponseEntity<EmissionsReport>(result, HttpStatus.OK);
	}
}
