package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsReportRepository;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceImplTest {
    
    @Mock
    private EmissionsReportRepository erRepo;
    
    @InjectMocks
    private ReportServiceImpl reportServiceImpl;
    
    @Before
    public void init(){
        EmissionsReport emissionsReport = new EmissionsReport();
        List<EmissionsReport> emissionsReportList = new ArrayList<EmissionsReport>();
        List<EmissionsReport> emptyReportList = new ArrayList<EmissionsReport>();
        emissionsReportList.add(emissionsReport);
        when(erRepo.findById(1L)).thenReturn(Optional.of(emissionsReport));
        when(erRepo.findById(2L)).thenReturn(Optional.empty());
        when(erRepo.findByEisProgramId("XXXX")).thenReturn(emissionsReportList);
        when(erRepo.findByEisProgramId("YYYY")).thenReturn(emptyReportList);
    }
    
    @Test
    public void findById_Should_Return_EmissionsReportObject_When_EmissionsReportExists(){
        EmissionsReport emissionsReport = reportServiceImpl.findById(1L);
        assertNotEquals(null, emissionsReport);
    }
    
    @Test
    public void findById_Should_Return_Null_When_EmissionsReportNotExist(){
        EmissionsReport emissionsReport = reportServiceImpl.findById(2L);
        assertEquals(null, emissionsReport);
    }
    
    @Test
    public void findById_Should_Return_Null_When_IDisNull(){
        EmissionsReport emissionsReport = reportServiceImpl.findById(null);
        assertEquals(null, emissionsReport);
    }
    
    @Test
    public void findByFacilityId_Should_Return_ReportList_WhenReportExist() {
        Collection<EmissionsReport> emissionsReportList = reportServiceImpl.findByFacilityId("XXXX");
        assertNotEquals(null, emissionsReportList);
    }
    
    @Test
    public void findByFacilityId_Should_Return_Empty_WhenReportsDoNotExist() {
        Collection<EmissionsReport> emissionsReportList = reportServiceImpl.findByFacilityId("YYYY");
        assertEquals(new ArrayList<EmissionsReport>(), emissionsReportList);
    }

}
