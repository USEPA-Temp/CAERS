package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.service.dto.UserDto;

/**
 * Class that contains transforms for {@link ApplicationUser}
 * @author tfesperm
 *
 */
@Mapper(componentModel = "spring", uses = {})   
public interface ApplicationUserMapper {

    @Mapping(source="userId", target="cdxUserId")
    @Mapping(source="idTypeText", target="role")
    @Mapping(source="clientId", target="programSystemCode")
    UserDto toUserDto(ApplicationUser applicationUser);
    
}
