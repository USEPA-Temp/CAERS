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

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.mapper.ReleasePointMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReleasePointServiceImplTest {
    
    @Mock
    private ReleasePointRepository releasePointRepo;
    
    @Mock
    private ReleasePointMapper releasePointMapper;
    
    @InjectMocks
    private ReleasePointServiceImpl releasePointServiceImpl;
    
    private ReleasePointDto releasePointDto;
    private List<ReleasePointDto> releasePointDtoList;
    
    @Before
    public void init(){
        ReleasePoint releasePoint = new ReleasePoint();
        List<ReleasePoint> releasePointList = new ArrayList<ReleasePoint>();
        List<ReleasePoint> emptyReleasePointList = new ArrayList<ReleasePoint>();
        releasePointList.add(releasePoint);
        when(releasePointRepo.findById(1L)).thenReturn(Optional.of(releasePoint));
        when(releasePointRepo.findById(2L)).thenReturn(Optional.empty());
        when(releasePointRepo.findByFacilitySiteId(1L)).thenReturn(releasePointList);
        when(releasePointRepo.findByFacilitySiteId(2L)).thenReturn(emptyReleasePointList);
        
        releasePointDto=new ReleasePointDto();
        releasePointDtoList=new ArrayList<>();
        when(releasePointMapper.toDto(releasePoint)).thenReturn(releasePointDto);
        when(releasePointMapper.toDtoList(releasePointList)).thenReturn(releasePointDtoList);
    }

    @Test
    public void retrieveById_Should_Return_ReleasePointObject_When_ReleasePointExists(){
        ReleasePointDto releasePoint = releasePointServiceImpl.retrieveById(1L);
        assertNotEquals(null, releasePoint);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_ReleasePointDoesNotExist(){
        ReleasePointDto releasePoint = releasePointServiceImpl.retrieveById(2L);
        assertEquals(null, releasePoint);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_IDisNull(){
        ReleasePointDto releasePoint = releasePointServiceImpl.retrieveById(null);
        assertEquals(null, releasePoint);
    }
    
    @Test
    public void retrieveByFacilitySiteId_Should_Return_ReleasePointList_When_ReleasePointsExist() {
        Collection<ReleasePointDto> releasePointList = releasePointServiceImpl.retrieveByFacilitySiteId(1L);
        assertNotEquals(null, releasePointList);
    }
    
    @Test
    public void retrieveByFacilitySiteId_Should_Return_Empty_When_ReleasePointDoNotExist() {
        Collection<ReleasePointDto> releasePointList = releasePointServiceImpl.retrieveByFacilitySiteId(2L);
        assertEquals(new ArrayList<ReleasePoint>(), releasePointList);
    }
    
}
