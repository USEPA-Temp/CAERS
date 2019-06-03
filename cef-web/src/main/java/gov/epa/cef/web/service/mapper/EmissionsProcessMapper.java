package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;
import gov.epa.cef.web.service.dto.ReleasePointApptDto;

@Mapper(componentModel = "spring", uses = {})   
public interface EmissionsProcessMapper {
    
    ReleasePointApptMapper releasePointApptMapper= Mappers.getMapper( ReleasePointApptMapper.class );
    
    @Mapping(source="emissionsUnit.id", target="emissionsUnitId")
    @Mapping(source="operatingStatusCode.description", target="operatingStatusCodeDescription")
    @Mapping(source="releasePointAppts", target="releasePoints")
    EmissionsProcessDto  emissionsProcessToEmissionsProcessDto(EmissionsProcess emissionsProcess);
    
    List<EmissionsProcessDto> emissionsProcessesToEmissionsProcessDtos(List<EmissionsProcess> emissionsProcesses);

    default ReleasePointApptDto releasePointApptToReleasePointApptDto(ReleasePointAppt releasePointAppt) {
        if (releasePointAppt == null) {
            return null;
        }
        return releasePointApptMapper.releasePointApptToReleasePointApptDto(releasePointAppt);
    }
}
