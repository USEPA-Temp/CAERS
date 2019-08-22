package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@RestController
@RequestMapping("/api/emissionsReport")
public class EmissionsReportApi {

    @Autowired
    private EmissionsReportService emissionsReportService;

    /**
     * Retrieve report by ID
     * @param reportId
     * @return
     */
    @GetMapping(value = "/{reportId}")
    @ResponseBody
    public ResponseEntity<EmissionsReportDto> retrieveReport(@PathVariable Long reportId) {

        EmissionsReportDto result = emissionsReportService.findById(reportId);

        return new ResponseEntity<EmissionsReportDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve reports for a given facility
     * @param facilityEisProgramId {@link ProgramFacility}'s programId
     * @return
     */
    @GetMapping(value = "/facility/{facilityEisProgramId}")
    @ResponseBody
    public ResponseEntity<List<EmissionsReportDto>> retrieveReportsForFacility(@PathVariable String facilityEisProgramId) {

        List<EmissionsReportDto> result = emissionsReportService.findByFacilityEisProgramId(facilityEisProgramId, true);

        return new ResponseEntity<List<EmissionsReportDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve current report for a given facility
     * @param facilityId {@link ProgramFacility}'s programId
     * @return
     */
    @GetMapping(value = "/facility/{facilityEisProgramId}/current")
    @ResponseBody
    public ResponseEntity<EmissionsReportDto> retrieveCurrentReportForFacility(@PathVariable String facilityEisProgramId) {

        EmissionsReportDto result = emissionsReportService.findMostRecentByFacilityEisProgramId(facilityEisProgramId);

        return new ResponseEntity<EmissionsReportDto>(result, HttpStatus.OK);
    }

    
    /**
     * Submits a report to CROMERR after the user authenticates through the widgets and generates and activityId
     * @param activityId CROMERR Activity ID
     * @param reportId Emissions Report Id
     * @return
     */
    @GetMapping(value = "/submitToCromerr")
    @ResponseBody
    public ResponseEntity<String> submitToCromerr(@RequestParam String activityId, @RequestParam Long reportId) {

        String documentId= emissionsReportService.submitToCromerr(reportId, activityId);

        return new ResponseEntity<String>(documentId, HttpStatus.OK);
    }


    @GetMapping(value = "/create/{facilityEisProgramId}/{reportYear}")
    public ResponseEntity<EmissionsReportDto> create(@PathVariable String facilityEisProgramId, @PathVariable Integer reportYear) {

        EmissionsReport reportCopy = emissionsReportService.createEmissionReportCopy(facilityEisProgramId, reportYear);
        EmissionsReportDto result = new EmissionsReportDto();
        if (reportCopy != null) {
        	result = emissionsReportService.saveEmissionReport(reportCopy);
        }

        return new ResponseEntity<EmissionsReportDto>(result, HttpStatus.OK);
    }
}
