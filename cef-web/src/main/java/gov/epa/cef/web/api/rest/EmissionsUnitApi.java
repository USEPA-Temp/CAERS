package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/emissionsUnit")
public class EmissionsUnitApi {

    private final EmissionsUnitService emissionsUnitService;

    private final SecurityService securityService;

    public EmissionsUnitApi(SecurityService securityService,
                            EmissionsUnitService emissionsUnitService) {

        this.securityService = securityService;
        this.emissionsUnitService = emissionsUnitService;
    }

    /**
     * Retrieve a unit by it's ID
     * @param unitId
     * @return
     */
    @GetMapping(value = "/{unitId}")
    public ResponseEntity<EmissionsUnitDto> retrieveEmissionsUnit(@NotNull @PathVariable Long unitId) {

        this.securityService.facilityEnforcer().enforceEntity(unitId, EmissionsUnitRepository.class);

        EmissionsUnitDto emissionsUnit = emissionsUnitService.retrieveUnitById(unitId);

        return new ResponseEntity<>(emissionsUnit, HttpStatus.OK);
    }

    /**
     * Retrieve emissions unit of a facility
     * @param facilitySiteId
     * @return list of emissions unit
     */
    @GetMapping(value = "/facility/{facilitySiteId}")
    public ResponseEntity<List<EmissionsUnitDto>> retrieveEmissionsUnitsOfFacility(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        List<EmissionsUnitDto> emissionsUnits = emissionsUnitService.retrieveEmissionUnitsForFacility(facilitySiteId);

        return new ResponseEntity<>(emissionsUnits, HttpStatus.OK);
    }
    
    /**
     * Delete an emission unit for a given ID
     * @param unitId
     * @return
     */
    @DeleteMapping(value = "/{unitId}")
    public void deleteEmissionsUnit(@PathVariable Long unitId) {

        this.securityService.facilityEnforcer().enforceEntity(unitId, EmissionsUnitRepository.class);

        emissionsUnitService.delete(unitId);
    }
}
