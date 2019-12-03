package gov.epa.cef.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.repository.ControlPathRepository;
import gov.epa.cef.web.service.ControlPathService;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.mapper.ControlPathMapper;

@Service
public class ControlPathServiceImpl implements ControlPathService {

    @Autowired
    private ControlPathRepository repo;

    @Autowired
    private ControlPathMapper mapper;

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
    public List<ControlPathDto> retrieveForEmissionsUnit(Long unitId) {
        List<ControlPath> result = repo.findByEmissionsUnitId(unitId);
        result.addAll(getChildren(result));
        return mapper.toDtoList(result);
    }

    @Override
    public List<ControlPathDto> retrieveForReleasePoint(Long pointId) {
        List<ControlPath> result = repo.findByReleasePointId(pointId);
        result.addAll(getChildren(result));
        return mapper.toDtoList(result);
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

}
