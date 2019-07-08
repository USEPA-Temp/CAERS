package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.service.EmissionsProcessService;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;
import gov.epa.cef.web.service.mapper.EmissionsProcessMapper;

@Service
public class EmissionsProcessServiceImpl implements EmissionsProcessService {

    @Autowired
    private EmissionsProcessRepository processRepo;
    
    @Autowired
    private EmissionsProcessMapper emissionsProcessMapper;


    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.EmissionsProcessService#retrieveById(java.lang.Long)
     */
    @Override
    public EmissionsProcessDto retrieveById(Long id) {
        EmissionsProcess result = processRepo
            .findById(id)
            .orElse(null);
        return emissionsProcessMapper.emissionsProcessToEmissionsProcessDto(result);
    }
    
    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.EmissionsProcessService#retrieveForReleasePoint(java.lang.Long)
     */
    @Override
    public List<EmissionsProcessDto> retrieveForReleasePoint(Long pointId) {
        List<EmissionsProcess> result = processRepo.findByReleasePointApptsReleasePointId(pointId);
        return emissionsProcessMapper.emissionsProcessesToEmissionsProcessDtos(result);
    }


    /**
     * Retrieve Emissions Processes for an Emissions Unit
     * @param emissionsUnitid
     * @return
     */
    public List<EmissionsProcessDto> retrieveForEmissionsUnit(Long emissionsUnitId) {
        List<EmissionsProcess> result = processRepo.findByEmissionsUnitId(emissionsUnitId);
        return emissionsProcessMapper.emissionsProcessesToEmissionsProcessDtos(result);
    }


}
