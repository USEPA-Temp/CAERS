package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.service.dto.ControlDto;

@Mapper(componentModel = "spring", uses = {})
public interface ControlMapper {

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    ControlDto toDto(Control source);

    List<ControlDto> toDtoList(List<Control> source);
}
