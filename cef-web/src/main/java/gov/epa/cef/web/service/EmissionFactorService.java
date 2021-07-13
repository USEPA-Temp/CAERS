package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.EmissionFactorDto;
import gov.epa.cef.web.service.dto.EmissionFormulaVariableCodeDto;

public interface EmissionFactorService {

    /**
     * Search for Emission Factors matching the provided criteria
     * @param dto
     * @return
     */
    List<EmissionFactorDto> retrieveByExample(EmissionFactorDto dto);

    /**
     * Generate variables for an emission factor formula
     * @param formula
     * @return
     */
    List<EmissionFormulaVariableCodeDto> parseFormulaVariables(String formula);

}