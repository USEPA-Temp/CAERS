package gov.epa.cef.web.service;

import java.util.List;
import gov.epa.cef.web.service.dto.ControlPathDto;

public interface ControlPathService {

    /**
     * Retrieve Control Path by its id
     * @param id
     * @return
     */
    ControlPathDto retrieveById(Long id);

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

}