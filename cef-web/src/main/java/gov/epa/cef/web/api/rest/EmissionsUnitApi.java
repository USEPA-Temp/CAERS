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

import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;

@RestController
@RequestMapping("/api/emissionsUnit")
public class EmissionsUnitApi {

    @Autowired
    private EmissionsUnitService emissionsUnitService;

    /**
     * Retrieve a unit by it's ID
     * @param unitId
     * @return
     */
    @GetMapping(value = "/{unitId}")
    @ResponseBody
    public ResponseEntity<EmissionsUnitDto> retrieveEmissionsUnit(@PathVariable Long unitId) {
        EmissionsUnitDto emissionsUnit = emissionsUnitService.retrieveUnitById(unitId);
        return new ResponseEntity<EmissionsUnitDto>(emissionsUnit, HttpStatus.OK);
    }
    
    /**
     * Retrieve emissions unit of a facility
     * @param facilitySiteId
     * @return list of emissions unit
     */
    @GetMapping(value = "/facility/{facilitySiteId}")
    @ResponseBody
    public ResponseEntity<List<EmissionsUnitDto>> retrieveEmissionsUnitsOfFacility(@PathVariable Long facilitySiteId) {
        List<EmissionsUnitDto> emissionsUnits = emissionsUnitService.retrieveEmissionUnitsForFacility(facilitySiteId);
        return new ResponseEntity<List<EmissionsUnitDto>>(emissionsUnits, HttpStatus.OK);
    }
}
