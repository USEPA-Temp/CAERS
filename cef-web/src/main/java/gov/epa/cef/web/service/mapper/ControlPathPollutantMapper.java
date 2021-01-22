package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ControlPathPollutant;
import gov.epa.cef.web.service.dto.ControlPathPollutantDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface ControlPathPollutantMapper {
	
	@Mapping(source="controlPath.id", target="controlPathId")
	ControlPathPollutantDto toDto(ControlPathPollutant source);

    @Mapping(source="controlPathId", target="controlPath.id")
    ControlPathPollutant fromDto(ControlPathPollutantDto source);

    @Mapping(source = "controlPathId", target = "controlPath.id")
    void updateFromDto(ControlPathPollutantDto source, @MappingTarget ControlPathPollutant target);

}
