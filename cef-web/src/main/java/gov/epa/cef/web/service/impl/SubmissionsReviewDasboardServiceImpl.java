package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.repository.SubmissionsReviewDashboardRepository;
import gov.epa.cef.web.service.SubmissionsReviewDasboardService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.mapper.SubmissionsReviewDashboardMapper;

/**
 * @author ahmahfou
 *
 */
@Service
public class SubmissionsReviewDasboardServiceImpl implements SubmissionsReviewDasboardService{

    @Autowired
    private SubmissionsReviewDashboardRepository repo;
    
    @Autowired
    private SubmissionsReviewDashboardMapper mapper;
    
    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.SubmissionsReviewDasboardService#retrieveFacilitiesReports()
     */
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReports() {
        List<SubmissionsReviewDashboardView> reportsList=repo.findAll();
        return mapper.toDtoList(reportsList);
    }
}
