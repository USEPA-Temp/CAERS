package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import gov.epa.cef.web.service.dto.postOrder.EmissionsUnitPostOrderDto;

@Mapper(componentModel = "spring", uses = {EmissionsProcessMapper.class, LookupEntityMapper.class})   
public interface EmissionsUnitMapper {

    @Mapping(source = "facilitySite.id", target = "facilitySiteId")
    @Mapping(source = "emissionsProcesses", target = "emissionsProcesses")
    EmissionsUnitDto emissionsUnitToDto(EmissionsUnit emissionsUnit);
    
    List<EmissionsUnitDto> emissionsUnitsToEmissionUnitsDtos(List<EmissionsUnit> emissionUnits);
    
    EmissionsUnitPostOrderDto toUpDto(EmissionsUnit emissionsUnit);
    
    @Mapping(source = "facilitySiteId", target = "facilitySite.id")
    @Mapping(source = "emissionsProcesses", target = "emissionsProcesses")
    EmissionsUnit emissionsUnitFromDto(EmissionsUnitDto emissionsUnit);
    
    @Mapping(target = "operatingStatusCode", qualifiedByName  = "OperatingStatusCode")
    @Mapping(target = "unitOfMeasureCode", qualifiedByName  = "UnitMeasureCode")
    @Mapping(target = "unitTypeCode", qualifiedByName  = "UnitTypeCode")
    void updateFromDto(EmissionsUnitDto source, @MappingTarget EmissionsUnit target);

}