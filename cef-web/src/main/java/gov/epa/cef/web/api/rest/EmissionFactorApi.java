package gov.epa.cef.web.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.EmissionFactorService;
import gov.epa.cef.web.service.dto.EmissionFactorDto;

@RestController
@RequestMapping("/api/emissionFactor")
public class EmissionFactorApi {

    @Autowired
    private EmissionFactorService efService;

    /**
     * Search for Emission Factors matching the provided criteria
     * @param dto
     * @return
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<EmissionFactorDto>> search(EmissionFactorDto dto) {

        List<EmissionFactorDto> result = efService.retrieveByExample(dto);
        return new ResponseEntity<List<EmissionFactorDto>>(result, HttpStatus.OK);
    }

}
