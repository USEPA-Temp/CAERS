package gov.epa.cef.web.service.mapper;

import java.util.List; 
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.ControlPollutantDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface ControlPollutantMapper {

    @Mapping(source="control.id", target="controlId")
    ControlPollutantDto toDto(ControlPollutant source);

    @Mapping(source="controlId", target="control.id")
    ControlPollutant fromDto(ControlPollutantDto source);

    @Mapping(source = "controlId", target = "control.id")
    void updateFromDto(ControlPollutantDto source, @MappingTarget ControlPollutant target);
}
