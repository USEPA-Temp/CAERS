package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.Facility;
import gov.epa.cef.web.service.dto.FacilitySiteDto;

@Mapper(componentModel = "spring", uses = {})
public interface FacilityMapper {

    FacilitySiteDto toDto(Facility facility);
}