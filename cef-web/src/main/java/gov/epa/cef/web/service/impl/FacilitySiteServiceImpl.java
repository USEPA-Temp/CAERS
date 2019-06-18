package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;

@Service
public class FacilitySiteServiceImpl implements FacilitySiteService {

    @Autowired
    private FacilitySiteRepository facSiteRepo;

    @Autowired
    private FacilitySiteMapper facilitySiteMapper;

    @Override
    public FacilitySiteDto findById(Long id) {
        FacilitySite facilitySite=facSiteRepo.findById(id)
                    .orElse(null);
        return facilitySiteMapper.toDto(facilitySite);
    }
    
    @Override
    public FacilitySiteDto findByEisProgramIdAndReportId(String eisProgramId, Long emissionsReportId) {
        FacilitySite facilitySite=facSiteRepo.findByEisProgramIdAndEmissionsReportId(eisProgramId, emissionsReportId)
            .stream()
            .findFirst()
            .orElse(null);
        return facilitySiteMapper.toDto(facilitySite);
    }

    /**
     * Find common form facilities for a given state
     * @param state Two-character state code
     * @return
     */	
    public List<FacilitySiteDto> findByState(String stateCode) {
        List<FacilitySite> facilitySites= facSiteRepo.findByStateCode(stateCode);
        return facilitySiteMapper.toDtoList(facilitySites);
    }
}
