package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.EmissionFactor;
import gov.epa.cef.web.repository.EmissionFactorRepository;
import gov.epa.cef.web.service.dto.EmissionFactorDto;
import gov.epa.cef.web.service.mapper.EmissionFactorMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionFactorServiceImplTest extends BaseServiceTest {

    @Mock
    private EmissionFactorRepository emissionFactorRepo;

    @Mock
    private EmissionFactorMapper emissionFactorMapper;

    @InjectMocks
    private EmissionFactorServiceImpl emissionFactorServiceImpl;

    private EmissionFactorDto emissionFactorDto;
    private EmissionFactorDto emissionFactorDto2;
    private List<EmissionFactorDto> emissionFactorDtoList;
    private List<EmissionFactorDto> emptyEmissionFactorDtoList;

    @Before
    public void init(){
        EmissionFactor emissionFactor = new EmissionFactor();
        emissionFactor.setId(1L);
        EmissionFactor emissionFactor2 = new EmissionFactor();
        emissionFactor2.setId(2L);
        List<EmissionFactor> emissionFactorList = new ArrayList<EmissionFactor>();
        List<EmissionFactor> emptyEmissionFactorList = new ArrayList<EmissionFactor>();
        emissionFactorList.add(emissionFactor);
        when(emissionFactorRepo.findAll(Example.of(emissionFactor))).thenReturn(emissionFactorList);
        when(emissionFactorRepo.findAll(Example.of(emissionFactor2))).thenReturn(emptyEmissionFactorList);

        emissionFactorDto = new EmissionFactorDto();
        emissionFactorDto.setId(1L);
        emissionFactorDto2 = new EmissionFactorDto();
        emissionFactorDto2.setId(2L);
        emissionFactorDtoList = new ArrayList<EmissionFactorDto>();
        emptyEmissionFactorDtoList = new ArrayList<EmissionFactorDto>();
        emissionFactorDtoList.add(emissionFactorDto);
        when(emissionFactorMapper.toDto(emissionFactor)).thenReturn(emissionFactorDto);
        when(emissionFactorMapper.toDto(emissionFactor2)).thenReturn(emissionFactorDto2);
        when(emissionFactorMapper.toDtoList(emissionFactorList)).thenReturn(emissionFactorDtoList);
        when(emissionFactorMapper.toDtoList(emptyEmissionFactorList)).thenReturn(emptyEmissionFactorDtoList);
        when(emissionFactorMapper.fromDto(emissionFactorDto)).thenReturn(emissionFactor);
        when(emissionFactorMapper.fromDto(emissionFactorDto2)).thenReturn(emissionFactor2);
    }

    @Test
    public void retrieveByExample_Should_Return_EmissionFactorList_When_EmissionFactorsExists(){
        List<EmissionFactorDto> result = emissionFactorServiceImpl.retrieveByExample(emissionFactorDto);
        assertNotEquals(null, result);
    }

    @Test
    public void retrieveByExample_Should_Return_Empty_When_EmissionFactorsDoNotExist(){
        List<EmissionFactorDto> result = emissionFactorServiceImpl.retrieveByExample(emissionFactorDto2);
        assertEquals(new ArrayList<EmissionFactorDto>(), result);
    }

}
