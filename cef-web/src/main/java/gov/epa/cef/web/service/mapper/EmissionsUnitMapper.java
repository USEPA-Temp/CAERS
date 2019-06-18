package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;

@Mapper(componentModel = "spring", uses = {EmissionsProcessMapper.class})   
public interface EmissionsUnitMapper {

    @Mapping(source = "unitTypeCode.description", target = "unitTypeCodeDescription")
    @Mapping(source = "operatingStatusCode.description", target = "operatingStatusCodeDescription")
    @Mapping(source = "facilitySite.id", target = "facilitySiteId")
    @Mapping(source = "unitOfMeasureCode.code", target = "unitOfMeasureCode")
    @Mapping(source = "unitOfMeasureCode.description", target = "unitOfMeasureDescription")
    @Mapping(source = "emissionsProcesses", target = "emissionsProcesses")
    EmissionsUnitDto emissionsUnitToDto(EmissionsUnit emissionsUnit);
    
    List<EmissionsUnitDto> emissionsUnitsToEmissionUnitsDtos(List<EmissionsUnit> emissionUnits);
    
}