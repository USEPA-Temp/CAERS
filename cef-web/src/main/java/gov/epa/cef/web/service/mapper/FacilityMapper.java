package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import gov.epa.cef.web.domain.Facility;
import gov.epa.cef.web.service.dto.FacilitySiteDto;

@Mapper
public interface FacilityMapper {
    FacilityMapper INSTANCE = Mappers.getMapper(FacilityMapper.class);

    FacilitySiteDto toDto(Facility facility);
}