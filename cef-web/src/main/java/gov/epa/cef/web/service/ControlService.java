package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.ControlPollutantDto;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;

public interface ControlService {
	
	/**
     * Create a new Control
     * @param dto
     * @return
     */
		ControlDto create(ControlDto dto);

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
    List<ControlPostOrderDto> retrieveForFacilitySite(Long facilitySiteId);
    
    /***
     * Retrieve a DTO containing all of the related sub-facility components for the given control
     * @param controlId
     * @return
     */
    List<EmissionsReportItemDto> retrieveControlComponents(Long controlId);
    
    /**
     * Update an existing Control by id
     * @param dto
     * @return
     */
    ControlDto update(ControlDto dto);
    
    /**
     * Delete a Control for a given id
     * @param controlId
     */
    void delete(Long controlId);
    
	/**
     * Create a new Control Pollutant
     * @param dto
     * @return
     */
	ControlPollutantDto createPollutant(ControlPollutantDto dto);
	
    /**
     * Update an existing Control Pollutant by id
     * @param dto
     * @return
     */
    ControlPollutantDto updateControlPollutant(ControlPollutantDto dto);
}