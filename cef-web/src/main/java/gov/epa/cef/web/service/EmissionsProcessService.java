package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.EmissionsProcessDto;

public interface EmissionsProcessService {

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

}