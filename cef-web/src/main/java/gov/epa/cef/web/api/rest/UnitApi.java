package gov.epa.cef.web.api.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.EmissionsUnitService;

@RestController
@RequestMapping("/api/unit")
public class UnitApi {

    @Autowired
    private EmissionsUnitService emissionsUnitService;

    /**
     * Retrieve a unit by it's ID
     * @param unitId
     * @return
     */
    @GetMapping(value = "/{unitId}")
    @ResponseBody
    public ResponseEntity<EmissionsUnit> retrieveEmissionsUnit(@PathVariable Long unitId) {

        EmissionsUnit result = emissionsUnitService.retrieveUnitById(unitId);

        return new ResponseEntity<EmissionsUnit>(result, HttpStatus.OK);
    }
}
