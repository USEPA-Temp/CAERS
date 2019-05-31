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

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.Facility;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.FacilityService;
import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.mapper.EmissionsUnitMapper;
import gov.epa.cef.web.service.mapper.FacilityMapper;
import gov.epa.cef.web.service.mapper.ReleasePointMapper;

@RestController
@RequestMapping("/api/facilitySite")
public class FacilitySiteApi {

    @Autowired
    private EmissionsUnitService emissionsUnitService;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private ReleasePointService releasePointService;

    /**
     * Retrieve a facility site by ID
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/{facilityId}")
    @ResponseBody
    public ResponseEntity<FacilitySiteDto> retrieveFacilitySite(@PathVariable Long facilityId) {

        Facility facility =  facilityService.findById(facilityId);
        FacilitySiteDto result = FacilityMapper.INSTANCE.toDto(facility);
        return new ResponseEntity<FacilitySiteDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Emissions Units for a facility
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/{facilityId}/emissionsUnits")
    @ResponseBody
    public ResponseEntity<Collection<EmissionsUnitDto>> retrieveFacilityEmissionsUnits(@PathVariable Long facilityId) {

        Collection<EmissionsUnit> units = emissionsUnitService.retrieveByFacilityId(facilityId);
        Collection<EmissionsUnitDto> result = units.stream()
                .map(unit -> EmissionsUnitMapper.INSTANCE.emissionsUnitToDto(unit))
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<EmissionsUnitDto>>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Release Points for a facility
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/{facilityId}/releasePoints")
    @ResponseBody
    public ResponseEntity<Collection<ReleasePointDto>> retrieveFacilityReleasePoints(@PathVariable Long facilityId) {

        Collection<ReleasePoint> releasePoints = releasePointService.retrieveByFacilityId(facilityId);
        Collection<ReleasePointDto> result = releasePoints.stream()
                .map(releasePoint -> ReleasePointMapper.INSTANCE.toDto(releasePoint))
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<ReleasePointDto>>(result, HttpStatus.OK);
    }
}
