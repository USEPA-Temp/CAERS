package gov.epa.cef.web.service.impl;

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
    
    /**
     * Create a new Release Point from a DTO object
     */
    public ReleasePointDto create(ReleasePointDto dto) {
    	ReleasePoint releasePoint = releasePointMapper.fromDto(dto);
    	
    	ReleasePointDto results = releasePointMapper.toDto(releasePointRepo.save(releasePoint));
    	return results;
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
     * Delete Release Point for a given id
     * @param releasePointId
     */
    public void delete(Long releasePointId) {
    	releasePointRepo.deleteById(releasePointId);
    }
    
    /**
     * Delete Release Point Apportionment for a given id
     * @param releasePointApptId
     */
    public void deleteAppt(Long releasePointApptId) {
    	releasePointApptRepo.deleteById(releasePointApptId);
    }

}
