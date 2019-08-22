package gov.epa.cef.web.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.OperatingDetailService;
import gov.epa.cef.web.service.dto.OperatingDetailDto;

@RestController
@RequestMapping("/api/operatingDetail")
public class OperatingDetailApi {

    @Autowired
    private OperatingDetailService operatingDetailService;

    /**
     * Update an Operating Detail
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<OperatingDetailDto> updateOperatingDetail(@PathVariable Long id, @RequestBody OperatingDetailDto dto) {

        OperatingDetailDto result = operatingDetailService.update(dto);

        return new ResponseEntity<OperatingDetailDto>(result, HttpStatus.OK);
    }

}
