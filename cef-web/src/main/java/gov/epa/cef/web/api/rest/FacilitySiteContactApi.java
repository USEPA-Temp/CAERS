package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.FacilitySiteContactRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.FacilitySiteContactService;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/api/facilitySiteContact")
public class FacilitySiteContactApi {

    private final FacilitySiteContactService facilitySiteContactService;

    private final SecurityService securityService;

    @Autowired
    FacilitySiteContactApi(SecurityService securityService,
                           FacilitySiteContactService facilitySiteContactService) {

        this.facilitySiteContactService = facilitySiteContactService;
        this.securityService = securityService;
    }

    /**
     * Retrieve a facility site contact by ID
     * @param contactId
     * @return
     */
    @GetMapping(value = "/{contactId}")
    public ResponseEntity<FacilitySiteContactDto> retrieveContact(@NotNull @PathVariable Long contactId) {

        this.securityService.facilityEnforcer().enforceEntity(contactId, FacilitySiteContactRepository.class);

        FacilitySiteContactDto result = facilitySiteContactService.retrieveById(contactId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/facility/{facilitySiteId}")
    public ResponseEntity<Collection<FacilitySiteContactDto>> retrieveContactsForFacility(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceFacilitySite(facilitySiteId);

        Collection<FacilitySiteContactDto> result =
            facilitySiteContactService.retrieveForFacilitySite(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
