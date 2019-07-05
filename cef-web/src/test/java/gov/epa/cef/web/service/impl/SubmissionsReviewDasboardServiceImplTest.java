package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.repository.SubmissionsReviewDashboardRepository;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.SubmissionsReviewDashboardMapper;
import gov.epa.cef.web.util.DateUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtils.class})
public class SubmissionsReviewDasboardServiceImplTest {
    
    @Mock
    private SubmissionsReviewDashboardRepository repo;
    
    @Mock
    private SubmissionsReviewDashboardMapper mapper;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    SubmissionsReviewDasboardServiceImpl submissionsReviewDasboardServiceImpl;
    
    
    @Test
    public void retrieveFacilitiesReports_Should_ReturnListOfSubmissionsUnderReview_When_NoArugmentsPassed() {
        
        UserDto user=new UserDto();
        user.setAgencyCode("GA");
        when(userService.getCurrentUser()).thenReturn(user);

        Date date=Calendar.getInstance().getTime();
        PowerMockito.mockStatic(Calendar.class);
        when(Calendar.getInstance().getTime()).thenReturn(date);
        when(DateUtils.getFiscalYearForDate(date)).thenReturn(2019);
        
        List<SubmissionsReviewDashboardView> submissionsReviewDashboardView=new ArrayList<>();
        when(repo.findByYearAndAgencyCode((short)2019, "GA")).thenReturn(submissionsReviewDashboardView);
        
        List<SubmissionsReviewDashboardDto> submissionsReviewDashboardDto=new ArrayList<>();
        when(mapper.toDtoList(submissionsReviewDashboardView)).thenReturn(submissionsReviewDashboardDto);
        
        List<SubmissionsReviewDashboardDto> result= submissionsReviewDasboardServiceImpl.retrieveFacilitiesReportsForCurrentUserAgencyForTheCurrentFiscalYear();
        
        assertEquals(submissionsReviewDashboardDto, result);
    }
    
}
