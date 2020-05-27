package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.EmissionService;
import gov.epa.cef.web.service.dto.EmissionBulkEntryHolderDto;
import gov.epa.cef.web.service.dto.EmissionDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/emission")
public class EmissionApi {

    private final EmissionService emissionService;

    private final SecurityService securityService;

    @Autowired
    EmissionApi(SecurityService securityService,
                EmissionService emissionService) {

        this.securityService = securityService;
        this.emissionService = emissionService;
    }

    /**
     * Create a new Emission
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<EmissionDto> createEmission(@NotNull @RequestBody EmissionDto dto) {

        this.securityService.facilityEnforcer()
            .enforceEntity(dto.getReportingPeriodId(), ReportingPeriodRepository.class);

        EmissionDto result = emissionService.create(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve an Emission
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmissionDto> retrieveEmission(@NotNull @PathVariable Long id) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionRepository.class);

        EmissionDto result = emissionService.retrieveById(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update an existing Emission
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<EmissionDto> updateEmission(
        @NotNull @PathVariable Long id, @NotNull @RequestBody EmissionDto dto) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionRepository.class);

        EmissionDto result = emissionService.update(dto.withId(id));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Delete an Emission for given id
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void deleteEmission(@NotNull @PathVariable Long id) {

        this.securityService.facilityEnforcer().enforceEntity(id, EmissionRepository.class);

        emissionService.delete(id);
    }

    /**
     * Retrieve Reporting Periods for bulk entry by Report Id
     * @param facilitySiteId
     * @return
     */
    @GetMapping(value = "/bulkEntry/{facilitySiteId}")
    public ResponseEntity<Collection<EmissionBulkEntryHolderDto>> retrieveBulkEntryEmissionsForFacilitySite(
        @NotNull @PathVariable Long facilitySiteId) {

        this.securityService.facilityEnforcer().enforceEntity(facilitySiteId, FacilitySiteRepository.class);

        Collection<EmissionBulkEntryHolderDto> result = emissionService.retrieveBulkEntryEmissionsForFacilitySite(facilitySiteId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Update the total emissions for multiple Emissions at once and recalculate all Emissions for this facility
     * @param dtos
     * @return
     */
    @PutMapping(value = "/bulkEntry/{facilitySiteId}")
    public ResponseEntity<Collection<EmissionBulkEntryHolderDto>> bulkUpdate(
        @NotNull @PathVariable Long facilitySiteId, @RequestBody List<EmissionDto> dtos) {

        this.securityService.facilityEnforcer().enforceEntity(facilitySiteId, FacilitySiteRepository.class);

        List<Long> emissionIds = dtos.stream().map(EmissionDto::getId).collect(Collectors.toList());
        this.securityService.facilityEnforcer().enforceEntities(emissionIds, EmissionRepository.class);

        Collection<EmissionBulkEntryHolderDto> result = emissionService.bulkUpdate(facilitySiteId, dtos);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Calculate total emissions and emission factor
     * @param dto
     * @return
     */
    @PostMapping(value = "/calculate")
    public ResponseEntity<EmissionDto> calculateTotalEmissions(@NotNull @RequestBody EmissionDto dto) {


        EmissionDto result = emissionService.calculateTotalEmissions(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
