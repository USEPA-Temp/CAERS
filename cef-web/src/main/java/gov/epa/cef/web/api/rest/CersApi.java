package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.CersXmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotNull;

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
    public ResponseEntity<StreamingResponseBody> retrieveReportXml(
            @NotNull @PathVariable("reportId") Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        return new ResponseEntity<>(outputStream -> {

            this.cersXmlService.writeCersXmlTo(reportId, outputStream);

        }, HttpStatus.OK);
    }
}
