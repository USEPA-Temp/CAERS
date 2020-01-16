package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.EmissionFormulaVariableDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface EmissionMapper {

    EmissionDto toDto(Emission source);

    @Mapping(source="reportingPeriodId", target="reportingPeriod.id")
    Emission fromDto(EmissionDto source);

    @Mapping(target = "emissionsCalcMethodCode", qualifiedByName  = "CalculationMethodCode")
    @Mapping(target = "emissionsUomCode", qualifiedByName  = "UnitMeasureCode")
    @Mapping(target = "emissionsNumeratorUom", qualifiedByName  = "UnitMeasureCode")
    @Mapping(target = "emissionsDenominatorUom", qualifiedByName  = "UnitMeasureCode")
    @Mapping(target = "variables", ignore = true)
    void updateFromDto(EmissionDto source, @MappingTarget Emission target);
    
    @Mapping(source="emission.id", target="emissionId")
    EmissionFormulaVariableDto formulaVariableToDto(EmissionFormulaVariable source);

    EmissionFormulaVariable formulaVariableFromDto(EmissionFormulaVariableDto source);

    List<EmissionFormulaVariable> formulaVariableFromDtoList(List<EmissionFormulaVariableDto> source);

    @Mapping(target = "emission", ignore = true)
    @Mapping(target = "variableCode", ignore = true)
    EmissionFormulaVariable updateFormulaVariableFromDto(EmissionFormulaVariableDto source, @MappingTarget EmissionFormulaVariable target);
}
