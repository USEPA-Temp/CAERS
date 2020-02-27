package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.exception.AppValidationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import gov.epa.cef.web.service.mapper.EmissionsUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmissionsUnitServiceImpl implements EmissionsUnitService {

    @Autowired
    private EmissionsReportRepository reportRepo;

    @Autowired
    private EmissionsUnitRepository unitRepo;

    @Autowired
    private EmissionsUnitMapper emissionsUnitMapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;


    /**
     * Retrieve Emissions Unit by its id
     * @param unitId
     * @return
     */
    public EmissionsUnitDto retrieveUnitById(Long unitId) {
        EmissionsUnit emissionsUnit= unitRepo
            .findById(unitId)
            .orElse(null);
        EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(emissionsUnit);
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.EmissionsUnitService#retrieveEmissionUnitsForFacility(java.lang.Long)
     */
    @Override
    public List<EmissionsUnitDto> retrieveEmissionUnitsForFacility(Long facilitySiteId) {
        List<EmissionsUnit> emissionUnits= unitRepo.findByFacilitySiteIdOrderByUnitIdentifier(facilitySiteId);
        return emissionsUnitMapper.emissionsUnitsToEmissionUnitsDtos(emissionUnits);
    }

    /**
     * Delete an Emissions Unit for a given id
     * @param unitId
     */
    public void delete(Long unitId) {
        EmissionsUnit emissionsUnit= unitRepo
                .findById(unitId)
                .orElse(null);

        // find the last year reported
        Optional<EmissionsReport> lastReport = reportRepo.findRecentByEisProgramIdAndYear(emissionsUnit.getFacilitySite().getEisProgramId(),
                Integer.valueOf(emissionsUnit.getFacilitySite().getEmissionsReport().getYear() - 1).shortValue());

        // check if the emissions unit was reported last year
        if (lastReport.isPresent()) {
            unitRepo.retrieveByIdentifierFacilityYear(emissionsUnit.getUnitIdentifier(),
                    emissionsUnit.getFacilitySite().getEisProgramId(),
                    lastReport.get().getYear())
                    .ifPresent(oldUnit -> {
                        throw new AppValidationException("This Unit has been submitted on previous years' facility reports, so it cannot be deleted. "
                                + "If this Unit is no longer operational, please use the \"Operating Status\" field to mark this Unit as \"Permanently Shutdown\".");
                    });
        }

        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(unitId), EmissionsUnitRepository.class);
        unitRepo.deleteById(unitId);
    }

    /**
     * Create a new Emissions Unit from a DTO object
     */
    public EmissionsUnitDto create(EmissionsUnitDto dto) {

    	EmissionsUnit emissionUnit = emissionsUnitMapper.emissionsUnitFromDto(dto);

    	EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(unitRepo.save(emissionUnit));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionsUnitRepository.class);
    	return result;
    }


    public EmissionsUnitDto update(EmissionsUnitDto dto) {

        EmissionsUnit unit = unitRepo.findById(dto.getId()).orElse(null);

        if((dto.getOperatingStatusCode().getCode().equals("PS") && !unit.getOperatingStatusCode().getCode().equals("PS"))
         ||(dto.getOperatingStatusCode().getCode().equals("TS") && !unit.getOperatingStatusCode().getCode().equals("TS"))
         ||(dto.getOperatingStatusCode().getCode().equals("OP") && !unit.getOperatingStatusCode().getCode().equals("OP"))
         ||(dto.getOperatingStatusCode().getCode().equals("ONP") && !unit.getOperatingStatusCode().getCode().equals("ONP"))
         ||(dto.getOperatingStatusCode().getCode().equals("ONRE") && !unit.getOperatingStatusCode().getCode().equals("ONRE"))

         ){
            // TODO use this after switching to CodeLookupDto
            // OperatingStatusCode tempOperatingStatusCode = new OperatingStatusCode();
            // tempOperatingStatusCode.setCode(dto.getOperatingStatusCode().getCode());
            //
        	OperatingStatusCode tempOperatingStatusCode = dto.getOperatingStatusCode();
        	Short tempStatusYear = dto.getStatusYear();

        	unit.getEmissionsProcesses().forEach(process -> {
	        	process.setOperatingStatusCode(tempOperatingStatusCode);
	        	process.setStatusYear(tempStatusYear);
        	});
        }

        emissionsUnitMapper.updateFromDto(dto, unit);
        EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(unitRepo.save(unit));

        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionsUnitRepository.class);
        return result;
    }

}
