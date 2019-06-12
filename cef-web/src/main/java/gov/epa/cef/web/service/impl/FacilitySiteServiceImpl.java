package gov.epa.cef.web.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.FacilitySiteService;

@Service
public class FacilitySiteServiceImpl implements FacilitySiteService {

    @Autowired
    private FacilitySiteRepository facSiteRepo;


    @Override
    public FacilitySite findById(Long id) {
        return facSiteRepo
            .findById(id)
            .orElse(null);
    }
    
    @Override
    public FacilitySite findByEisProgramIdAndReportId(String eisProgramId, Long emissionsReportId) {
        return facSiteRepo.findByEisProgramIdAndEmissionsReportId(eisProgramId, emissionsReportId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    /**
     * Find common form facilities for a given state
     * @param state Two-character state code
     * @return
     */	
    public List<FacilitySite> findByState(String stateCode) {
        return facSiteRepo.findByStateCode(stateCode);
    }


}
