package gov.epa.cef.web.api.rest.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;

@RestController
@RequestMapping("/api/emissionsByFacilityAndCAS")
public class EmissionsByFacilityAndCASApi {

    @Autowired
    private EmissionService emissionService;

    /**
     * Retrieve the total point and non-point emissions for a given FRS Facility Id and pollutant
     * @param frsFacilityId
     * @param casNumber
     * @return
     */
    @GetMapping(value = "/v1/{frsFacilityId}/{casNumber}")
    @ResponseBody
    public ResponseEntity<EmissionsByFacilityAndCASDto> retrieveEmissionsUnit(@PathVariable String frsFacilityId, @PathVariable String casNumber) {
        EmissionsByFacilityAndCASDto emissions = emissionService.findEmissionsByFacilityAndCAS(frsFacilityId, casNumber);
        return new ResponseEntity<EmissionsByFacilityAndCASDto>(emissions, HttpStatus.OK);
    }
    
}
