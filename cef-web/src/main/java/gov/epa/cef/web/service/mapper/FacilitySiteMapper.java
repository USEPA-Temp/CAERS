package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.dto.FacilitySiteDto;

@Mapper(componentModel = "spring", uses = {})
public interface FacilitySiteMapper {

    FacilitySiteDto toDto(FacilitySite facility);

    List<FacilitySiteDto> toDtoList(List<FacilitySite> facilitySites);
}