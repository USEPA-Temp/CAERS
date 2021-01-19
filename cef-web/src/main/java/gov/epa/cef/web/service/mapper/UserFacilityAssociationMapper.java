package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.UserFacilityAssociation;
import gov.epa.cef.web.service.dto.UserFacilityAssociationDto;
import net.exchangenetwork.wsdl.register.streamlined._1.RegistrationNewUserProfile;

@Mapper(componentModel = "spring", uses = {})
public interface UserFacilityAssociationMapper {

    @Mapping(source="masterFacilityRecord.id", target="masterFacilityRecordId")
    UserFacilityAssociationDto toDto(UserFacilityAssociation source);

    @Mapping(source="source.masterFacilityRecord.id", target="masterFacilityRecordId")
    @Mapping(source="regUser.user.userId", target="cdxUserId")
    @Mapping(source="regUser.user.firstName", target="firstName")
    @Mapping(source="regUser.user.lastName", target="lastName")
    @Mapping(source="regUser.organization.email", target="email")
    @Mapping(source="regUser.role.type.code", target="roleId")
    @Mapping(source="regUser.role.type.description", target="roleDescription")
    UserFacilityAssociationDto toDto(UserFacilityAssociation source, RegistrationNewUserProfile regUser);

    List<UserFacilityAssociationDto> toDtoList(List<UserFacilityAssociation> source);

}
