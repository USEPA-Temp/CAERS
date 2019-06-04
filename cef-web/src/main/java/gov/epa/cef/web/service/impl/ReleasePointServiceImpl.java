package gov.epa.cef.web.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.service.ReleasePointService;

@Service
public class ReleasePointServiceImpl implements ReleasePointService {

    @Autowired
    private ReleasePointRepository releasePointRepo;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReleasePointService#retrieveById(java.lang.Long)
     */ 
    @Override
    public ReleasePoint retrieveById(Long releasePointId) {
        return releasePointRepo
            .findById(releasePointId)
            .orElse(null);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReleasePointService#retrieveByFacilityId(java.lang.Long)
     */
    @Override
    public Collection<ReleasePoint> retrieveByFacilityId(Long facilityId) {
        return releasePointRepo.findByFacilityId(facilityId);
    }

}
