package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.service.dto.ControlPathDto;


@Mapper(componentModel = "spring", uses = {ReleasePointApptMapper.class})
public interface ControlPathMapper {

    ControlPathDto toDto(ControlPath source);

    List<ControlPathDto> toDtoList(List<ControlPath> source);
}
