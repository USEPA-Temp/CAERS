package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.OperatingDetailRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.OperatingDetailService;
import gov.epa.cef.web.service.ReportingPeriodService;
import gov.epa.cef.web.service.dto.OperatingDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/operatingDetail")
public class OperatingDetailApi {

    private final OperatingDetailService operatingDetailService;

    private final SecurityService securityService;

    private final ReportingPeriodService reportingPeriodService;

    @Autowired
    OperatingDetailApi(SecurityService securityService,
                       OperatingDetailService operatingDetailService,
                       ReportingPeriodService reportingPeriodService) {

        this.operatingDetailService = operatingDetailService;
        this.securityService = securityService;
        this.reportingPeriodService = reportingPeriodService;
    }

    /**
     * Update an Operating Detail
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<OperatingDetailDto> updateOperatingDetail(
        @NotNull @PathVariable Long id, @NotNull @RequestBody OperatingDetailDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(id, OperatingDetailRepository.class);

        OperatingDetailDto result = operatingDetailService.update(dto.withId(id));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
