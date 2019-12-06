package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.dto.postOrder.ReleasePointPostOrderDto;


@Mapper(componentModel = "spring", uses = {})
public interface ReleasePointMapper {

	@Mapping(source="facilitySite.id", target="facilitySiteId")
    ReleasePointDto toDto(ReleasePoint releasePoint);
    
    List<ReleasePointDto> toDtoList(List<ReleasePoint> releasePointList);
    
    ReleasePointPostOrderDto toUpDto(ReleasePoint releasePoint);
    
    @Mapping(source="facilitySiteId", target="facilitySite.id")
    ReleasePoint fromDto(ReleasePointDto source);
}