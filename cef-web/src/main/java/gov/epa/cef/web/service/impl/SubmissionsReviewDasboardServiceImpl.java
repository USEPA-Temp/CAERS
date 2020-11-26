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
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByReportStatusAndProgramSystemCode(ReportStatus reportStatus) {
    	UserDto currentUser=userService.getCurrentUser();
    	List<SubmissionsReviewDashboardView> reportsList=repo.findByReportStatusAndProgramSystemCode(reportStatus, currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }

    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsForCurrentUserProgramSystemForTheCurrentFiscalYear() {
        UserDto currentUser=userService.getCurrentUser();
        List<SubmissionsReviewDashboardView> reportsList=repo.findByProgramSystemCode(currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }
    
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndReportStatusAndProgramSystemCode(Short reportYear, ReportStatus reportStatus){
        UserDto currentUser=userService.getCurrentUser();
    	List<SubmissionsReviewDashboardView> reportsList=repo.findByYearAndReportStatusAndProgramSystemCode(reportYear, reportStatus, currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }
    
    @Override
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsByYearAndProgramSystemCode(Short reportYear){
        UserDto currentUser=userService.getCurrentUser();
        List<SubmissionsReviewDashboardView> reportsList=repo.findByYearAndProgramSystemCode(reportYear, currentUser.getProgramSystemCode());
        return mapper.toDtoList(reportsList);
    }
    
}
