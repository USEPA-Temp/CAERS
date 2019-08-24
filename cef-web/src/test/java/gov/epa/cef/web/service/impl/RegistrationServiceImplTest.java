package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.CdxConfig;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.soap.RegisterFacilityClient;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest extends BaseServiceTest {

	@Mock
	private RegisterFacilityClient registerFacilityClient;

	@Mock
	private CefConfig cefConfig;

	@Mock
	private CdxConfig cdxConfig;

	@InjectMocks
	private RegistrationServiceImpl registrationServiceImpl;

	private List<ProgramFacility> facilities=new ArrayList<>();

	@Before
	public void init() throws ApplicationException, MalformedURLException {
		String mockCdxUrl="http:\\mockurl";
		String mockUser="mock-user";
		String mockPassword="mock-password";
		when(cefConfig.getCdxConfig()).thenReturn(cdxConfig);
		when(cefConfig.getCdxConfig().getRegisterProgramFacilityServiceEndpoint()).thenReturn(mockCdxUrl);
		when(cefConfig.getCdxConfig().getNaasUser()).thenReturn("mock-user");
		when(cefConfig.getCdxConfig().getNaasPassword()).thenReturn("mock-password");
		when(registerFacilityClient.authenticate(new URL(mockCdxUrl), mockUser, mockPassword)).thenReturn("token");
		when(registerFacilityClient.getFacilitiesByUserRoleId(new URL(mockCdxUrl), "token", 123L)).thenReturn(facilities);
		when(registerFacilityClient.getFacilitiesByUserRoleId(new URL(mockCdxUrl), "token", 545L)).thenReturn(new ArrayList<>());
		when(registerFacilityClient.getFacilityByProgramId(new URL(mockCdxUrl), "token", "pId")).thenReturn(new ArrayList<>());
		when(registerFacilityClient.getFacilityByProgramId(new URL(mockCdxUrl), "token", "pId2")).thenReturn(facilities);
	}

	@Test
	public void retrieveFacilities_Should_ReturnFacilitiesList_When_ValidUserRoleIdPassed() {
		ProgramFacility programFacility=new ProgramFacility();
		programFacility.setFacilityName("Test-Facility");
		facilities.add(programFacility);
		assertEquals(registrationServiceImpl.retrieveFacilities(123L).size()==1, Boolean.TRUE);
	}

	@Test
	public void retrieveFacilities_Should_ReturnEmptyFacilitiesList_When_InvalidUserRoleIdPassed() {
	    assertEquals(registrationServiceImpl.retrieveFacilities(545L).size()==0, Boolean.TRUE);
	}

	@Test(expected = ApplicationException.class)
	public void retrieveFacilities_Should_ThrowException_When_FacilitiesListHasNullValues() {
		facilities.add(null);
		registrationServiceImpl.retrieveFacilities(123L);
	}

	@Test
	public void retrieveFacilityByProgramId_Should_ReturnNull_When_ProgramIdHasNoFacilities() {
		assertEquals(null, registrationServiceImpl.retrieveFacilityByProgramId("pId"));
	}

	@Test
	public void retrieveFacilityByProgramId_Should_ReturnFacilitiesList_When_ProgramIdHasFacilities(){
		ProgramFacility programFacility=new ProgramFacility();
		programFacility.setFacilityName("Test-Facility");
		facilities.add(programFacility);
		assertNotEquals(null, registrationServiceImpl.retrieveFacilityByProgramId("pId2"));
	}

	@Test(expected = ApplicationException.class)
	public void retrieveFacilityByProgramId_Should_ThrowException_When_FacilitiesListHasNullValues(){
		facilities.add(null);
		registrationServiceImpl.retrieveFacilityByProgramId("pId2");
	}

}
