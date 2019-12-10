package gov.epa.cef.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import gov.epa.cef.web.service.mapper.EmissionsUnitMapper;

@Service
public class EmissionsUnitServiceImpl implements EmissionsUnitService {

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
        emissionsUnitMapper.updateFromDto(dto, unit);

        EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(unitRepo.save(unit));
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionsUnitRepository.class);
        return result;
    }
    
}
