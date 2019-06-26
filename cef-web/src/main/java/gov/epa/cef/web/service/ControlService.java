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

}