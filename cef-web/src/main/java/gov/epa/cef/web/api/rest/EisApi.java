package gov.epa.cef.web.api.rest;

import com.google.common.base.Preconditions;
import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.EisDataCriteria;
import gov.epa.cef.web.service.dto.EisDataListDto;
import gov.epa.cef.web.service.dto.EisDataReportDto;
import gov.epa.cef.web.service.dto.EisDataStatsDto;
import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.dto.simple.SimpleStringValue;
import gov.epa.cef.web.service.impl.EisTransmissionServiceImpl;
import gov.epa.cef.web.service.impl.EisXmlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.NotNull;
import java.util.Collections;

@RestController
@RequestMapping("/api/eis")
public class EisApi {

    private final EisTransmissionServiceImpl eisTransmissionService;

    private final EisXmlServiceImpl eisXmlService;

    private final SecurityService securityService;

    @Autowired
    EisApi(SecurityService securityService,
           EisTransmissionServiceImpl eisTransmissionService,
           EisXmlServiceImpl eisXmlService) {

        this.securityService = securityService;
        this.eisXmlService = eisXmlService;

        this.eisTransmissionService = eisTransmissionService;
    }

    @Validated(EisHeaderDto.EisApiGroup.class)
    @PostMapping(value = "/transaction",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EisDataListDto> createTransaction(@NotNull @RequestBody EisHeaderDto eisHeader) {

        Preconditions.checkArgument(eisHeader.getSubmissionStatus() != null,
            "SubmissionStatus can not be null.");

        Preconditions.checkArgument(eisHeader.getEmissionsReports().size() > 0,
            "EmissionsReportIds must contain at lease one ID.");

        this.securityService.facilityEnforcer()
            .enforceEntities(eisHeader.getEmissionsReports(), EmissionsReportRepository.class);

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        eisHeader.withAuthorName(String.format("%s %s", appUser.getFirstName(), appUser.getLastName()))
            .withOrganizationName(appUser.getOrganization());

        EisDataListDto result = this.eisTransmissionService.submitReports(eisHeader);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PutMapping(value = "/emissionsReport/{id}/passed",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EisDataReportDto> putEisPassedStatus(@NotNull @PathVariable("id") Long reportId,
                                                          @NotNull @RequestBody SimpleStringValue passed) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        EisDataReportDto result =
            this.eisTransmissionService.updateReportEisPassedStatus(reportId, passed.getValue());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/emissionsReport/{id}/comment",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EisDataReportDto> putEisComment(@NotNull @PathVariable("id") Long reportId,
                                                          @NotNull @RequestBody SimpleStringValue comment) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        EisDataReportDto result =
            this.eisTransmissionService.updateReportComment(reportId, comment.getValue());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/emissionsReport",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EisDataListDto> retrieveEisDataList(@NotNull @RequestParam(value = "year") Integer year,
                                                              @RequestParam(value = "status", required = false) EisSubmissionStatus status) {

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        EisDataCriteria criteria = new EisDataCriteria()
            .withAgencyCode(appUser.getClientId())
            .withReportingYear(year)
            .withSubmissionStatus(status);

        EisDataListDto result = this.eisTransmissionService.retrieveSubmittableData(criteria);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/emissionsReport/stats",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EisDataStatsDto> retrieveEisDataStats(@NotNull @RequestParam(value = "year") Short year) {

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        EisDataStatsDto result = this.eisTransmissionService.retrieveStatInfo(appUser.getClientId(), year);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/emissionsReport/{reportId}",
        produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StreamingResponseBody> retrieveEisXml(
        @NotNull @PathVariable("reportId") Long reportId) {

        this.securityService.facilityEnforcer().enforceEntity(reportId, EmissionsReportRepository.class);

        ApplicationUser appUser = this.securityService.getCurrentApplicationUser();

        EisHeaderDto eisHeader = new EisHeaderDto()
            .withAuthorName(String.format("%s %s", appUser.getFirstName(), appUser.getLastName()))
            .withOrganizationName(appUser.getOrganization())
            .withSubmissionStatus(EisSubmissionStatus.ProdFacility)
            .withEmissionsReports(Collections.singletonList(reportId));

        return new ResponseEntity<>(outputStream -> {

            this.eisXmlService.writeEisXmlTo(eisHeader, outputStream);

        }, HttpStatus.OK);
    }
}
