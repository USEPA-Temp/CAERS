package gov.epa.cef.web.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.epa.cef.web.domain.Facility;
import gov.epa.cef.web.repository.FacilityRepository;
import gov.epa.cef.web.service.FacilityService;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityRepository facRepo;


    /**
     * Find common form facilities for a given state
     * @param state Two-character state code
     * @return
     */	
    public List<Facility> findByState(String stateCode) {
        return facRepo.findByStateCode(stateCode);
    }

}
