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

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.service.dto.ControlAssignmentDto;
import gov.epa.cef.web.service.mapper.ControlAssignmentMapper;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ControlAssignmentServiceImplTest {
    
    @Mock
    private ControlAssignmentRepository repo;
    
    @Mock
    private ControlAssignmentMapper controlMapper;
    
    @InjectMocks
    private ControlAssignmentServiceImpl serviceImpl;
    
    private ControlAssignmentDto controlAssignmentDto;
    private List<ControlAssignmentDto> controlAssignmentDtoList;
    
    @Before
    public void init(){
        ControlAssignment control = new ControlAssignment();
        List<ControlAssignment> controlList = new ArrayList<ControlAssignment>();
        List<ControlAssignment> emptyControlList = new ArrayList<ControlAssignment>();
        controlList.add(control);
        when(repo.findById(1L)).thenReturn(Optional.of(control));
        when(repo.findById(2L)).thenReturn(Optional.empty());
        when(repo.findByEmissionsProcessId(3L)).thenReturn(controlList);
        when(repo.findByEmissionsProcessId(4L)).thenReturn(emptyControlList);
        when(repo.findByEmissionsUnitId(5L)).thenReturn(controlList);
        when(repo.findByEmissionsUnitId(6L)).thenReturn(emptyControlList);
        when(repo.findByReleasePointId(7L)).thenReturn(controlList);
        when(repo.findByReleasePointId(8L)).thenReturn(emptyControlList);
        
        controlAssignmentDto = new ControlAssignmentDto();
        controlAssignmentDtoList=new ArrayList<>();
        when(controlMapper.toDto(control)).thenReturn(controlAssignmentDto);
        when(controlMapper.toDtoList(controlList)).thenReturn(controlAssignmentDtoList);
    }

    @Test
    public void retrieveById_Should_Return_ControlObject_When_ControlExists(){
        ControlAssignmentDto controlAssignment = serviceImpl.retrieveById(1L);
        assertNotEquals(null, controlAssignment);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_ControlAssignmentDoesNotExist(){
        ControlAssignmentDto controlAssignment = serviceImpl.retrieveById(2L);
        assertEquals(null, controlAssignment);
    }
    
    @Test
    public void retrieveById_Should_Return_Null_When_IDisNull(){
        ControlAssignmentDto controlAssignment = serviceImpl.retrieveById(null);
        assertEquals(null, controlAssignment);
    }
    
    @Test
    public void retrieveForEmissionsProcess_Should_Return_ControlList_When_ControlAssignmentsExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForEmissionsProcess(3L);
        assertNotEquals(null, controlAssignmentList);
    }
    
    @Test
    public void retrieveForEmissionsProcess_Should_Return_Empty_When_ControlAssignmentsDoNotExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForEmissionsProcess(4L);
        assertEquals(new ArrayList<ControlAssignment>(), controlAssignmentList);
    }
    
    @Test
    public void retrieveForEmissionsUnit_Should_Return_ControlList_When_ControlAssignmentsExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForEmissionsUnit(5L);
        assertNotEquals(null, controlAssignmentList);
    }
    
    @Test
    public void retrieveForEmissionsUnit_Should_Return_Empty_When_ControlAssignmentsDoNotExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForEmissionsUnit(6L);
        assertEquals(new ArrayList<ControlAssignment>(), controlAssignmentList);
    }
    
    @Test
    public void retrieveForReleasePoint_Should_Return_ControlList_When_ControlAssignmentsExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForReleasePoint(7L);
        assertNotEquals(null, controlAssignmentList);
    }
    
    @Test
    public void retrieveForReleasePoint_Should_Return_Empty_When_ControlAssignmentsDoNotExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForReleasePoint(8L);
        assertEquals(new ArrayList<ControlAssignment>(), controlAssignmentList);
    }
    
}
