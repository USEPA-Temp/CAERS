package gov.epa.cef.web.service.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.service.dto.FacilityNAICSDto;

@Mapper(componentModel = "spring", uses = {})
public interface FacilityNAICSMapper {
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "facilitySite.id", target = "facilitySiteId")
    @Mapping(source = "naicsCode.code", target = "code")
    @Mapping(source = "naicsCode.description", target = "description")
    @Mapping(source = "primaryFlag", target = "primaryFlag")
    FacilityNAICSDto facilityNAICSXrefToFacilityNAICSDto(FacilityNAICSXref facilityNAICS);
    
    Set<FacilityNAICSDto> facilityNAICSXrefsToFacilityNAICSDtos(Set<FacilityNAICSXref> facilityNAICSs);

}
