package gov.epa.cef.web.api.rest;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.security.FacilityAccessEnforcer;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.RegistrationService;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CdxFacilityApiTest extends BaseApiTest {

    @Mock
    private RegistrationService registrationService;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private CdxFacilityApi cdxFacilityApi;

    @Mock
    private ApplicationUser appicationUser;

    @Mock
    private FacilityAccessEnforcer facilityAccessEnforcer;

    private ProgramFacility programFacility;

    private List<ProgramFacility> programFacilities;

    @Before
    public void init() {
        programFacility=new ProgramFacility();
        when(registrationService.retrieveFacilityByProgramId("p-id")).thenReturn(programFacility);

        programFacilities=new ArrayList<>();
        programFacilities.add(programFacility);
        when(registrationService.retrieveFacilities(123L)).thenReturn(programFacilities);

        when(securityService.facilityEnforcer()).thenReturn(facilityAccessEnforcer);

        when(securityService.getCurrentApplicationUser()).thenReturn(appicationUser);
        when(securityService.getCurrentApplicationUser().getUserRoleId()).thenReturn(123L);
    }

    @Test
    public void retrieveFacility_Should_ReturnObjectWithOkStatusCode_When_ProgramIdIsPassed() {
        ResponseEntity<ProgramFacility> result = cdxFacilityApi.retrieveFacility("p-id");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(programFacility, result.getBody());
    }

    @Test
    public void retrieveFacilitiesForUser_Should_ReturnFacilitiesListWithOkStatusCode_WhenValidUserRoleIdPassed() {
        ResponseEntity<Collection<ProgramFacility>> result = cdxFacilityApi.retrieveFacilitiesForUser(123L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(programFacilities, result.getBody());
    }

    @Test
    public void retrieveFacilitiesForCurrentUser_Should_ReturnCurrentUserFacilitiesListWithStatusOk_When_ValidUserAlreadyAuthenticated() {
        ResponseEntity<Collection<ProgramFacility>> result =cdxFacilityApi.retrieveFacilitiesForCurrentUser();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(programFacilities, result.getBody());
    }

}
