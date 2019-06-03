package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.service.dto.ReleasePointApptDto;

@Mapper(componentModel = "spring", uses = {})   
public interface ReleasePointApptMapper {
    
    @Mapping(source = "releasePoint.description", target = "releasePointDescription")
    @Mapping(source = "releasePoint.id", target = "releasePointId")
    ReleasePointApptDto releasePointApptToReleasePointApptDto(ReleasePointAppt releasePointAppt);
    
    List<ReleasePointApptDto> releasePointApptsToReleasePointApptDtos(List<ReleasePointAppt> releasePointAppts);

}
