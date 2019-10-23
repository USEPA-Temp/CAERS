package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.security.FacilityAccessEnforcer;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FacilitySiteApiTest extends BaseApiTest {

    @Mock
    private FacilitySiteService facilityService;

    @Mock
    private FacilitySiteMapper facilitySiteMapper;

    @Mock
    private SecurityService securityService;

    @Mock
    private FacilityAccessEnforcer facilityAccessEnforcer;

    @InjectMocks
    private FacilitySiteApi facilitySiteApi;

    private FacilitySiteDto facilitSiteDto;

    @Before
    public void init() {
        facilitSiteDto=new FacilitySiteDto();
        when(facilityService.findById(123L)).thenReturn(facilitSiteDto);
        when(facilityService.findByEisProgramIdAndReportId("p-id", 1L)).thenReturn(facilitSiteDto);

        when(securityService.facilityEnforcer()).thenReturn(facilityAccessEnforcer);
    }

    @Test
    public void retrieveFacilitySite_Should_ReturnFacilitySiteDtoObject_When_ValidIdPassed() {
        ResponseEntity<FacilitySiteDto> result=facilitySiteApi.retrieveFacilitySite(123L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(facilitSiteDto, result.getBody());
    }

    @Test
    public void retrieveFacilitySiteByProgramIdAndReportId_Should_ReturnFacilitySiteDto_When_ValidProgramIdAndReportIdPassed() {
        ResponseEntity<FacilitySiteDto> result=facilitySiteApi.retrieveFacilitySiteByProgramIdAndReportId(1L, "p-id");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(facilitSiteDto, result.getBody());

    }

}
