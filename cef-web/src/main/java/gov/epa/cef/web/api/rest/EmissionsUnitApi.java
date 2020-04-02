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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * Create a new Emissions Unit
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<EmissionsUnitDto> createEmissionsUnit(
        @RequestBody EmissionsUnitDto dto) {

        this.securityService.facilityEnforcer()
            .enforceFacilitySite(dto.getFacilitySiteId());

        EmissionsUnitDto result = emissionsUnitService.create(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Update an Emissions Unit
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<EmissionsUnitDto> updateEmissionsUnit(
        @NotNull @PathVariable Long id, @NotNull @RequestBody EmissionsUnitDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionsUnitRepository.class);

        EmissionsUnitDto result = emissionsUnitService.update(dto.withId(id));

        return new ResponseEntity<EmissionsUnitDto>(result, HttpStatus.OK);
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
