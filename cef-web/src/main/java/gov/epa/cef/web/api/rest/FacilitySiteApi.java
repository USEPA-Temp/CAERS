package gov.epa.cef.web.api.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

import gov.epa.cef.web.domain.Facility;
import gov.epa.cef.web.service.FacilityService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilityMapper;

/**
 * API for retrieving facility site information related to reports.
 * @author tfesperm
 *
 */
@RestController
@RequestMapping("/api/facilitySite")
public class FacilitySiteApi {

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private FacilityMapper facilityMapper;

    /**
     * Retrieve a facility site by ID
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/{facilityId}")
    @ResponseBody
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySite(@PathVariable Long facilityId) {

        Facility facility =  facilityService.findById(facilityId);
        FacilitySiteDto result = facilityMapper.toDto(facility);
        return new ResponseEntity<FacilitySiteDto>(result, HttpStatus.OK);
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
        Collection<Facility> facilities = facilityService.findByState(stateCode);
        Collection<FacilitySiteDto> result = facilities.stream()
                .map(facility -> facilityMapper.toDto(facility))
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<FacilitySiteDto>>(result, HttpStatus.OK);     
    }

    /**
     * Retrieve a facility site by eis program ID and report ID
     * @param reportId
     * @param eisProgramId
     * @return
     */
    @GetMapping(value = "/report/{reportId}/facility/{eisProgramId}")
    @ResponseBody
    public ResponseEntity<FacilitySiteDto> retrieveFacility(@PathVariable Long reportId, @PathVariable String eisProgramId) {

        FacilitySiteDto result = facilityMapper.toDto(facilityService.findByEisProgramIdAndReportId(eisProgramId, reportId));

        return new ResponseEntity<FacilitySiteDto>(result, HttpStatus.OK);
    }

}
