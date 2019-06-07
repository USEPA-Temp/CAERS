package gov.epa.cef.web.api.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.FacilitySiteContactService;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;

@RestController
@RequestMapping("/api/facilitySiteContact")
public class FacilitySiteContactApi {

    @Autowired
    private FacilitySiteContactService facilitySiteContactService;

    /**
     * Retrieve a facility site contact by ID
     * @param contactId
     * @return
     */
    @GetMapping(value = "/{contactId}")
    @ResponseBody
    public ResponseEntity<FacilitySiteContactDto> retrieveContact(@PathVariable Long contactId) {

        FacilitySiteContactDto result = facilitySiteContactService.retrieveById(contactId);
        return new ResponseEntity<FacilitySiteContactDto>(result, HttpStatus.OK);
    }
 
    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facility/{facilitySiteId}")
    @ResponseBody
    public ResponseEntity<Collection<FacilitySiteContactDto>> retrieveContactsForFacility(@PathVariable Long facilitySiteId) {

        Collection<FacilitySiteContactDto> result = facilitySiteContactService.retrieveForFacilitySite(facilitySiteId);
        return new ResponseEntity<Collection<FacilitySiteContactDto>>(result, HttpStatus.OK);
    }
}
