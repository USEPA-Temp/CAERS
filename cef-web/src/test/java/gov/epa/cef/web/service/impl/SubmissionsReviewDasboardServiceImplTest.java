package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.repository.SubmissionsReviewDashboardRepository;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.mapper.SubmissionsReviewDashboardMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SubmissionsReviewDasboardServiceImplTest {
    
    @Mock
    private SubmissionsReviewDashboardRepository repo;
    
    @Mock
    private SubmissionsReviewDashboardMapper mapper;
    
    @InjectMocks
    SubmissionsReviewDasboardServiceImpl submissionsReviewDasboardServiceImpl;
    
    
    @Test
    public void retrieveFacilitiesReports_Should_ReturnListOfSubmissionsUnderReview_When_NoArugmentsPassed() {
        List<SubmissionsReviewDashboardView> submissionsReviewDashboardView=new ArrayList<>();
        when(repo.findAll()).thenReturn(submissionsReviewDashboardView);
        
        List<SubmissionsReviewDashboardDto> submissionsReviewDashboardDto=new ArrayList<>();
        when(mapper.toDtoList(submissionsReviewDashboardView)).thenReturn(submissionsReviewDashboardDto);
        
        List<SubmissionsReviewDashboardDto> result= submissionsReviewDasboardServiceImpl.retrieveFacilitiesReports();
        
        assertEquals(submissionsReviewDashboardDto, result);
    }
    
}
