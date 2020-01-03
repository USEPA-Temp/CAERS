package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;
import gov.epa.cef.web.service.dto.EmissionsProcessSaveDto;
import gov.epa.cef.web.service.dto.postOrder.EmissionsProcessPostOrderDto;

@Mapper(componentModel = "spring", uses = {ReleasePointApptMapper.class, LookupEntityMapper.class})
public interface EmissionsProcessMapper {
    
    @Mapping(source="emissionsUnit.id", target="emissionsUnitId")
    EmissionsProcessDto emissionsProcessToEmissionsProcessDto(EmissionsProcess emissionsProcess);
    
    List<EmissionsProcessDto> emissionsProcessesToEmissionsProcessDtos(List<EmissionsProcess> emissionsProcesses);
    
    @Mapping(source="emissionsUnitId", target="emissionsUnit.id")
    @Mapping(target = "operatingStatusCode", qualifiedByName  = "OperatingStatusCode")
    EmissionsProcess fromSaveDto(EmissionsProcessSaveDto source);
    
    @Mapping(target = "operatingStatusCode", qualifiedByName  = "OperatingStatusCode")
    @Mapping(target = "aircraftEngineTypeCode", qualifiedByName = "AircraftEngineTypeCode")
    @Mapping(target = "releasePointAppts", ignore = true)
    @Mapping(target = "reportingPeriods", ignore = true)
    void updateFromSaveDto(EmissionsProcessSaveDto source, @MappingTarget EmissionsProcess target);
    
    EmissionsProcessPostOrderDto toUpDto(EmissionsProcess emissionsProcess);

}
