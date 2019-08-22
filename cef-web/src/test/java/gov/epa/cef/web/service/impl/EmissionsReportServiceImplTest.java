package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsReportServiceImplTest extends BaseServiceTest {

    @Mock
    private EmissionsReportRepository erRepo;

    @Mock
    private EmissionsReportMapper emissionsReportMapper;

    @InjectMocks
    private EmissionsReportServiceImpl emissionsReportServiceImpl;


    private EmissionsReportDto emissionsReportDto;

    private List<EmissionsReportDto> emissionsReportDtoList;

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
        when(erRepo.findByEisProgramId("XXXX", new Sort(Sort.Direction.DESC, "year"))).thenReturn(emissionsReportList);

        emissionsReportDto=new EmissionsReportDto();
        emissionsReportDtoList=new ArrayList<>();
        when(emissionsReportMapper.toDto(emissionsReport)).thenReturn(emissionsReportDto);
        when(emissionsReportMapper.toDtoList(emissionsReportList)).thenReturn(emissionsReportDtoList);
    }

    @Test
    public void findById_Should_Return_EmissionsReportObject_When_EmissionsReportExists(){
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findById(1L);
        assertEquals(emissionsReportDto, emissionsReport);
    }

    @Test
    public void findById_Should_Return_Null_When_EmissionsReportNotExist(){
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findById(2L);
        assertEquals(null, emissionsReport);
    }

    @Test
    public void findById_Should_Return_Null_When_IDisNull(){
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findById(null);
        assertEquals(null, emissionsReport);
    }

    @Test
    public void findByFacilityId_Should_Return_ReportList_WhenReportExist() {
        Collection<EmissionsReportDto> emissionsReportList = emissionsReportServiceImpl.findByFacilityEisProgramId("XXXX");
        assertEquals(emissionsReportDtoList, emissionsReportList);
    }

    @Test
    public void findByFacilityId_Should_Return_Empty_WhenReportsDoNotExist() {
        Collection<EmissionsReportDto> emissionsReportList = emissionsReportServiceImpl.findByFacilityEisProgramId("YYYY");
        assertEquals(0, emissionsReportList.size());
    }

    @Test
    public void findMostRecentByFacilityEisProgramId_Should_ReturnTheLatestEmissionsReportForAFacility_WhenValidFacilityEisProgramIdPassed() {
        EmissionsReportDto emissionsReport = emissionsReportServiceImpl.findMostRecentByFacilityEisProgramId("XXXX");
        assertNotEquals(null, emissionsReport);
    }
}
