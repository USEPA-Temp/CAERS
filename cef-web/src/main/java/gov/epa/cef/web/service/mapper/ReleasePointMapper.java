package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.dto.postOrder.ReleasePointPostOrderDto;


@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface ReleasePointMapper {

	@Mapping(source="facilitySite.id", target="facilitySiteId")
    ReleasePointDto toDto(ReleasePoint releasePoint);
    
    List<ReleasePointDto> toDtoList(List<ReleasePoint> releasePointList);
    
    ReleasePointPostOrderDto toUpDto(ReleasePoint releasePoint);
    
    @Mapping(source="facilitySiteId", target="facilitySite.id")
    ReleasePoint fromDto(ReleasePointDto source);
    
    @Mapping(target="typeCode", qualifiedByName="ReleasePointTypeCode")
    @Mapping(target="programSystemCode", qualifiedByName="ProgramSystemCode")
    @Mapping(target="operatingStatusCode", qualifiedByName="OperatingStatusCode")
    @Mapping(target="stackHeightUomCode", qualifiedByName="UnitMeasureCode")
    @Mapping(target="stackDiameterUomCode", qualifiedByName="UnitMeasureCode")
    @Mapping(target="exitGasVelocityUomCode", qualifiedByName="UnitMeasureCode")
    @Mapping(target="exitGasFlowUomCode", qualifiedByName="UnitMeasureCode")
    void updateFromDto(ReleasePointDto source, @MappingTarget ReleasePoint target);
}