package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;


@Mapper(componentModel = "spring", uses = {})
public interface ReportingPeriodMapper {

    @Mapping(source="emissionsProcess.id", target="emissionsProcessId")
    ReportingPeriodDto toDto(ReportingPeriod source);

    List<ReportingPeriodDto> toDtoList(List<ReportingPeriod> source);

    @Mapping(target = "reportingPeriodTypeCode", ignore = true)
    @Mapping(target = "emissionsOperatingTypeCode", ignore = true)
    @Mapping(target = "calculationParameterTypeCode", ignore = true)
    @Mapping(target = "calculationParameterUom", ignore = true)
    @Mapping(target = "calculationMaterialCode", ignore = true)
    @Mapping(target = "emissions", ignore = true)
    @Mapping(target = "operatingDetails", ignore = true)
    void updateFromDto(ReportingPeriodDto source, @MappingTarget ReportingPeriod target);
}
