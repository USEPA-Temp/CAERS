package gov.epa.cef.web.api.rest;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;

/**
 * API for retrieving facility site information related to reports.
 * @author tfesperm
 *
 */
@RestController
@RequestMapping("/api/facilitySite")
public class FacilitySiteApi {

    @Autowired
    private FacilitySiteService facilityService;

    /**
     * Retrieve a facility site by ID
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/{facilityId}")
    @ResponseBody
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySite(@PathVariable Long facilitySiteId) {
        FacilitySiteDto  facilitySiteDto= facilityService.findById(facilitySiteId);
        return new ResponseEntity<FacilitySiteDto>(facilitySiteDto, HttpStatus.OK);
    }

    /**
     * Retrieve facility sites by state code
     * @param stateCode
     * @return
     */
    //TODO: add year to parameters
    @GetMapping(value = "/state/{stateCode}")
    @ResponseBody
    public ResponseEntity<Collection<FacilitySiteDto>> retrieveFacilitiesByState(@PathVariable String stateCode) {
        List<FacilitySiteDto> facilities = facilityService.findByState(stateCode);
        return new ResponseEntity<Collection<FacilitySiteDto>>(facilities, HttpStatus.OK);     
    }

    /**
     * Retrieve a facility site by eis program ID and report ID
     * @param reportId
     * @param eisProgramId
     * @return
     */
    @GetMapping(value = "/report/{reportId}/facility/{eisProgramId}")
    @ResponseBody
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySiteByProgramIdAndReportId(@PathVariable Long reportId, @PathVariable String eisProgramId) {

        FacilitySiteDto result = facilityService.findByEisProgramIdAndReportId(eisProgramId, reportId);

        return new ResponseEntity<FacilitySiteDto>(result, HttpStatus.OK);
    }

}
