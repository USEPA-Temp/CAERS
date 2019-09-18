package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.service.dto.ReleasePointApptDto;
import gov.epa.cef.web.service.dto.ReleasePointApptUpDto;

@Mapper(componentModel = "spring", uses = {})   
public interface ReleasePointApptMapper {
    
    @Mapping(source = "releasePoint.id", target = "releasePointId")
    @Mapping(source = "releasePoint.releasePointIdentifier", target = "releasePointIdentifier")
    @Mapping(source = "releasePoint.description", target = "releasePointDescription")
    @Mapping(source = "releasePoint.typeCode", target = "releasePointTypeCode")
    ReleasePointApptDto releasePointApptToReleasePointApptDto(ReleasePointAppt releasePointAppt);
    
    List<ReleasePointApptDto> releasePointApptsToReleasePointApptDtos(List<ReleasePointAppt> releasePointAppts);
    
    ReleasePointApptUpDto toUpDto(ReleasePointAppt releasePointAppt);

}
