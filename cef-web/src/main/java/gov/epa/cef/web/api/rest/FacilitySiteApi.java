package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final FacilitySiteMapper mapper;

    @Autowired
    FacilitySiteApi(FacilitySiteService facilityService, FacilitySiteMapper mapper) {

        super();

        this.facilityService = facilityService;
        this.mapper = mapper;
    }

    /**
     * Retrieve a facility site by ID
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/{facilitySiteId}")
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySite(@PathVariable Long facilitySiteId) {
        FacilitySiteDto  facilitySiteDto= facilityService.findById(facilitySiteId);
        return new ResponseEntity<FacilitySiteDto>(facilitySiteDto, HttpStatus.OK);
    }

    /**
     * Retrieve a facility site by eis program ID and report ID
     * @param reportId
     * @param eisProgramId
     * @return
     */
    @GetMapping(value = "/report/{reportId}/facility/{eisProgramId}")
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySiteByProgramIdAndReportId(@PathVariable Long reportId, @PathVariable String eisProgramId) {

        FacilitySiteDto result = facilityService.findByEisProgramIdAndReportId(eisProgramId, reportId);

        return new ResponseEntity<FacilitySiteDto>(result, HttpStatus.OK);
    }

    /**
     * Creates a facility site from a previously created EmissionsReport
     * @param reportId
     * @param inputFacilitySite
     * @return
     */
    @PostMapping(value = "/report/{reportId}/facility",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacilitySiteDto> createFacilitySite(@PathVariable Long reportId,
                                                              @NotNull @RequestBody FacilitySiteDto inputFacilitySite) {

        if (StringUtils.isBlank(inputFacilitySite.getEisProgramId())) {
            throw new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT, "EIS Program ID can not be blank.");
        }

        FacilitySiteDto result = this.facilityService.copyFromFrs(reportId, inputFacilitySite.getEisProgramId())
            .map(this.mapper::toDto)
            .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.E_INVALID_REQUEST,
                "Unable to create FacilitySite from "));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
