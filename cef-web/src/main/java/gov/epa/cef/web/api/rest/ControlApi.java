package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.service.ControlService;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/control")
public class ControlApi {

    @Autowired
    private ControlService controlService;

    /**
     * Retrieve a control by id
     * @param controlId
     * @return
     */
    @GetMapping(value = "/{controlId}")
    @ResponseBody
    public ResponseEntity<ControlDto> retrieveControl(@PathVariable Long controlId) {

        ControlDto result = controlService.retrieveById(controlId);
        return new ResponseEntity<ControlDto>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facilitySite/{facilitySiteId}")
    @ResponseBody
    public ResponseEntity<List<ControlDto>> retrieveControlsForFacilitySite(@PathVariable Long facilitySiteId) {

        List<ControlDto> result = controlService.retrieveForFacilitySite(facilitySiteId);
        return new ResponseEntity<List<ControlDto>>(result, HttpStatus.OK);
    }


    @GetMapping(value = "/components/{controlId}")
    @ResponseBody
    public ResponseEntity<List<EmissionsReportItemDto>> retrieveControlComponents(@PathVariable Long controlId) {
    	List<EmissionsReportItemDto> result = controlService.retrieveControlComponents(controlId);
    	return new ResponseEntity<List<EmissionsReportItemDto>>(result, HttpStatus.OK);
    }

}
