package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPathPostOrderDto;


@Mapper(componentModel = "spring", uses = {ReleasePointApptMapper.class})
public interface ControlPathMapper {

    ControlPathPostOrderDto toPostOrderDto(ControlPath source);

    List<ControlPathPostOrderDto> toPostOrderDtoList(List<ControlPath> source);

    ControlPathDto toDto(ControlPath source);

    List<ControlPathDto> toDtoList(List<ControlPath> source);
}
