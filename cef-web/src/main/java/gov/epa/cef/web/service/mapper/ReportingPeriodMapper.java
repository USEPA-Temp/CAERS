package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;


@Mapper(componentModel = "spring", uses = {})
public interface ReportingPeriodMapper {

    @Mapping(source="emissionsProcess.id", target="emissionsProcessId")
    ReportingPeriodDto toDto(ReportingPeriod source);
}
