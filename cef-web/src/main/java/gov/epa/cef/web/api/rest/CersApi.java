package gov.epa.cef.web.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.CersXmlService;

@RestController
@RequestMapping("/api/cers/")
public class CersApi {

    @Autowired
    private CersXmlService cersXmlService;

    /**
     * Retrieve XML report for an Emissions Report
     * @param reportId
     * @return
     */
    @GetMapping(value = "/emissionsReport/{reportId}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<String> retrieveReportXml(@PathVariable Long reportId) {
        String result = cersXmlService.retrieveCersXml(reportId);
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

}
