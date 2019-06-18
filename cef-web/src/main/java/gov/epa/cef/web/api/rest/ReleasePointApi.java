package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.dto.ReleasePointDto;

@RestController
@RequestMapping("/api/releasePoint")
public class ReleasePointApi {

    @Autowired
    private ReleasePointService releasePointService;

    /**
     * Retrieve a release point by ID
     * @param pointId
     * @return
     */
    @GetMapping(value = "/{pointId}")
    @ResponseBody
    public ResponseEntity<ReleasePointDto> retrieveReleasePoint(@PathVariable Long pointId) {

        ReleasePointDto result = releasePointService.retrieveById(pointId);
        return new ResponseEntity<ReleasePointDto>(result, HttpStatus.OK);
    }
 
    /**
     * Retrieve Release Points for a facility
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facility/{facilitySiteId}")
    @ResponseBody
    public ResponseEntity<List<ReleasePointDto>> retrieveFacilityReleasePoints(@PathVariable Long facilitySiteId) {

        List<ReleasePointDto> result = releasePointService.retrieveByFacilitySiteId(facilitySiteId);
        return new ResponseEntity<List<ReleasePointDto>>(result, HttpStatus.OK);
    }
}
