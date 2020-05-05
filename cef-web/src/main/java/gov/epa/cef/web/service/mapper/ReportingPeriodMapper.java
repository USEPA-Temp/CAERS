package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.ReportingPeriodBulkEntryDto;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;


@Mapper(componentModel = "spring", uses = {})
public interface ReportingPeriodMapper {

    @Mapping(source="emissionsProcess.id", target="emissionsProcessId")
    ReportingPeriodDto toDto(ReportingPeriod source);

    List<ReportingPeriodDto> toDtoList(List<ReportingPeriod> source);

    @Mapping(source="emissionsProcessId", target="emissionsProcess.id")
    ReportingPeriod fromDto(ReportingPeriodDto source);

    @Mapping(target = "reportingPeriodTypeCode", ignore = true)
    @Mapping(target = "emissionsOperatingTypeCode", ignore = true)
    @Mapping(target = "calculationParameterTypeCode", ignore = true)
    @Mapping(target = "calculationParameterUom", ignore = true)
    @Mapping(target = "calculationMaterialCode", ignore = true)
    @Mapping(target = "emissions", ignore = true)
    @Mapping(target = "operatingDetails", ignore = true)
    void updateFromDto(ReportingPeriodDto source, @MappingTarget ReportingPeriod target);

    @Mapping(source="emissionsProcess.emissionsUnit.id", target="emissionsUnitId")
    @Mapping(source="emissionsProcess.emissionsUnit.unitIdentifier", target="unitIdentifier")
    @Mapping(source="emissionsProcess.emissionsUnit.description", target="unitDescription")
    @Mapping(source="emissionsProcess.id", target="emissionsProcessId")
    @Mapping(source="emissionsProcess.emissionsProcessIdentifier", target="emissionsProcessIdentifier")
    @Mapping(source="emissionsProcess.description", target="emissionsProcessDescription")
    @Mapping(source="id", target="reportingPeriodId")
    ReportingPeriodBulkEntryDto toBulkEntryDto(ReportingPeriod source);

    List<ReportingPeriodBulkEntryDto> toBulkEntryDtoList(List<ReportingPeriod> source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="reportingPeriodId", target="id")
    @Mapping(source="calculationParameterValue", target="calculationParameterValue")
    ReportingPeriodDto toUpdateDtoFromBulk(ReportingPeriodBulkEntryDto source);

    List<ReportingPeriodDto> toUpdateDtoFromBulkList(List<ReportingPeriodBulkEntryDto> source);
}
