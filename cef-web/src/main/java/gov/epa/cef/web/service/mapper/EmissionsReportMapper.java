package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.dto.EmissionsReportDto;


@Mapper(componentModel = "spring", uses = {})
public interface EmissionsReportMapper {

    @Mapping(source="masterFacilityRecord.id", target="masterFacilityRecordId")
    EmissionsReportDto toDto(EmissionsReport emissionsReport);
    
    List<EmissionsReportDto> toDtoList(List<EmissionsReport> emissionsReportList);
    
    void updateFromDto(EmissionsReportDto source, @MappingTarget EmissionsReport target);

}