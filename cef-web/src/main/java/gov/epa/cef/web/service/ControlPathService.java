package gov.epa.cef.web.service;

import java.util.List; 

import org.springframework.data.domain.Sort;

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
     * Update an existing Control Path by id
     * @param dto
     * @return
     */
    ControlPathDto update(ControlPathDto dto);

    /**
     * Delete a Control Path for a given id
     * @param controlId
     */
    void delete(Long controlPathId);
}