package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.EmissionFactorDto;

public interface EmissionFactorService {

    /**
     * Search for Emission Factors matching the provided criteria
     * @param dto
     * @return
     */
    List<EmissionFactorDto> retrieveByExample(EmissionFactorDto dto);

}