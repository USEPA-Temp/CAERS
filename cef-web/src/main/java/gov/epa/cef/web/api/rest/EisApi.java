package gov.epa.cef.web.api.rest;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.EisDataCategory;
import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.service.dto.EisSubmissionType;
import gov.epa.cef.web.service.impl.EisXmlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

@RestController
@RequestMapping("/api/eis")
public class EisApi {

    private final EisXmlServiceImpl eisXmlService;

    private final SecurityService securityService;

    @Autowired
    EisApi(SecurityService securityService,
           EisXmlServiceImpl eisXmlService) {

        this.securityService = securityService;
        this.eisXmlService = eisXmlService;
    }

    @GetMapping(value = "/emissionsReport/{reportId}", produces = MediaType.APPLICATION_XML_VALUE)
    public void retrieveEisXml(@PathVariable Long reportId,
                               HttpServletResponse response) throws IOException {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        OutputStream outputStream = response.getOutputStream();

        EisHeaderDto eisHeader = new EisHeaderDto();
        eisHeader.setAuthorName(String.format("%s %s", appUser.getFirstName(), appUser.getLastName()));
        eisHeader.setOrganizationName(appUser.getOrganization());
        eisHeader.setDataCategory(EisDataCategory.FacilityInventory);
        eisHeader.setSubmissionType(EisSubmissionType.QA);
        eisHeader.setEmissionReports(Collections.singletonList(reportId));

        this.eisXmlService.writeEisXmlTo(eisHeader, outputStream);
        response.flushBuffer();
    }
}
