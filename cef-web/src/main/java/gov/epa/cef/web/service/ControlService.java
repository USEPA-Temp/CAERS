package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.ControlDto;

public interface ControlService {

    ControlDto retrieveById(Long id);

    List<ControlDto> retrieveForFacilitySite(Long facilitySiteId);

}