package gov.epa.cef.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.repository.ReleasePointApptRepository;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.mapper.ReleasePointMapper;

@Service
public class ReleasePointServiceImpl implements ReleasePointService {

    @Autowired
    private ReleasePointRepository releasePointRepo;
    
    @Autowired
    private ReleasePointApptRepository releasePointApptRepo;
    
    @Autowired
    private ReleasePointMapper releasePointMapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;
    
    /**
     * Create a new Release Point from a DTO object
     */
    public ReleasePointDto create(ReleasePointDto dto) {
    	ReleasePoint releasePoint = releasePointMapper.fromDto(dto);
    	
    	ReleasePointDto result = releasePointMapper.toDto(releasePointRepo.save(releasePoint));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ReleasePointRepository.class);
    	return result;
    }
    
    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReleasePointService#retrieveById(java.lang.Long)
     */ 
    @Override
    public ReleasePointDto retrieveById(Long releasePointId) {
        ReleasePoint releasePoint= releasePointRepo
            .findById(releasePointId)
            .orElse(null);
        return releasePointMapper.toDto(releasePoint);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReleasePointService#retrieveByFacilityId(java.lang.Long)
     */
    public List<ReleasePointDto> retrieveByFacilitySiteId(Long facilitySiteId) {
        List<ReleasePoint> releasePoints=releasePointRepo.findByFacilitySiteIdOrderByReleasePointIdentifier(facilitySiteId);
        return releasePointMapper.toDtoList(releasePoints);
    }
    
    /**
     * Update an existing Release Point from a DTO
     */
    public ReleasePointDto update(ReleasePointDto dto) {
    	
    	ReleasePoint releasePoint = releasePointRepo.findById(dto.getId()).orElse(null);
    	releasePointMapper.updateFromDto(dto, releasePoint);
    	
    	ReleasePointDto result = releasePointMapper.toDto(releasePointRepo.save(releasePoint));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ReleasePointRepository.class);

        return result;
    }
    
    /**
     * Delete Release Point for a given id
     * @param releasePointId
     */
    public void delete(Long releasePointId) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(releasePointId), ReleasePointRepository.class);
    	releasePointRepo.deleteById(releasePointId);
    }
    
    /**
     * Delete Release Point Apportionment for a given id
     * @param releasePointApptId
     */
    public void deleteAppt(Long releasePointApptId) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(releasePointApptId), ReleasePointApptRepository.class);
    	releasePointApptRepo.deleteById(releasePointApptId);
    }

}
