package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.ControlDto;

public interface ControlService {

    /**
     * Retrieve Control by its id
     * @param id
     * @return
     */
    ControlDto retrieveById(Long id);

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    List<ControlDto> retrieveForFacilitySite(Long facilitySiteId);

    /**
     * Retrieve Controls for an emissions process
     * @param processId
     * @return
     */
    List<ControlDto> retrieveForEmissionsProcess(Long processId);

    /**
     * Retrieve Controls for an emissions unit
     * @param unitId
     * @return
     */
    List<ControlDto> retrieveForEmissionsUnit(Long unitId);

    /**
     * Retrieve Controls for a release point
     * @param pointId
     * @return
     */
    List<ControlDto> retrieveForReleasePoint(Long pointId);

}