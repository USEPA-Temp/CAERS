package gov.epa.cef.web.service.mapper;

import java.util.List; 

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.dto.FacilitySiteDto;

@Mapper(componentModel = "spring", uses = {FacilityNAICSMapper.class, LookupEntityMapper.class})
public interface FacilitySiteMapper {

    FacilitySiteDto toDto(FacilitySite facility);

    List<FacilitySiteDto> toDtoList(List<FacilitySite> facilitySites);
    
    @Mapping(target="tribalCode", qualifiedByName = "TribalCode")
    @Mapping(target ="operatingStatusCode", qualifiedByName  = "OperatingStatusCode")
    @Mapping(target = "facilityNAICS", ignore = true)
    @Mapping(target = "facilityCategoryCode", qualifiedByName = "FacilityCategoryCode")
    @Mapping(target = "facilitySourceTypeCode", qualifiedByName = "FacilitySourceTypeCode")
    void updateFromDto(FacilitySiteDto source, @MappingTarget FacilitySite target);
    
    FacilitySite fromDto(FacilitySiteDto facilitySite);
}