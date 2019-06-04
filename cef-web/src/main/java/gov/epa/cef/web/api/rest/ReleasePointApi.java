package gov.epa.cef.web.api.rest;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.mapper.ReleasePointMapper;

@RestController
@RequestMapping("/api/releasePoint")
public class ReleasePointApi {

    @Autowired
    private ReleasePointService releasePointService;

    @Autowired
    private ReleasePointMapper releasePointMapper;

    /**
     * Retrieve a release point by ID
     * @param pointId
     * @return
     */
    @GetMapping(value = "/{pointId}")
    @ResponseBody
    public ResponseEntity<ReleasePointDto> retrieveReleasePoint(@PathVariable Long pointId) {

        ReleasePoint releasePoint = releasePointService.retrieveById(pointId);
        ReleasePointDto result = releasePointMapper.toDto(releasePoint);
        return new ResponseEntity<ReleasePointDto>(result, HttpStatus.OK);
    }
 
    /**
     * Retrieve Release Points for a facility
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/facility/{facilityId}")
    @ResponseBody
    public ResponseEntity<Collection<ReleasePointDto>> retrieveFacilityReleasePoints(@PathVariable Long facilityId) {

        Collection<ReleasePoint> releasePoints = releasePointService.retrieveByFacilityId(facilityId);
        Collection<ReleasePointDto> result = releasePoints.stream()
                .map(releasePoint -> releasePointMapper.toDto(releasePoint))
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<ReleasePointDto>>(result, HttpStatus.OK);
    }
}
