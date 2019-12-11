package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface ControlMapper {

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    ControlPostOrderDto toDto(Control source);

    List<ControlPostOrderDto> toDtoList(List<Control> source);

    EmissionsReportItemDto toReportItemDto(Control source);
    
    @Mapping(source="facilitySiteId", target="facilitySite.id")
    Control fromDto(ControlDto source);
    
    @Mapping(target="operatingStatusCode", qualifiedByName="OperatingStatusCode")
    @Mapping(target="controlMeasureCode", qualifiedByName="ControlMeasureCode")
    void updateFromDto(ControlDto source, @MappingTarget Control target);
}
