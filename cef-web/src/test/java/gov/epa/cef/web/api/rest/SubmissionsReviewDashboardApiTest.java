package gov.epa.cef.web.api.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;
import gov.epa.cef.web.service.impl.SubmissionsReviewDasboardServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SubmissionsReviewDashboardApiTest {

    @Mock
    private SubmissionsReviewDasboardServiceImpl submissionsReviewDasboardServiceImpl;
    
    @InjectMocks
    private SubmissionsReviewDashboardApi submissionsReviewDashboardApi;

    
    @Test
    public void retrieveFacilitiesReportsForReview_Should_ReturnListOfFacilitiesReportsForReview_WhenNoParameterPassed() {
        List<SubmissionsReviewDashboardDto> submissionsUnderReview=new ArrayList<>();
        when(submissionsReviewDasboardServiceImpl.retrieveFacilitiesReportsForCurrentUserAgencyForTheCurrentFiscalYear()).thenReturn(new ArrayList<>());
        ResponseEntity<List<SubmissionsReviewDashboardDto>> result=submissionsReviewDashboardApi.retrieveFacilitiesReportsForReview();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(submissionsUnderReview, result.getBody());
    }
    
}