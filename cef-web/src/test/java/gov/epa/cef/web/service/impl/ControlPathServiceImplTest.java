package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.repository.ControlPathRepository;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.mapper.ControlPathMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ControlPathServiceImplTest extends BaseServiceTest {

    @Mock
    private ControlPathRepository repo;

    @Mock
    private ControlPathMapper controlMapper;

    @InjectMocks
    private ControlPathServiceImpl serviceImpl;

    private ControlPathDto controlPathDto;
    private List<ControlPathDto> controlPathDtoList;

    @Before
    public void init(){
        ControlPath control = new ControlPath();
        List<ControlPath> controlList = new ArrayList<ControlPath>();
        List<ControlPath> emptyControlList = new ArrayList<ControlPath>();
        controlList.add(control);
        when(repo.findById(1L)).thenReturn(Optional.of(control));
        when(repo.findById(2L)).thenReturn(Optional.empty());
        when(repo.findByEmissionsProcessId(3L)).thenReturn(controlList);
        when(repo.findByEmissionsProcessId(4L)).thenReturn(emptyControlList);
        when(repo.findByEmissionsUnitId(5L)).thenReturn(controlList);
        when(repo.findByEmissionsUnitId(6L)).thenReturn(emptyControlList);
        when(repo.findByReleasePointId(7L)).thenReturn(controlList);
        when(repo.findByReleasePointId(8L)).thenReturn(emptyControlList);

        controlPathDto = new ControlPathDto();
        controlPathDtoList=new ArrayList<>();
        when(controlMapper.toDto(control)).thenReturn(controlPathDto);
        when(controlMapper.toDtoList(controlList)).thenReturn(controlPathDtoList);
    }

    @Test
    public void retrieveById_Should_Return_ControlObject_When_ControlExists(){
        ControlPathDto controlPath = serviceImpl.retrieveById(1L);
        assertNotEquals(null, controlPath);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_ControlPathDoesNotExist(){
        ControlPathDto controlPath = serviceImpl.retrieveById(2L);
        assertEquals(null, controlPath);
    }

    @Test
    public void retrieveById_Should_Return_Null_When_IDisNull(){
        ControlPathDto controlPath = serviceImpl.retrieveById(null);
        assertEquals(null, controlPath);
    }

    @Test
    public void retrieveForEmissionsProcess_Should_Return_ControlList_When_ControlPathsExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForEmissionsProcess(3L);
        assertNotEquals(null, controlPathList);
    }

    @Test
    public void retrieveForEmissionsProcess_Should_Return_Empty_When_ControlPathsDoNotExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForEmissionsProcess(4L);
        assertEquals(new ArrayList<ControlPath>(), controlPathList);
    }

    @Test
    public void retrieveForEmissionsUnit_Should_Return_ControlList_When_ControlPathsExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForEmissionsUnit(5L);
        assertNotEquals(null, controlPathList);
    }

    @Test
    public void retrieveForEmissionsUnit_Should_Return_Empty_When_ControlPathsDoNotExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForEmissionsUnit(6L);
        assertEquals(new ArrayList<ControlPath>(), controlPathList);
    }

    @Test
    public void retrieveForReleasePoint_Should_Return_ControlList_When_ControlPathsExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForReleasePoint(7L);
        assertNotEquals(null, controlPathList);
    }

    @Test
    public void retrieveForReleasePoint_Should_Return_Empty_When_ControlPathsDoNotExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForReleasePoint(8L);
        assertEquals(new ArrayList<ControlPath>(), controlPathList);
    }

}
