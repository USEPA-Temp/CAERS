package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;

@Mapper(componentModel = "spring", uses = {ReleasePointApptMapper.class})   
public interface EmissionsProcessMapper {
    
    @Mapping(source="emissionsUnit.id", target="emissionsUnitId")
    @Mapping(source="aircraftEngineTypeCode.code", target="aircraftEngineTypeCodeCode")
    @Mapping(source="operatingStatusCode.description", target="operatingStatusCodeDescription")
    EmissionsProcessDto  emissionsProcessToEmissionsProcessDto(EmissionsProcess emissionsProcess);
    
    List<EmissionsProcessDto> emissionsProcessesToEmissionsProcessDtos(List<EmissionsProcess> emissionsProcesses);

}
