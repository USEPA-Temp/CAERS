package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * API for retrieving facility site information related to reports.
 * @author tfesperm
 *
 */
@RestController
@RequestMapping("/api/facilitySite")
public class FacilitySiteApi {

    private final FacilitySiteService facilityService;

    private final SecurityService securityService;

    @Autowired
    FacilitySiteApi(SecurityService securityService,
                    FacilitySiteService facilityService) {

        this.securityService = securityService;
        this.facilityService = facilityService;
    }

    /**
     * Retrieve a facility site by ID
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/{facilitySiteId}")
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySite(@NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        FacilitySiteDto  facilitySiteDto= facilityService.findById(facilitySiteId);
        return new ResponseEntity<>(facilitySiteDto, HttpStatus.OK);
    }

    /**
     * Retrieve a facility site by eis program ID and report ID
     * @param reportId
     * @param eisProgramId
     * @return
     */
    @GetMapping(value = "/report/{reportId}/facility/{eisProgramId}")
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySiteByProgramIdAndReportId(
        @NotNull @PathVariable Long reportId, @NotNull @PathVariable String eisProgramId) {

        this.securityService.facilityEnforcer().enforceProgramId(eisProgramId);

        FacilitySiteDto result = facilityService.findByEisProgramIdAndReportId(eisProgramId, reportId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
