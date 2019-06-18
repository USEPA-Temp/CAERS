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

import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;

@RunWith(MockitoJUnitRunner.class)
public class EmissionsUnitApiTest {

    @Mock
    private EmissionsUnitService emissionsUnitService;

    @InjectMocks
    private EmissionsUnitApi emissionsUnitApi;
    
    private EmissionsUnitDto emissionsUnit;
    
    private List<EmissionsUnitDto> emissionsUnitDtos;
    
    @Before
    public void init() {
        emissionsUnit=new EmissionsUnitDto();
        when(emissionsUnitService.retrieveUnitById(123L)).thenReturn(emissionsUnit);
        
        emissionsUnitDtos=new ArrayList<>();
        emissionsUnitDtos.add(emissionsUnit);
        when(emissionsUnitService.retrieveEmissionUnitsForFacility(1L)).thenReturn(emissionsUnitDtos);
        
    }
    
    @Test
    public void retrieveEmissionsUnit_Should_ReturnEmissionsUnitObectWithOkStatusCode_When_ValidIdPassed() {
        ResponseEntity<EmissionsUnitDto> result=emissionsUnitApi.retrieveEmissionsUnit(123L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(emissionsUnit, result.getBody());
    }
    
    @Test
    public void retrieveEmissionsUnitsOfFacility_Should_ReturnEmissionsUnitListWithOkStatusCode_WhenValidFacilitIdPassed() {
        ResponseEntity<List<EmissionsUnitDto>> result=emissionsUnitApi.retrieveEmissionsUnitsOfFacility(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(emissionsUnitDtos, result.getBody());
    }
}
