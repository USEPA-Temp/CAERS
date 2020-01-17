package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EmissionsByFacilityAndCAS;
import gov.epa.cef.web.service.dto.EmissionsByFacilityAndCASDto;

@Mapper(componentModel = "spring", uses = {})
public interface EmissionsByFacilityAndCASMapper {
    
    @Mapping(source="pollutantCasId", target="casNumber")
    @Mapping(source="pollutantName", target="chemical")
    @Mapping(source="emissionsUomCode", target="uom")
    @Mapping(source="status", target="reportStatus")
    EmissionsByFacilityAndCASDto toDto(EmissionsByFacilityAndCAS emissionsByFacilityAndCAS);

}
