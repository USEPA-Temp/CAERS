package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FacilitySiteServiceImplTest extends BaseServiceTest {

    @Mock
    private FacilitySiteRepository facSiteRepo;

    @Mock
    private FacilitySiteMapper facilitySiteMapper;

    @InjectMocks
    private FacilitySiteServiceImpl facilitySiteServiceImpl;

    @Before
    public void init(){
        FacilitySite testSite = new FacilitySite();
        when(facSiteRepo.findById(1L)).thenReturn(Optional.of(testSite));
        when(facSiteRepo.findById(2L)).thenReturn(Optional.empty());

        FacilitySiteDto facilitySiteDto=new FacilitySiteDto();
        when(facilitySiteMapper.toDto(testSite)).thenReturn(facilitySiteDto);

        List<FacilitySite> testSiteList = new ArrayList<FacilitySite>();
        testSiteList.add(testSite);
        List<FacilitySite> nullSiteList =null;
        when(facSiteRepo.findByEisProgramIdAndEmissionsReportId("XXXX", 1L)).thenReturn(testSiteList);
        when(facSiteRepo.findByEisProgramIdAndEmissionsReportId("XXXX", 2L)).thenReturn(new ArrayList<>());
        when(facSiteRepo.findByStateCode("GA")).thenReturn(testSiteList);
        when(facSiteRepo.findByStateCode("AK")).thenReturn(nullSiteList);
        when(facilitySiteMapper.toDtoList(nullSiteList)).thenReturn(null);
    }

    @Test
    public void retrieveById_Should_Return_FacilitySiteObject_When_FacilitySiteExists(){
        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findById(1L);
        assertNotEquals(null, facilitySite);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_FacilitySiteDoesNotExist(){
        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findById(2L);
        assertEquals(null, facilitySite);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_FacilitySiteIDisNull(){
        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findById(null);
        assertEquals(null, facilitySite);
    }

    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_FacilitySiteObject_When_FacilitySiteExists(){
        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findByEisProgramIdAndReportId("XXXX", 1L);
        assertNotEquals(null, facilitySite);
    }

    @Test
    public void retrieveByEisProgramIdAndReportId_Should_Return_Null_When_FacilitySiteDoesNotExist(){
        FacilitySiteDto facilitySite = facilitySiteServiceImpl.findByEisProgramIdAndReportId("XXXX", 2L);
        assertEquals(null, facilitySite);
    }
}
