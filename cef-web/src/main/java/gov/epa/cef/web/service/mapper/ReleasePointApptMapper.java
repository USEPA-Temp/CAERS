package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.service.dto.ReleasePointApptDto;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.dto.postOrder.ReleasePointApptPostOrderDto;
import gov.epa.cef.web.service.dto.postOrder.ReleasePointPostOrderDto;

@Mapper(componentModel = "spring", uses = {})   
public interface ReleasePointApptMapper {

    List<ReleasePointApptDto> releasePointApptsToReleasePointApptDtos(List<ReleasePointAppt> releasePointAppts);
    
    ReleasePointApptPostOrderDto toUpDto(ReleasePointAppt releasePointAppt);

    @Mapping(source = "releasePointDescription", target = "releasePoint.description")
    @Mapping(source = "releasePointIdentifier", target = "releasePoint.releasePointIdentifier")
    @Mapping(source = "releasePointTypeCode", target = "releasePoint.typeCode")
    @Mapping(source = "releasePointId", target = "releasePoint.id")
    @Mapping(source = "emissionsProcessId", target = "emissionsProcess.id")
    ReleasePointAppt fromDto(ReleasePointApptDto source);
    
    @Mapping(source = "releasePoint.description", target = "releasePointDescription")
    @Mapping(source = "releasePoint.releasePointIdentifier", target = "releasePointIdentifier")
    @Mapping(source = "releasePoint.typeCode", target = "releasePointTypeCode")
    @Mapping(source = "releasePoint.id", target = "releasePointId")
    @Mapping(source = "emissionsProcess.id", target = "emissionsProcessId")
    ReleasePointApptDto toDto(ReleasePointAppt releasePointAppt);
    
    @Mapping(source = "releasePointId", target = "releasePoint.id")
    void updateFromDto(ReleasePointApptDto source, @MappingTarget ReleasePointAppt target);
}
