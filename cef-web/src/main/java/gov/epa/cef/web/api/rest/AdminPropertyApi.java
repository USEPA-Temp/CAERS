package gov.epa.cef.web.api.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.domain.AdminProperty;
import gov.epa.cef.web.provider.system.PropertyProvider;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.PropertyDto;
import gov.epa.cef.web.service.mapper.AppPropertyMapper;

@RestController
@RequestMapping("/api/admin/property")
@RolesAllowed(value = {AppRole.ROLE_CAERS_ADMIN})
public class AdminPropertyApi {

    @Autowired
    private PropertyProvider propertyProvider;

    @Autowired
    private EmissionService emissionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppPropertyMapper mapper;

    /**
     * Retrieve a properties
     * @return
     */
    @GetMapping(value = "/{name}")
    public ResponseEntity<PropertyDto> retrieveProperty(@NotNull @PathVariable String name) {
        PropertyDto result = mapper.toDto(propertyProvider.retrieve(new PropertyDto().withName(name)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve all properties
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PropertyDto>> retrieveAllProperties() {
        List<PropertyDto> result = mapper.toDtoList(propertyProvider.retrieveAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update a property value
     * @return
     */
    @PutMapping(value = "/{propName}")
    public ResponseEntity<PropertyDto> updateProperty(@NotNull @PathVariable String propName,
            @NotNull @RequestBody PropertyDto dto) {

        AdminProperty result = this.propertyProvider.update(dto.withName(propName), dto.getValue());
        return new ResponseEntity<>(mapper.toDto(result), HttpStatus.OK);
    }

    /**
     * Update multiple properties
     * @return
     */
    @PostMapping
    public ResponseEntity<List<PropertyDto>> updateProperties(@NotNull @RequestBody List<PropertyDto> dtos) {

        List<PropertyDto> result = dtos.stream().map(dto -> {
            AdminProperty prop = this.propertyProvider.update(dto, dto.getValue());
            return mapper.toDto(prop);
        }).collect(Collectors.toList());
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Send a test email to the Admin email list
     */
    @PostMapping(value = "/sendTestEmail")
    public void sendTestAdminEmail() {

        this.notificationService.sendAdminNotification(NotificationService.AdminEmailType.AdminTest, null);
    }

    @PostMapping(value = "/emission/recalculate/{reportId}")
    public ResponseEntity<List<EmissionDto>> recalculateEmissionTotalTons(@NotNull @PathVariable Long reportId) {

        List<EmissionDto> result = emissionService.recalculateEmissionTons(reportId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
