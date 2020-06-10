package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.EmissionBulkEntryDto;
import gov.epa.cef.web.service.dto.EmissionBulkEntryHolderDto;
import gov.epa.cef.web.service.dto.EmissionDto;
import gov.epa.cef.web.service.dto.EmissionFormulaVariableDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface EmissionMapper {

    EmissionDto toDto(Emission source);

    List<EmissionDto> toDtoList(List<Emission> source);

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

    @Mapping(source="emissionsProcess.emissionsUnit.id", target="emissionsUnitId")
    @Mapping(source="emissionsProcess.emissionsUnit.unitIdentifier", target="unitIdentifier")
    @Mapping(source="emissionsProcess.emissionsUnit.description", target="unitDescription")
    @Mapping(source="emissionsProcess.id", target="emissionsProcessId")
    @Mapping(source="emissionsProcess.emissionsProcessIdentifier", target="emissionsProcessIdentifier")
    @Mapping(source="emissionsProcess.description", target="emissionsProcessDescription")
    @Mapping(source="id", target="reportingPeriodId")
    EmissionBulkEntryHolderDto periodToEmissionBulkEntryDto(ReportingPeriod source);

    List<EmissionBulkEntryHolderDto> periodToEmissionBulkEntryDtoList(List<ReportingPeriod> source);

    EmissionBulkEntryDto toBulkDto(Emission source);
}
