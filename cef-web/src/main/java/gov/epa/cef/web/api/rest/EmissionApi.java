package gov.epa.cef.web.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.dto.EmissionDto;

@RestController
@RequestMapping("/api/emission")
public class EmissionApi {

    @Autowired
    private EmissionService emissionService;

    /**
     * Create a new Emission
     * @param dto
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<EmissionDto> createEmissionsProcess(@RequestBody EmissionDto dto) {

        EmissionDto result = emissionService.create(dto);

        return new ResponseEntity<EmissionDto>(result, HttpStatus.OK);
    }

    /**
     * Update an existing Emission
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<EmissionDto> updateEmissionsProcess(@PathVariable Long id, @RequestBody EmissionDto dto) {

        EmissionDto result = emissionService.update(dto);

        return new ResponseEntity<EmissionDto>(result, HttpStatus.OK);
    }

}
