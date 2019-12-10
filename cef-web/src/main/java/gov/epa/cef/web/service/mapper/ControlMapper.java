package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;

@Mapper(componentModel = "spring", uses = {})
public interface ControlMapper {

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    ControlPostOrderDto toDto(Control source);

    List<ControlPostOrderDto> toDtoList(List<Control> source);

    EmissionsReportItemDto toReportItemDto(Control source);
    
    @Mapping(source="facilitySiteId", target="facilitySite.id")
    Control fromDto(ControlDto source);
}
