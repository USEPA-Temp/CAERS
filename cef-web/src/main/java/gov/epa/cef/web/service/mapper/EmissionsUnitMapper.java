package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;

@Mapper(componentModel = "spring", uses = {})
public interface EmissionsUnitMapper {

    @Mapping(source = "unitTypeCode.description", target = "unitTypeCodeDescription")
    @Mapping(source = "operatingStatusCode.description", target = "operatingStatusCodeDescription")
    @Mapping(source = "facility.id", target = "facilityId")
    @Mapping(source = "unitOfMeasureCode.code", target = "unitOfMeasureCode")
    @Mapping(source = "unitOfMeasureCode.description", target = "unitOfMeasureDescription")
    EmissionsUnitDto emissionsUnitToDto(EmissionsUnit emissionsUnit);
}