package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;
import gov.epa.cef.web.service.mapper.EmissionsProcessMapper;
import gov.epa.cef.web.service.mapper.ReleasePointApptMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmissionsProcessServiceImplTest extends BaseServiceTest {

    private EmissionsProcess emissionProcess;

    @Mock
    private EmissionsProcessRepository processRepo;

    @Mock
    private EmissionsProcessMapper emissionsProcessMapper;

    @Mock
    private ReleasePointApptMapper releasePointApptMapper;

	@InjectMocks
	private EmissionsProcessServiceImpl emissionsProcessServiceImpl;

	@Before
	public void init(){

	    emissionProcess=new EmissionsProcess();
        when(emissionsProcessMapper.emissionsProcessToEmissionsProcessDto(null)).thenReturn(null);
        when(emissionsProcessMapper.emissionsProcessToEmissionsProcessDto(emissionProcess)).thenReturn(new EmissionsProcessDto());

	    when(processRepo.findById(1L)).thenReturn(Optional.of(emissionProcess));
	    when(processRepo.findById(2L)).thenReturn(Optional.empty());

	    List<EmissionsProcess> emissionsProcessList=new ArrayList<>();
	    List<EmissionsProcessDto> emissionsProcessDtosList=new ArrayList<>();
        when(emissionsProcessMapper.emissionsProcessesToEmissionsProcessDtos(emissionsProcessList))
                .thenReturn(emissionsProcessDtosList);
        when(emissionsProcessMapper.emissionsProcessesToEmissionsProcessDtos(null)).thenReturn(null);

        when(processRepo.findByReleasePointApptsReleasePointId(1L)).thenReturn(emissionsProcessList);
	    when(processRepo.findByReleasePointApptsReleasePointId(2L)).thenReturn(null);

        when(processRepo.findByEmissionsUnitId(1L)).thenReturn(emissionsProcessList);
        when(processRepo.findByEmissionsUnitId(2L)).thenReturn(null);


	}

	@Test
	public void retrieveById_Should_ReturnEmissionProcessDtoObject_When_EmissionProcessExists(){
	    EmissionsProcessDto emissionProcess=emissionsProcessServiceImpl.retrieveById(1L);
	    assertNotEquals(null, emissionProcess);
	}

	@Test
    public void retrieveById_Should_ReturnNull_When_EmissionProcessNotExists(){
       EmissionsProcessDto emissionProcess=emissionsProcessServiceImpl.retrieveById(2L);
       assertEquals(null, emissionProcess);
    }

    @Test
    public void retrieveById_Should_ReturnNull_When_IdIsNull() {
        EmissionsProcessDto emissionProcess = emissionsProcessServiceImpl.retrieveById(null);
        assertEquals(null, emissionProcess);
    }

    @Test
    public void retrieveForReleasePoint_Should_ReturnEmissionProcessDtosList_When_ValidReleasePointIdPassed(){
        List<EmissionsProcessDto> emissionsProcessDtosList=emissionsProcessServiceImpl.retrieveForReleasePoint(1L);
        assertNotEquals(null, emissionsProcessDtosList);
    }

    @Test
    public void retrieveForReleasePoint_Should_ReturnNull_When_InvalidReleasePointIdPassed(){
        List<EmissionsProcessDto> emissionsProcessDtosList=emissionsProcessServiceImpl.retrieveForReleasePoint(2L);
        assertEquals(null, emissionsProcessDtosList);
    }

    @Test
    public void retrieveForEmissionsUnit_Should_ReturnEmissionProcessDtosList_When_ValidEmissionsUnitIdPassed(){
        List<EmissionsProcessDto> emissionsProcessDtosList=emissionsProcessServiceImpl.retrieveForEmissionsUnit(1L);
        assertNotEquals(null, emissionsProcessDtosList);
    }

    @Test
    public void retrieveForEmissionsUnit_Should_ReturnNull_When_InvalidEmissionsUnitIdPassed(){
        List<EmissionsProcessDto> emissionsProcessDtosList=emissionsProcessServiceImpl.retrieveForEmissionsUnit(2L);
        assertEquals(null, emissionsProcessDtosList);
    }

}
