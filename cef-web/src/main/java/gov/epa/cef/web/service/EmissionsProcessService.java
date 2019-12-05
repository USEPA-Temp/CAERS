package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.EmissionsProcessDto;
import gov.epa.cef.web.service.dto.EmissionsProcessSaveDto;

import java.util.List;

import org.springframework.data.domain.Sort;

public interface EmissionsProcessService {

    /**
     * Create a new Emissions Process
     * @param dto
     * @return
     */
    EmissionsProcessDto create(EmissionsProcessSaveDto dto);

    /**
     * Update an Emissions Process
     * @param dto
     * @return
     */
    EmissionsProcessDto update(EmissionsProcessSaveDto dto);

    /**
     * Retrieve Emissions Process by its id
     * @param id
     * @return
     */
    EmissionsProcessDto retrieveById(Long id);

    /**
     * Retrieve Emissions Processes for a release point
     * @param pointId
     * @return
     */
    List<EmissionsProcessDto> retrieveForReleasePoint(Long pointId);

    /**
     * Retrieve Emissions Processes for an Emissions Unit
     * @param emissionsUnitId
     * @return
     */
    List<EmissionsProcessDto> retrieveForEmissionsUnit(Long emissionsUnitId);

    /**
     * Delete an Emissions Process for a given id
     * @param id
     */
    void delete(Long id);

}
