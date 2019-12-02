package gov.epa.cef.web.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;

public interface ControlService {

    /**
     * Retrieve Control by its id
     * @param id
     * @return
     */
    ControlPostOrderDto retrieveById(Long id);

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    List<ControlPostOrderDto> retrieveForFacilitySite(Long facilitySiteId, Sort sort);
    
    /***
     * Retrieve a DTO containing all of the related sub-facility components for the given control
     * @param controlId
     * @return
     */
    List<EmissionsReportItemDto> retrieveControlComponents(Long controlId);

}