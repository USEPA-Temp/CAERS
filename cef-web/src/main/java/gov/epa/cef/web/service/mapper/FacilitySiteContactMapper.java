package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface FacilitySiteContactMapper {

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    FacilitySiteContactDto toDto(FacilitySiteContact contact);
    
    List<FacilitySiteContactDto> toDtoList(List<FacilitySiteContact> facilitySiteContacts);
    
    @Mapping(source="facilitySiteId", target="facilitySite.id")
    FacilitySiteContact fromDto(FacilitySiteContactDto source);
    
    @Mapping(target="type", qualifiedByName = "ContactTypeCode")
    @Mapping(target="stateCode", qualifiedByName = "FipsStateCode")
    @Mapping(target="mailingStateCode", qualifiedByName = "FipsStateCode")
    void updateFromDto(FacilitySiteContactDto source, @MappingTarget FacilitySiteContact target);
}