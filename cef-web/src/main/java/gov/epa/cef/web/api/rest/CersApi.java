package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.CersXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/cers/")
public class CersApi {

    private final CersXmlService cersXmlService;

    private final SecurityService securityService;

    @Autowired
    CersApi(SecurityService securityService,
            CersXmlService cersXmlService) {

        this.securityService = securityService;
        this.cersXmlService = cersXmlService;
    }

    /**
     * Retrieve XML report for an Emissions Report
     *
     * @param reportId
     * @return
     */
    @GetMapping(value = "/emissionsReport/{reportId}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public void retrieveReportXml(@PathVariable Long reportId,
                                  HttpServletResponse response) throws IOException {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        OutputStream outputStream = response.getOutputStream();
        this.cersXmlService.writeCersXmlTo(reportId, outputStream);
        response.flushBuffer();
    }
}
