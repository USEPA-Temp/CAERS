package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.ReportSummary;
import gov.epa.cef.web.repository.ReportSummaryRepository;
import gov.epa.cef.web.service.dto.ReportSummaryDto;
import gov.epa.cef.web.service.mapper.ReportSummaryMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReportServiceImplTest {
    
    @Mock
    private ReportSummaryRepository reportSummaryRepo;
    
    @Mock
    private ReportSummaryMapper reportSummaryMapper;
    
    @InjectMocks
    private ReportServiceImpl reportServiceImpl;
    
    @Before
    public void init() {
        List<ReportSummary> emptyReportSummaryList = new ArrayList<ReportSummary>();
        List<ReportSummaryDto> fullReportSummaryDtoList = new ArrayList<ReportSummaryDto>();
        List<ReportSummary> fullReportSummaryList = new ArrayList<ReportSummary>();
        when(reportSummaryRepo.findByReportYearAndFacilitySiteId(new Short("2000"), 1L)).thenReturn(emptyReportSummaryList);
        when(reportSummaryRepo.findByReportYearAndFacilitySiteId(new Short("2019"), 2L)).thenReturn(emptyReportSummaryList);

        for (int x = 1; x <= 5; x++) {
            ReportSummary rs = new ReportSummary();
            rs.setCasId("ABC123-" + String.valueOf(x));
            rs.setFacilitySiteId(1L);
            rs.setFugitiveTotal(new Double(x*10));
            rs.setId(1L);
            rs.setPollutantName("Test Pollutant-" + String.valueOf(x));
            rs.setPollutantType("HAP");
            rs.setPreviousYearTotal(new Double(0));
            rs.setReportYear(new Short("2019"));
            rs.setStackTotal(new Double(x*20));
            rs.setEmissionsTonsTotal(rs.getFugitiveTotal() + rs.getStackTotal());
            rs.setUom("tons");
            fullReportSummaryList.add(rs);

            ReportSummaryDto dto = new ReportSummaryDto();
            dto.setCasId(rs.getCasId());
            dto.setFacilitySiteId(rs.getFacilitySiteId());
            dto.setFugitiveTotal(rs.getFugitiveTotal());
            dto.setId(rs.getId());
            dto.setPollutantName(rs.getPollutantName());
            dto.setPollutantType(rs.getPollutantType());
            dto.setPreviousYearTotal(rs.getPreviousYearTotal());
            dto.setReportYear(rs.getReportYear());
            dto.setStackTotal(rs.getStackTotal());
            dto.setEmissionsTonsTotal(rs.getEmissionsTonsTotal());
            dto.setUom(rs.getUom());
            fullReportSummaryDtoList.add(dto);
        }
        when(reportSummaryRepo.findByReportYearAndFacilitySiteId(new Short("2019"), 1L)).thenReturn(fullReportSummaryList);
        when(reportSummaryMapper.toDtoList(fullReportSummaryList)).thenReturn(fullReportSummaryDtoList);
    }
    
    @Test
    public void findByReportYearAndFacilitySiteId_should_return_total_emissions_when_year_and_facility_exist() {
        List<ReportSummaryDto> dtoList = reportServiceImpl.findByReportYearAndFacilitySiteId(new Short("2019"), 1L);
        assertEquals(5, dtoList.size());

        ReportSummaryDto dto = dtoList.get(0);
        assertEquals("ABC123-1", dto.getCasId());
        assertEquals(new Double(10), dto.getFugitiveTotal());
        assertEquals(new Double(30), dto.getEmissionsTonsTotal());
    }

    @Test
    public void findByReportYearAndFacilitySiteId_should_return_empty_when_year_does_not_exist_for_facility() {
        List<ReportSummaryDto> dtoList = reportServiceImpl.findByReportYearAndFacilitySiteId(new Short("2019"), 2L);
        ArrayList<ReportSummaryDto> emptyList = new ArrayList<ReportSummaryDto>();
        assertEquals(emptyList, dtoList);
    }
    
    @Test
    public void findByReportYearAndFacilitySiteId_should_return_empty_when_facility_does_not_exist_for_year() {
        List<ReportSummaryDto> dtoList = reportServiceImpl.findByReportYearAndFacilitySiteId(new Short("2000"), 1L);
        ArrayList<ReportSummaryDto> emptyList = new ArrayList<ReportSummaryDto>();
        assertEquals(emptyList, dtoList);
    }
}
