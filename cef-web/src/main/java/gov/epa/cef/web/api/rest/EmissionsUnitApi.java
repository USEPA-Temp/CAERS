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
import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import gov.epa.cef.web.service.mapper.EmissionsUnitMapper;

@RestController
@RequestMapping("/api/emissionsUnit")
public class EmissionsUnitApi {

    @Autowired
    private EmissionsUnitService emissionsUnitService;

    @Autowired
    private EmissionsUnitMapper emissionsUnitMapper;

    /**
     * Retrieve a unit by it's ID
     * @param unitId
     * @return
     */
    @GetMapping(value = "/{unitId}")
    @ResponseBody
    public ResponseEntity<EmissionsUnitDto> retrieveEmissionsUnit(@PathVariable Long unitId) {

        EmissionsUnit emissionsUnit = emissionsUnitService.retrieveUnitById(unitId);
        EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(emissionsUnit);
        return new ResponseEntity<EmissionsUnitDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Emissions Units for a facility
     * @param facilityId
     * @return
     */
    @GetMapping(value = "/facility/{facilityId}")
    @ResponseBody
    public ResponseEntity<Collection<EmissionsUnitDto>> retrieveFacilityEmissionsUnits(@PathVariable Long facilityId) {

        Collection<EmissionsUnit> units = emissionsUnitService.retrieveByFacilityId(facilityId);
        Collection<EmissionsUnitDto> result = units.stream()
                .map(unit -> emissionsUnitMapper.emissionsUnitToDto(unit))
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<EmissionsUnitDto>>(result, HttpStatus.OK);
    }
}
