package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.client.soap.RegisterFacilityClient;
import gov.epa.cef.web.exception.ApplicationException;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest extends BaseServiceTest {

	@Mock
	private RegisterFacilityClient registerFacilityClient;

	@InjectMocks
	private RegistrationServiceImpl registrationServiceImpl;

	private List<ProgramFacility> facilities=new ArrayList<>();

	@Before
	public void init() throws ApplicationException, MalformedURLException {

		when(registerFacilityClient.getFacilitiesByUserRoleId(123L)).thenReturn(facilities);
		when(registerFacilityClient.getFacilitiesByUserRoleId(545L)).thenReturn(new ArrayList<>());

        when(registerFacilityClient.getFacilityByProgramIds(Collections.singletonList("pId")))
            .thenReturn(new ArrayList<>());

        when(registerFacilityClient.getFacilityByProgramIds(Collections.singletonList("pId2")))
            .thenReturn(facilities);
	}

	@Test
	public void retrieveFacilities_Should_ReturnFacilitiesList_When_ValidUserRoleIdPassed() {
		ProgramFacility programFacility=new ProgramFacility();
		programFacility.setFacilityName("Test-Facility");
		facilities.add(programFacility);
		assertEquals(registrationServiceImpl.retrieveFacilities(123L).size()==2, Boolean.TRUE);
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
