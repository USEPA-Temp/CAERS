package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.dto.EmissionsReportDto;


@Mapper(componentModel = "spring", uses = {})
public interface EmissionsReportMapper {

    EmissionsReportDto toDto(EmissionsReport emissionsReport);
    
    List<EmissionsReportDto> toDtoList(List<EmissionsReport> emissionsReportList);
    
    void updateFromDto(EmissionsReportDto source, @MappingTarget EmissionsReport target);

}