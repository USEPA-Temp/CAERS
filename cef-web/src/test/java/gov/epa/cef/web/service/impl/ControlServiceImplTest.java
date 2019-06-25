package gov.epa.cef.web.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.mapper.ControlMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ControlServiceImplTest {
    
    @Mock
    private ControlRepository controlRepo;
    
    @Mock
    private ControlMapper controlMapper;
    
    @InjectMocks
    private ControlServiceImpl controlServiceImpl;
    
    private ControlDto controlDto;
    private List<ControlDto> controlDtoList;
    
    @Before
    public void init(){
        Control control = new Control();
        List<Control> controlList = new ArrayList<Control>();
        List<Control> emptyControlList = new ArrayList<Control>();
        controlList.add(control);
        when(controlRepo.findById(1L)).thenReturn(Optional.of(control));
        when(controlRepo.findById(2L)).thenReturn(Optional.empty());
        when(controlRepo.findByFacilitySiteId(1L)).thenReturn(controlList);
        when(controlRepo.findByFacilitySiteId(2L)).thenReturn(emptyControlList);
        
        controlDto = new ControlDto();
        controlDtoList=new ArrayList<>();
        when(controlMapper.toDto(control)).thenReturn(controlDto);
        when(controlMapper.toDtoList(controlList)).thenReturn(controlDtoList);
    }

    @Test
    public void retrieveById_Should_Return_ControlObject_When_ControlExists(){
        ControlDto control = controlServiceImpl.retrieveById(1L);
        assertNotEquals(null, control);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_ControlDoesNotExist(){
        ControlDto control = controlServiceImpl.retrieveById(2L);
        assertEquals(null, control);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_IDisNull(){
        ControlDto control = controlServiceImpl.retrieveById(null);
        assertEquals(null, control);
    }
    
    @Test
    public void retrieveForFacilitySite_Should_Return_ControlList_When_ControlsExist() {
        Collection<ControlDto> controlList = controlServiceImpl.retrieveForFacilitySite(1L);
        assertNotEquals(null, controlList);
    }
    
    @Test
    public void retrieveForFacilitySite_Should_Return_Empty_When_ControlsDoNotExist() {
        Collection<ControlDto> controlList = controlServiceImpl.retrieveForFacilitySite(2L);
        assertEquals(new ArrayList<Control>(), controlList);
    }
    
}
