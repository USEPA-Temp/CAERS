package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.UserFeedback;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.dto.UserFeedbackDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface UserFeedbackMapper {

	
    UserFeedback fromDto(UserFeedbackDto source);
    
    UserFeedbackDto toDto(UserFeedback userFeedback);
    
    List<UserFeedbackDto> toDtoList(List<UserFeedback> UserFeedbackList);
    
    void updateFromDto(UserFeedbackDto source, @MappingTarget UserFeedback target);

}
