package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.dto.ReleasePointDto;


@Mapper
public interface ReleasePointMapper {
    ReleasePointMapper INSTANCE = Mappers.getMapper(ReleasePointMapper.class);

    ReleasePointDto toDto(ReleasePoint releasePoint);
}