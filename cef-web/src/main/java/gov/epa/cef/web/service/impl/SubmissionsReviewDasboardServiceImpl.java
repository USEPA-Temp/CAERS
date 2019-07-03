package gov.epa.cef.web.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.repository.SubmissionsReviewDashboardRepository;
import gov.epa.cef.web.service.SubmissionsReviewDasboardService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.SubmissionsReviewDashboardMapper;
import gov.epa.cef.web.util.DateUtils;

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
    public List<SubmissionsReviewDashboardDto> retrieveFacilitiesReportsForCurrentUserAgencyForTheCurrentFiscalYear() {
        UserDto currentUser=userService.getCurrentUser();
        Short currentFiscalYear=DateUtils.getFiscalYearForDate(Calendar.getInstance().getTime()).shortValue();
        List<SubmissionsReviewDashboardView> reportsList=repo.findByYearAndAgencyCode(currentFiscalYear, currentUser.getAgencyCode());
        return mapper.toDtoList(reportsList);
    }

}
