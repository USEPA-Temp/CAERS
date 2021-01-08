package gov.epa.cef.web.api.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.UserFacilityAssociationDto;
import gov.epa.cef.web.service.impl.UserFacilityAssociationServiceImpl;

@RestController
@RequestMapping("/api/userFacilityAssociation")
public class UserFacilityAssociationApi {

    private final UserFacilityAssociationServiceImpl ufaService;

    private final SecurityService securityService;

    @Autowired
    UserFacilityAssociationApi(SecurityService securityService,
                               UserFacilityAssociationServiceImpl mfrService) {

        this.securityService = securityService;
        this.ufaService = mfrService;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<UserFacilityAssociationDto> retrieveAssociation(@NotNull @PathVariable Long id) {

        UserFacilityAssociationDto result = this.ufaService.findById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAssociation(@NotNull @PathVariable Long id) {

        this.ufaService.deleteById(id);
    }

    @GetMapping(value = "/facility/{masterFacilityRecordId}")
    public ResponseEntity<List<UserFacilityAssociationDto>> retrieveAssociationsForFacility(
        @NotNull @PathVariable Long masterFacilityRecordId) {

        List<UserFacilityAssociationDto> result =
            this.ufaService.findByMasterFacilityRecordId(masterFacilityRecordId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
