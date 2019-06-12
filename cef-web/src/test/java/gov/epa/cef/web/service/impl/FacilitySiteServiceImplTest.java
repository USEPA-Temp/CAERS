package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.repository.FacilitySiteRepository;

@RunWith(MockitoJUnitRunner.class)
public class FacilitySiteServiceImplTest {
    
    @Mock
    private FacilitySiteRepository facSiteRepo;
    
    @InjectMocks
    private FacilitySiteServiceImpl facilitySiteServiceImpl;
    
    @Before
    public void init(){
        FacilitySite testSite = new FacilitySite();
        List<FacilitySite> testSiteList = new ArrayList<FacilitySite>();
        List<FacilitySite> nullSiteList = null;
        List<FacilitySite> emptySiteList = new ArrayList<FacilitySite>();
        testSiteList.add(testSite);
        when(facSiteRepo.findById(1L)).thenReturn(Optional.of(testSite));
        when(facSiteRepo.findById(2L)).thenReturn(Optional.empty());
        when(facSiteRepo.findByEisProgramIdAndEmissionsReportId("XXXX", 1L)).thenReturn(testSiteList);
        when(facSiteRepo.findByEisProgramIdAndEmissionsReportId("XXXX", 2L)).thenReturn(emptySiteList);
        when(facSiteRepo.findByStateCode("GA")).thenReturn(testSiteList);
        when(facSiteRepo.findByStateCode("AK")).thenReturn(nullSiteList);
        
    }
    
    @Test
    public void retrieveById_Should_Return_FacilitySiteObject_When_FacilitySiteExists(){
        FacilitySite facilitySite = facilitySiteServiceImpl.findById(1L);
        assertNotEquals(facilitySite, null);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_FacilitySiteDoesNotExist(){
        FacilitySite facilitySite = facilitySiteServiceImpl.findById(2L);
        assertEquals(facilitySite, null);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_FacilitySiteIDisNull(){
        FacilitySite facilitySite = facilitySiteServiceImpl.findById(null);
        assertEquals(facilitySite, null);
    }
    
    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_FacilitySiteObject_When_FacilitySiteExists(){
        FacilitySite facilitySite = facilitySiteServiceImpl.findByEisProgramIdAndReportId("XXXX", 1L);
        assertNotEquals(facilitySite, null);
    }
    
    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_Null_When_FacilitySiteDoesNotExist(){
        FacilitySite facilitySite = facilitySiteServiceImpl.findByEisProgramIdAndReportId("XXXX", 2L);
        assertEquals(facilitySite, null);
    }
    
    @Test
    public void retrieveByState_Should_Return_FacilitySiteList_When_FacilitiesExist(){
        List<FacilitySite> facilitySiteList = facilitySiteServiceImpl.findByState("GA");
        assertNotEquals(facilitySiteList, null);
    }
    
    @Test
    public void retrieveByState_Should_Return_Null_When_NoFacilitySitesExist(){
        List<FacilitySite> facilitySiteList = facilitySiteServiceImpl.findByState("AK");
        assertEquals(facilitySiteList, null);
    }

}
