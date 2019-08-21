package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.service.dto.EmissionDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface EmissionMapper {

    EmissionDto toDto(Emission source);

    @Mapping(source="reportingPeriodId", target="reportingPeriod.id")
    Emission fromDto(EmissionDto source);

    @Mapping(target = "emissionsCalcMethodCode", qualifiedByName  = "CalculationMethodCode")
    @Mapping(target = "emissionsUomCode", qualifiedByName  = "UnitMeasureCode")
    @Mapping(target = "emissionsNumeratorUom", qualifiedByName  = "UnitMeasureCode")
    @Mapping(target = "emissionsDenominatorUom", qualifiedByName  = "UnitMeasureCode")
    void updateFromDto(EmissionDto source, @MappingTarget Emission target);
}
