package gov.epa.cef.web.service.mapper;

import java.util.List; 

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPathPostOrderDto;


@Mapper(componentModel = "spring", uses = {ReleasePointApptMapper.class})
public interface ControlPathMapper {

    List<ControlPathPostOrderDto> toPostOrderDtoList(List<ControlPath> source);
    
    @Mapping(source="facilitySite.id", target="facilitySiteId")
    ControlPathDto toDto(ControlPath source);
    
    @Mapping(source="facilitySiteId", target="facilitySite.id")
    ControlPath fromDto(ControlPathDto source);

    List<ControlPathDto> toDtoList(List<ControlPath> source);
    
    void updateFromDto(ControlPathDto source, @MappingTarget ControlPath target);
}
