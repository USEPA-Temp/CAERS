package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.service.dto.ControlAssignmentDto;
import gov.epa.cef.web.service.dto.postOrder.ControlAssignmentPostOrderDto;


@Mapper(componentModel = "spring", uses = {})
public interface ControlAssignmentMapper {

    ControlAssignmentPostOrderDto toPostOrderDto(ControlAssignment source);

    List<ControlAssignmentPostOrderDto> toPostOrderDtoList(List<ControlAssignment> source);

    ControlAssignmentDto toDto(ControlAssignment source);

    List<ControlAssignmentDto> toDtoList(List<ControlAssignment> source);
}
