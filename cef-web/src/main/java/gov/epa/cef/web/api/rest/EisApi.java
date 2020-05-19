package gov.epa.cef.web.api.rest;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.EisDataCategory;
import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.service.dto.EisSubmissionType;
import gov.epa.cef.web.service.impl.EisXmlServiceImpl;
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
    public ResponseEntity<StreamingResponseBody> retrieveEisXml(
            @NotNull @PathVariable("reportId") Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        EisHeaderDto eisHeader = new EisHeaderDto()
                .withAuthorName(String.format("%s %s", appUser.getFirstName(), appUser.getLastName()))
                .withOrganizationName(appUser.getOrganization())
                .withDataCategory(EisDataCategory.FacilityInventory)
                .withSubmissionType(EisSubmissionType.QA)
                .withEmissionReports(Collections.singletonList(reportId));

        return new ResponseEntity<>(outputStream -> {

            this.eisXmlService.writeEisXmlTo(eisHeader, outputStream);

        }, HttpStatus.OK);
    }
}
