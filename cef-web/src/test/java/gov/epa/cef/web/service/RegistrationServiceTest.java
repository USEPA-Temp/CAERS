package gov.epa.cef.web.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.config.CdxConfig;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.impl.RegistrationServiceImpl;
import gov.epa.cef.web.soap.RegisterFacilityClient;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

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
		when(cefConfig.getCdxConfig().getRegisterProgramFacilityServiceEndpoint()).thenReturn("http:\\mockurl");
		when(cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorUser()).thenReturn("mock-user");
		when(cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorPassword()).thenReturn("mock-password");
		when(registerFacilityClient.authenticate(new URL(mockCdxUrl), mockUser, mockPassword)).thenReturn("token");
		when(registerFacilityClient.getFacilitiesByUserRoleId(new URL(mockCdxUrl), "token", 123L)).thenReturn(facilities);
		when(registerFacilityClient.getFacilityByProgramId(new URL(mockCdxUrl), "token", "pId")).thenReturn(facilities);
	}
	
	@Test
	public void testRetrieveFacilities() {
		ProgramFacility programFacility=new ProgramFacility();
		programFacility.setFacilityName("Test-Facility");
		facilities.add(programFacility);
		assertTrue(registrationServiceImpl.retrieveFacilities(123L).size()==1);
	}
	
	@Test
	public void testRetrieveFacilitiesEmptyList() {
		facilities=new ArrayList<>();
		assertTrue(registrationServiceImpl.retrieveFacilities(123L).size()==0);
	}
	
	@Test(expected = ApplicationException.class) 
	public void testRetrieveFacilitiesException() {
		facilities.add(null);
		registrationServiceImpl.retrieveFacilities(123L);
	}
	
	@Test
	public void testRetrieveFacilityByProgramIdNull() {
		facilities=new ArrayList<>();
		assertTrue(registrationServiceImpl.retrieveFacilityByProgramId("pId")==null);
	}
	
	@Test
	public void testRetrieveFacilityByProgramId(){
		ProgramFacility programFacility=new ProgramFacility();
		programFacility.setFacilityName("Test-Facility");
		facilities.add(programFacility);
		assertTrue(registrationServiceImpl.retrieveFacilityByProgramId("pId")!=null);
	}

	@Test(expected = ApplicationException.class) 
	public void testRetrieveFacilityByProgramIdException(){
		facilities.add(null);
		registrationServiceImpl.retrieveFacilityByProgramId("pId");
	}
	
}
