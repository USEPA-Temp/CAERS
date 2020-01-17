package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.repository.SubmissionsReviewDashboardRepository;
import gov.epa.cef.web.service.SubmissionsReviewDasboardService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.dto.UserDto;
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
    
    @Autowired
    private UserService userService;
    
    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.SubmissionsReviewDasboardService#retrieveFacilitiesReportsForCurrentUserAgencyForTheCurrentFiscalYear()
     */
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByReportStatus(ReportStatus reportStatus) {
        List<SubmissionsReviewDashboardView> reportsList=repo.findByReportStatus(reportStatus);
        return mapper.toDtoList(reportsList);
    }

    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsForCurrentUserAgencyForTheCurrentFiscalYear() {
        UserDto currentUser=userService.getCurrentUser();
        List<SubmissionsReviewDashboardView> reportsList=repo.findByAgencyCode(currentUser.getAgencyCode());
        return mapper.toDtoList(reportsList);
    }
    
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndReportStatus(Short reportYear, ReportStatus reportStatus){
        List<SubmissionsReviewDashboardView> reportsList=repo.findByYearAndReportStatus(reportYear, reportStatus);
        return mapper.toDtoList(reportsList);
    }
    
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYear(Short reportYear){
        List<SubmissionsReviewDashboardView> reportsList=repo.findByYear(reportYear);
        return mapper.toDtoList(reportsList);
    }
    
}
