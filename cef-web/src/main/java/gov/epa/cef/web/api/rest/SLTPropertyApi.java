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

import gov.epa.cef.web.domain.SLTConfigProperty;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.dto.PropertyDto;
import gov.epa.cef.web.service.mapper.AppPropertyMapper;

@RestController
@RequestMapping("/api/slt/property")
@RolesAllowed(value = {AppRole.ROLE_REVIEWER})
public class SLTPropertyApi {

    @Autowired
    private SLTPropertyProvider propertyProvider;
    
    @Autowired
    private SecurityService securityService;

    @Autowired
    private AppPropertyMapper mapper;

    /**
     * Retrieve a properties
     * @return
     */
    @GetMapping(value = "/{name}")
    public ResponseEntity<PropertyDto> retrieveProperty(@NotNull @PathVariable String name) {

        String userProgramSystem = this.securityService.getCurrentProgramSystemCode();

        PropertyDto result = mapper.sltToDto(propertyProvider.retrieve(new PropertyDto().withName(name), userProgramSystem));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve all properties for the user's state
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PropertyDto>> retrieveAllProperties() {

        String userProgramSystem = this.securityService.getCurrentProgramSystemCode();

        List<PropertyDto> result = mapper.sltToDtoList(propertyProvider.retrieveAllForProgramSystem(userProgramSystem));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update a property value
     * @return
     */
    @PutMapping(value = "/{propName}")
    public ResponseEntity<PropertyDto> updateProperty(@NotNull @PathVariable String propName,
            @NotNull @RequestBody PropertyDto dto) {

        String userProgramSystem = this.securityService.getCurrentProgramSystemCode();
        SLTConfigProperty result = this.propertyProvider.update(dto.withName(propName), userProgramSystem, dto.getValue());
        return new ResponseEntity<>(mapper.sltToDto(result), HttpStatus.OK);
    }

    /**
     * Update multiple properties
     * @return
     */
    @PostMapping
    public ResponseEntity<List<PropertyDto>> updateProperties(@NotNull @RequestBody List<PropertyDto> dtos) {

        String userProgramSystem = this.securityService.getCurrentProgramSystemCode();

        List<PropertyDto> result = dtos.stream().map(dto -> {
            SLTConfigProperty prop = this.propertyProvider.update(dto, userProgramSystem, dto.getValue());
            return mapper.sltToDto(prop);
        }).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
