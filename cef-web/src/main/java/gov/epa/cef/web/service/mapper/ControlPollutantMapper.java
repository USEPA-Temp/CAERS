package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.service.dto.ControlPollutantDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface ControlPollutantMapper {

    @Mapping(source="control.id", target="controlId")
    ControlPollutantDto toDto(ControlPollutant source);

    @Mapping(source="controlId", target="control.id")
    ControlPollutant fromDto(ControlPollutantDto source);

    @Mapping(source = "controlId", target = "control.id")
    void updateFromDto(ControlPollutantDto source, @MappingTarget ControlPollutant target);
}
