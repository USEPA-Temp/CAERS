package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.service.dto.FacilitySiteContactDto;

@Mapper(componentModel = "spring", uses = {})
public interface FacilitySiteContactMapper {

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    FacilitySiteContactDto toDto(FacilitySiteContact contact);
    
    List<FacilitySiteContactDto> toDtoList(List<FacilitySiteContact> facilitySiteContacts);
    
    @Mapping(source="facilitySiteId", target="facilitySite.id")
    FacilitySiteContact fromDto(FacilitySiteContactDto source);
}