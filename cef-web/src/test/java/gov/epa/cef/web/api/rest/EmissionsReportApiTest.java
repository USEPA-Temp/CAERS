package gov.epa.cef.web.api.rest;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import gov.epa.cef.web.service.EmissionsReportService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsReportApiTest {

    @Mock
    private EmissionsReportService emissionsReportService;

    @InjectMocks
    private EmissionsReportApi emissionsReportApi;
    
    private EmissionsReportDto emissionsReportDto;
    
    private List<EmissionsReportDto> emissionsReportDtoList;
    
    @Before
    public void init() {
        emissionsReportDto=new EmissionsReportDto();
        emissionsReportDtoList=new ArrayList<>();
        when(emissionsReportService.findById(1L)).thenReturn(emissionsReportDto);
        when(emissionsReportService.findByFacilityEisProgramId("p-id")).thenReturn(emissionsReportDtoList);
        when(emissionsReportService.findMostRecentByFacilityEisProgramId("p-id")).thenReturn(emissionsReportDto);
    }
    
    @Test
    public void retrieveReport_Should_ReturnEmissionsReportObjectWithOkStatus_When_ValidIdPassed() {
        ResponseEntity<EmissionsReportDto> result=emissionsReportApi.retrieveReport(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(emissionsReportDto, result.getBody());
    }

    @Test
    public void retrieveReportsForFacility_Should_ReturnEmissionsReportListWithStatusOk_When_ValidFacilityProgramIdPassed() {
        ResponseEntity<List<EmissionsReportDto>> result=emissionsReportApi.retrieveReportsForFacility("p-id");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(emissionsReportDtoList, result.getBody());
    }

    @Test
    public void retrieveCurrentReportForFacility_Should_ReturnCurrentEmissionsReportWithStatusOk_When_ValidFacilityProgramIdPassed() {
        ResponseEntity<EmissionsReportDto> result=emissionsReportApi.retrieveCurrentReportForFacility("p-id");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(emissionsReportDto, result.getBody());
    }
}
