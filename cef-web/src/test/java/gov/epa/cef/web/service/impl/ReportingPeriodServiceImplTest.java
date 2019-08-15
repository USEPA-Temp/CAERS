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

import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import gov.epa.cef.web.service.mapper.ReportingPeriodMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReportingPeriodServiceImplTest {
    
    @Mock
    private ReportingPeriodRepository reportingPeriodRepo;
    
    @Mock
    private ReportingPeriodMapper reportingPeriodMapper;
    
    @InjectMocks
    private ReportingPeriodServiceImpl reportingPeriodServiceImpl;
    
    private ReportingPeriod reportingPeriod;
    private ReportingPeriodDto reportingPeriodDto;
    private List<ReportingPeriodDto> reportingPeriodDtoList;
    
    @Before
    public void init(){
        reportingPeriod = new ReportingPeriod();
        reportingPeriod.setId(1L);
        List<ReportingPeriod> reportingPeriodList = new ArrayList<ReportingPeriod>();
        List<ReportingPeriod> emptyReportingPeriodList = new ArrayList<ReportingPeriod>();
        reportingPeriodList.add(reportingPeriod);
        when(reportingPeriodRepo.findById(1L)).thenReturn(Optional.of(reportingPeriod));
        when(reportingPeriodRepo.findById(2L)).thenReturn(Optional.empty());
        when(reportingPeriodRepo.findByEmissionsProcessId(1L)).thenReturn(reportingPeriodList);
        when(reportingPeriodRepo.findByEmissionsProcessId(2L)).thenReturn(emptyReportingPeriodList);
        when(reportingPeriodRepo.save(reportingPeriod)).thenReturn(reportingPeriod);
        
        reportingPeriodDto = new ReportingPeriodDto();
        reportingPeriodDto.setId(1L);
        reportingPeriodDtoList=new ArrayList<>();
        when(reportingPeriodMapper.toDto(reportingPeriod)).thenReturn(reportingPeriodDto);
        when(reportingPeriodMapper.toDtoList(reportingPeriodList)).thenReturn(reportingPeriodDtoList);
        
    }

    @Test
    public void update_Should_ReportingPeriodObject_When_ReportingPeriodExists(){
        ReportingPeriodDto reportingPeriod = reportingPeriodServiceImpl.update(reportingPeriodDto);
        assertNotEquals(null, reportingPeriod);
    }

    @Test
    public void retrieveById_Should_Return_ReportingPeriodObject_When_ReportingPeriodExists(){
        ReportingPeriodDto reportingPeriod = reportingPeriodServiceImpl.retrieveById(1L);
        assertNotEquals(null, reportingPeriod);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_ReportingPeriodDoesNotExist(){
        ReportingPeriodDto reportingPeriod = reportingPeriodServiceImpl.retrieveById(2L);
        assertEquals(null, reportingPeriod);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_IDisNull(){
        ReportingPeriodDto reportingPeriod = reportingPeriodServiceImpl.retrieveById(null);
        assertEquals(null, reportingPeriod);
    }
    
    @Test
    public void retrieveByFacilitySiteId_Should_Return_ReportingPeriodList_When_ReportingPeriodsExist() {
        Collection<ReportingPeriodDto> reportingPeriodList = reportingPeriodServiceImpl.retrieveForEmissionsProcess(1L);
        assertNotEquals(null, reportingPeriodList);
    }
    
    @Test
    public void retrieveByFacilitySiteId_Should_Return_Empty_When_ReportingPeriodDoNotExist() {
        Collection<ReportingPeriodDto> reportingPeriodList = reportingPeriodServiceImpl.retrieveForEmissionsProcess(2L);
        assertEquals(new ArrayList<ReportingPeriod>(), reportingPeriodList);
    }
    
}
