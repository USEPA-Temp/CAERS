package gov.epa.cef.web.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPathPollutant;
import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.repository.ControlPathPollutantRepository;
import gov.epa.cef.web.repository.ControlPathRepository;
import gov.epa.cef.web.service.dto.ControlAssignmentDto;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.dto.ControlPathPollutantDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;
import gov.epa.cef.web.service.mapper.ControlAssignmentMapper;
import gov.epa.cef.web.service.mapper.ControlMapper;
import gov.epa.cef.web.service.mapper.ControlPathMapper;
import gov.epa.cef.web.service.mapper.ControlPathPollutantMapper;

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
    private ControlPathPollutantRepository pollutantRepo;
    
    @Mock
    private ControlAssignmentRepository assignmentRepo;
   
    @Mock
    private ControlPathMapper mapper;
    
    @Mock
    private ControlAssignmentMapper assignmentMapper;
    
    @Mock
    private ControlPathPollutantMapper pollutantMapper;
    
    @Mock
    private EmissionsReportStatusServiceImpl reportStatusService;
    
    @Mock
    private ControlServiceImpl controlService;
    
    @Mock
    private ControlPathServiceImpl controlPathService;
    
    @Mock
    private ControlMapper controlMapper;
    
    @InjectMocks
    private ControlPathServiceImpl serviceImpl;
    
    private ControlPathDto controlPathDto;
    private List<ControlPathDto> controlPathDtoList;
    private ControlPathPollutantDto controlPathPollutantSaveDto;
    private ControlPathDto controlPathSaveDto;
    private ControlAssignmentDto controlAssignmentSaveDto;

    @Before
    public void init(){
        ControlPath control = new ControlPath();
        ControlPath controlPathChild = new ControlPath();
        List<ControlAssignment> caList = new ArrayList<ControlAssignment>();
        List<ControlAssignment> emptyCaList = new ArrayList<ControlAssignment>();
        ControlAssignment contAssignment = new ControlAssignment();
        List<ControlPath> controlList = new ArrayList<ControlPath>();
        List<ControlPath> emptyControlList = new ArrayList<ControlPath>();
        controlPathChild.setId(19L);
        contAssignment.setControlPathChild(controlPathChild);
        caList.add(contAssignment);
        control.setAssignments(caList);
        controlList.add(control);
        
        when(repo.findById(1L)).thenReturn(Optional.of(control));
        when(repo.findById(19L)).thenReturn(Optional.of(controlPathChild));
        when(repo.findById(2L)).thenReturn(Optional.empty());
        when(repo.findByEmissionsProcessId(3L)).thenReturn(controlList);
        when(repo.findByEmissionsProcessId(4L)).thenReturn(emptyControlList);
        when(repo.findByEmissionsUnitId(5L)).thenReturn(controlList);
        when(repo.findByEmissionsUnitId(6L)).thenReturn(emptyControlList);
        when(repo.findByReleasePointId(7L)).thenReturn(controlList);
        when(repo.findByReleasePointId(8L)).thenReturn(emptyControlList);
        when(repo.findByFacilitySiteIdOrderByPathId(10L)).thenReturn(controlList);
        when(repo.findByFacilitySiteIdOrderByPathId(11L)).thenReturn(emptyControlList);
        when(repo.findByControlId(12L)).thenReturn(controlList);
        when(repo.findByControlId(13L)).thenReturn(emptyControlList);
        when(assignmentRepo.findByControlPathChildId(14L)).thenReturn(caList);
        when(assignmentRepo.findByControlPathChildId(15L)).thenReturn(emptyCaList);
        when(assignmentRepo.findByControlPathIdOrderBySequenceNumber(16L)).thenReturn(caList);
        when(assignmentRepo.findByControlPathIdOrderBySequenceNumber(17L)).thenReturn(emptyCaList);
        
        controlPathDto = new ControlPathDto();
        controlPathDtoList=new ArrayList<>();
        when(mapper.toDto(control)).thenReturn(controlPathDto);
        when(mapper.toDtoList(controlList)).thenReturn(controlPathDtoList);
        
        controlPathSaveDto = new ControlPathDto();
        controlPathSaveDto.setId(18L);
        when(mapper.fromDto(controlPathSaveDto)).thenReturn(control);
        when(repo.save(control)).thenReturn(control);
        when(mapper.toDto(control)).thenReturn(controlPathDto);
        when(repo.findById(18L)).thenReturn(Optional.of(control));
        
        controlPathPollutantSaveDto = new ControlPathPollutantDto();
        controlPathPollutantSaveDto.setId(1L);
        ControlPathPollutant cpp = new ControlPathPollutant();
        cpp.setControlPath(control);
        ControlPathPollutantDto controlPathPollutantDto = new ControlPathPollutantDto();
        controlPathPollutantDto.setId(1L);
        when(pollutantMapper.fromDto(controlPathPollutantSaveDto)).thenReturn(cpp);
        when(pollutantRepo.save(cpp)).thenReturn(cpp);
        when(pollutantMapper.toDto(cpp)).thenReturn(controlPathPollutantDto);
        when(pollutantRepo.findById(1L)).thenReturn(Optional.of(cpp));
        
        controlAssignmentSaveDto = new ControlAssignmentDto();
        ControlAssignmentDto controlAssignmentDto = new ControlAssignmentDto();
        contAssignment.setControlPath(control);
        when(assignmentMapper.fromDto(controlAssignmentSaveDto)).thenReturn(contAssignment);
        when(assignmentRepo.save(contAssignment)).thenReturn(contAssignment);
        when(assignmentMapper.toDto(contAssignment)).thenReturn(controlAssignmentDto);
        ControlPathDto cp = new ControlPathDto();
        ControlDto c = new ControlDto();
        ControlPostOrderDto cpo = new ControlPostOrderDto();
        Control controlDev = new Control();
        ControlPath cpc = new ControlPath ();
        controlAssignmentSaveDto.setControl(c);
        controlAssignmentSaveDto.setId(1L);
        controlAssignmentSaveDto.setControlPathChild(cp);
        controlAssignmentSaveDto.setControlPath(c);
        when(controlService.retrieveById(1L)).thenReturn(cpo);
        when(controlPathService.retrieveById(1L)).thenReturn(controlPathDto);
        when(assignmentRepo.findById(1L)).thenReturn(Optional.of(contAssignment));
        when(mapper.fromDto(controlPathDto)).thenReturn(control);
        when(controlMapper.fromDto(cpo)).thenReturn(controlDev);
        when(mapper.fromDto(cp)).thenReturn(cpc);
        when(assignmentMapper.toDto(contAssignment)).thenReturn(controlAssignmentDto);
        
        when(reportStatusService.resetEmissionsReportForEntity(ArgumentMatchers.anyList(), ArgumentMatchers.any())).thenReturn(null);
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
    public void retrieveForFacilitySite_Should_Return_ControlList_When_ControlPathsExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForFacilitySite(10L);
        assertNotEquals(null, controlPathList);
    }
    
    @Test
    public void retrieveForFacilitySite_Should_Return_Empty_When_ControlPathsDoNotExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForFacilitySite(11L);
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
    public void retrieveForControlDevice_Should_Return_ControlList_When_ControlPathsExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForControlDevice(12L);
        assertNotEquals(null, controlPathList);
    }

    @Test
    public void retrieveForControlDevice_Should_Return_Empty_When_ControlPathsDoNotExist() {
        Collection<ControlPathDto> controlPathList = serviceImpl.retrieveForControlDevice(13L);
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
    
    @Test
    public void retrieveParentPathById_Should_Return_Empty_When_ControlAssignDoNotExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveParentPathById(14L);
        assertEquals(new ArrayList<ControlPath>(), controlAssignmentList);
    }
    
    @Test
    public void retrieveParentPathById_Should_Return_ControlList_When_ControlAssignExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveParentPathById(15L);
        assertNotEquals(null, controlAssignmentList);
    }
    
    @Test
    public void retrieveForControlPath_Should_Return_Empty_When_ControlAssignDoNotExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForControlPath(16L);
        assertEquals(new ArrayList<ControlPath>(), controlAssignmentList);
    }
    
    @Test
    public void retrieveForControlPath_Should_Return_ControlList_When_ControlAssignExist() {
        Collection<ControlAssignmentDto> controlAssignmentList = serviceImpl.retrieveForControlPath(17L);
        assertNotEquals(null, controlAssignmentList);
    }
    
    @Test
    public void createControlPath() {
    	ControlPathDto cp = serviceImpl.create(controlPathSaveDto);
        assertNotEquals(null, cp);
    }
    
    @Test
    public void createPollutant() {
    	ControlPathPollutantDto cpp = serviceImpl.createPollutant(controlPathPollutantSaveDto);
        assertNotEquals(null, cpp);
    }
    
    @Test
    public void createAssignment() {
    	ControlAssignmentDto ca = serviceImpl.createAssignment(controlAssignmentSaveDto);
        assertNotEquals(null, ca);
    }
    
    @Test
    public void updateControlPathPollutant_Should_ReturnControlPathPollutantDtoObject_When_ControlPathPollutantExists() {
    	ControlPathPollutantDto controlPathPollutant = serviceImpl.updateControlPathPollutant(controlPathPollutantSaveDto);
    	assertNotEquals(null, controlPathPollutant);
    }
    
    @Test
    public void updateControlPath_Should_ReturnControlPathDtoObject_When_ControlPathExists() {
    	ControlPathDto controlPath = serviceImpl.update(controlPathSaveDto);
    	assertNotEquals(null, controlPath);
    }
    
    @Test
    public void updateAssignment_Should_ReturnControlAssignmentDtoObject_When_ControlAssignmentExists() {
    	ControlAssignmentDto controlAssignment = serviceImpl.updateAssignment(controlAssignmentSaveDto);
    	assertNotEquals(null, controlAssignment);
    }
    
}
