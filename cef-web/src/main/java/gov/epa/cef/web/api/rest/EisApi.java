package gov.epa.cef.web.api.rest;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.EisDataCriteria;
import gov.epa.cef.web.service.dto.EisDataListDto;
import gov.epa.cef.web.service.dto.EisDataStatsDto;
import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.impl.EisTransmissionServiceImpl;
import gov.epa.cef.web.service.impl.EisXmlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotNull;
import java.util.Collections;

@RestController
@RequestMapping("/api/eis")
public class EisApi {

    private final EisXmlServiceImpl eisXmlService;

    private final EisTransmissionServiceImpl eisTransmissionService;

    private final SecurityService securityService;

    @Autowired
    EisApi(SecurityService securityService,
           EisTransmissionServiceImpl eisTransmissionService,
           EisXmlServiceImpl eisXmlService) {

        this.securityService = securityService;
        this.eisXmlService = eisXmlService;

        this.eisTransmissionService = eisTransmissionService;
    }

    @GetMapping(value = "/emissionsReports", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EisDataListDto> retrieveEisDataList(@NotNull @RequestParam("year") Integer year,
                                                              @RequestParam("status") EisSubmissionStatus status) {

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        EisDataCriteria criteria = new EisDataCriteria()
            .withAgencyCode(appUser.getClientId())
            .withReportingYear(year)
            .withSubmissionStatus(status);

        EisDataListDto result = this.eisTransmissionService.retrieveSubmittableData(criteria);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/emissionsReports/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EisDataStatsDto> retrieveEisDataStats() {

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        EisDataStatsDto result = this.eisTransmissionService.retrieveStatInfo(appUser.getClientId());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/emissionsReports/{reportId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StreamingResponseBody> retrieveEisXml(
        @NotNull @PathVariable("reportId") Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        EisHeaderDto eisHeader = new EisHeaderDto()
            .withAuthorName(String.format("%s %s", appUser.getFirstName(), appUser.getLastName()))
            .withOrganizationName(appUser.getOrganization())
            .withSubmissionStatus(EisSubmissionStatus.ProdFacility)
            .withEmissionReports(Collections.singletonList(reportId));

        return new ResponseEntity<>(outputStream -> {

            this.eisXmlService.writeEisXmlTo(eisHeader, outputStream);

        }, HttpStatus.OK);
    }
}
