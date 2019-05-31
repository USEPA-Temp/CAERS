package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.dto.EmissionsReportDto;


@Mapper
public interface EmissionsReportMapper {
    EmissionsReportMapper INSTANCE = Mappers.getMapper(EmissionsReportMapper.class);

    
    EmissionsReportDto toDto(EmissionsReport emissionsReport);
}