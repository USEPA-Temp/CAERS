package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.EmissionFactor;
import gov.epa.cef.web.service.dto.EmissionFactorDto;

@Mapper(componentModel = "spring")
public interface EmissionFactorMapper {

    EmissionFactorDto toDto(EmissionFactor source);

    List<EmissionFactorDto> toDtoList(List<EmissionFactor> source);

    EmissionFactor fromDto(EmissionFactorDto source);

}
