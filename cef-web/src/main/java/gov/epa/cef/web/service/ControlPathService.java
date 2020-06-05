package gov.epa.cef.web.service;

import java.util.List; 

import gov.epa.cef.web.service.dto.ControlAssignmentDto;
import gov.epa.cef.web.service.dto.ControlPathDto;

public interface ControlPathService {

	/**
     * Create a new Control Path
     * @param dto
     * @return
     */
	ControlPathDto create(ControlPathDto dto);

    /**
     * Retrieve Control Path by its id
     * @param id
     * @return
     */
    ControlPathDto retrieveById(Long id);

    /**
     * Retrieve Control Paths for a facility site
     * @param processId
     * @return
     */
    List<ControlPathDto> retrieveForFacilitySite(Long facilitySiteId);
    
    /**
     * Retrieve Control Paths for an emissions process
     * @param processId
     * @return
     */
    List<ControlPathDto> retrieveForEmissionsProcess(Long processId);

    /**
     * Retrieve Control Paths for an emissions unit
     * @param unitId
     * @return
     */
    List<ControlPathDto> retrieveForEmissionsUnit(Long unitId);

    /**
     * Retrieve Control Paths for a release point
     * @param pointId
     * @return
     */
    List<ControlPathDto> retrieveForReleasePoint(Long pointId);
    
    /**
     * Retrieve Control Paths by control device id
     * @param controlPathId
     * @return
     */
	List<ControlPathDto> retrieveForControlDevice(Long controlDeviceId);

    /**
     * Update an existing Control Path by id
     * @param dto
     * @return
     */
    ControlPathDto update(ControlPathDto dto);
    
    /**
     * Retrieve parent Control Paths for a child Control Path
     * @param id
     * @return
     */
	List<ControlAssignmentDto> retrieveParentPathById(Long id);

    /**
     * Delete a Control Path for a given id
     * @param controlId
     */
    void delete(Long controlPathId);
    
	/**
     * Create a new Control Path Assignment
     * @param dto
	 * @return 
     * @return
     */
    ControlAssignmentDto createAssignment(ControlAssignmentDto dto);
    
    /**
     * Retrieve Control Path Assignments by control path id
     * @param controlPathId
     * @return
     */
	List<ControlAssignmentDto> retrieveForControlPath(Long controlPathId);
	
    /**
     * Delete a Control Path Assignment for a given id
     * @param controlId
     */
    void deleteAssignment(Long controlPathAssignmentId);
    
    /**
     * Update an existing Control Path Assignment by id
     * @param dto
     * @return
     */
    ControlAssignmentDto updateAssignment(ControlAssignmentDto dto);
}