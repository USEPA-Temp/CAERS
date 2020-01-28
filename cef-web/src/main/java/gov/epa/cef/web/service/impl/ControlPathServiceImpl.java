package gov.epa.cef.web.service.impl;

import java.util.ArrayList; 
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.repository.ControlPathRepository;
import gov.epa.cef.web.service.ControlPathService;
import gov.epa.cef.web.service.dto.ControlAssignmentDto;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.mapper.ControlAssignmentMapper;
import gov.epa.cef.web.service.mapper.ControlPathMapper;

@Service
public class ControlPathServiceImpl implements ControlPathService {

    @Autowired
    private ControlPathRepository repo;
    
    @Autowired
    private ControlAssignmentRepository assignmentRepo;

    @Autowired
    private ControlPathMapper mapper;
    
    @Autowired
    private ControlAssignmentMapper assignmentMapper;
    
    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;
    
    @Override
    public ControlPathDto retrieveById(Long id) {
        ControlPath result = getPathById(id);
        return mapper.toDto(result);
    }

    @Override
    public List<ControlPathDto> retrieveForEmissionsProcess(Long processId) {
        List<ControlPath> result = repo.findByEmissionsProcessId(processId);
        result.addAll(getChildren(result));
        return mapper.toDtoList(result);
    }
    
    @Override
    public List<ControlPathDto> retrieveForFacilitySite(Long facilitySiteId) {
    	List<ControlPath> result = repo.findByFacilitySiteIdOrderByDescription(facilitySiteId);
        return mapper.toDtoList(result);    }
    
	@Override
    public List<ControlPathDto> retrieveForEmissionsUnit(Long unitId) {
        List<ControlPath> result = repo.findByEmissionsUnitId(unitId);
        result.addAll(getChildren(result));
        return mapper.toDtoList(result);
    }
	
	@Override
    public List<ControlAssignmentDto> retrieveForControlPath(Long controlPathId) {
        List<ControlAssignment> result = assignmentRepo.findByControlPathId(controlPathId);
        return assignmentMapper.toDtoList(result);
    }

    @Override
    public List<ControlPathDto> retrieveForReleasePoint(Long pointId) {
        List<ControlPath> result = repo.findByReleasePointId(pointId);
        result.addAll(getChildren(result));
        return mapper.toDtoList(result);
    }
    
    /**
     * Create a new Control Path from a DTO object
     */
    public ControlPathDto create(ControlPathDto dto) {
    	ControlPath controlPath = mapper.fromDto(dto);
    	
    	ControlPathDto result = mapper.toDto(repo.save(controlPath));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ControlPathRepository.class);
    	return result;
    }
    
	/***
	 * Get child paths associated with the ControlPaths in the paths list
     * 
     * This will help flatten out the ControlPath parent-child relationships so that all ControlPaths will be included in the same list
	 * @param paths
	 */
	private List<ControlPath> getChildren(List<ControlPath> paths) {
		List<ControlPath> childPaths = new ArrayList<ControlPath>();
		
		if (paths != null) {
	        for (ControlPath path : paths) {
	        	childPaths = getChildPaths(path);
	        }
        }
		
		return childPaths;
	}
	
    /**
     * Update an existing Control Path from a DTO
     */
    public ControlPathDto update(ControlPathDto dto) {
    	
    	ControlPath controlPath = repo.findById(dto.getId()).orElse(null);
    	mapper.updateFromDto(dto, controlPath);
    	
    	ControlPathDto result = mapper.toDto(repo.save(controlPath));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ControlPathRepository.class);
    	return result;
    }
	

    /***
     * Iterate the path's assignments looking for child paths
     * @param path
     */
    private List<ControlPath> getChildPaths(ControlPath path) {
    	List<ControlPath> childPaths = new ArrayList<ControlPath>();
    	
    	if (path.getAssignments() != null) {
			for (ControlAssignment assignment : path.getAssignments()) {
				if (assignment.getControlPathChild() != null) {
					ControlPath childPath = getPathById(assignment.getControlPathChild().getId());
					if (childPath != null) {
						childPaths.add(childPath);
						childPaths.addAll(getChildPaths(childPath));
					}
				}
			}
    	}
    	
    	return childPaths;
	}
    
    
    /***
     * Retrieve a ControlPath from the repo based on it's ID
     * @param id
     * @return
     */
    private ControlPath getPathById(Long id) {
    	return repo
    			.findById(id)
    			.orElse(null);
    }
    
    /**
     * Delete a Control Path for a given id
     * @Param controlId
     */
    public void delete(Long controlPathId) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(controlPathId), ControlPathRepository.class);
    	repo.deleteById(controlPathId);
    }
    
    /**
     * Create a new Control Path Assignment from a DTO object
     */
    public ControlAssignmentDto createAssignment(ControlAssignmentDto dto){
    	ControlAssignment controlAssignment = assignmentMapper.fromDto(dto);

    	ControlAssignmentDto result = assignmentMapper.toDto(assignmentRepo.save(controlAssignment));

    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ControlAssignmentRepository.class);
    	return result;
    }
    
    /**
     * Delete a Control Path Assignment for a given id
     * @Param controlId
     */
    public void deleteAssignment(Long controlPathAssignmentId) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(controlPathAssignmentId), ControlAssignmentRepository.class);
    	assignmentRepo.deleteById(controlPathAssignmentId);
    }
    
    /**
     * Update an existing Control Path Assignment from a DTO
     */
    public ControlAssignmentDto updateAssignment(ControlAssignmentDto dto) {
    	
    	ControlAssignment controlPathAssignment = assignmentRepo.findById(dto.getId()).orElse(null);
    	assignmentMapper.updateFromDto(dto, controlPathAssignment);
    	
    	ControlAssignmentDto result = assignmentMapper.toDto(assignmentRepo.save(controlPathAssignment));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ControlAssignmentRepository.class);
    	return result;
    }

}
