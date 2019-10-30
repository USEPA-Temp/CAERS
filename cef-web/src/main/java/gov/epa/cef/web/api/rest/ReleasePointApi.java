package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/releasePoint")
public class ReleasePointApi {

    private final ReleasePointService releasePointService;

    private final SecurityService securityService;

    @Autowired
    ReleasePointApi(SecurityService securityService,
                    ReleasePointService releasePointService) {

        this.releasePointService = releasePointService;
        this.securityService = securityService;
    }

    /**
     * Retrieve a release point by ID
     * @param pointId
     * @return
     */
    @GetMapping(value = "/{pointId}")
    public ResponseEntity<ReleasePointDto> retrieveReleasePoint(@NotNull @PathVariable Long pointId) {

        this.securityService.facilityEnforcer().enforceEntity(pointId, ReleasePointRepository.class);

        ReleasePointDto result = releasePointService.retrieveById(pointId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Release Points for a facility
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facility/{facilitySiteId}")
    public ResponseEntity<List<ReleasePointDto>> retrieveFacilityReleasePoints(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        List<ReleasePointDto> result = releasePointService.retrieveByFacilitySiteId(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
