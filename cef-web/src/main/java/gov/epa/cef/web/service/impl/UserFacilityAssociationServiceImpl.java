package gov.epa.cef.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.client.soap.RegisterServiceClient;
import gov.epa.cef.web.client.soap.StreamlinedRegistrationServiceClient;
import gov.epa.cef.web.domain.UserFacilityAssociation;
import gov.epa.cef.web.repository.UserFacilityAssociationRepository;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.service.dto.UserFacilityAssociationDto;
import gov.epa.cef.web.service.mapper.UserFacilityAssociationMapper;
import net.exchangenetwork.wsdl.register._1.RegistrationUser;
import net.exchangenetwork.wsdl.register.streamlined._1.RegistrationNewUserProfile;
import net.exchangenetwork.wsdl.register.streamlined._1.RegistrationUserSearchCriteria;

@Service
public class UserFacilityAssociationServiceImpl {

    @Autowired
    private UserFacilityAssociationRepository ufaRepo;

    @Autowired
    private RegisterServiceClient registerServiceClient;

    @Autowired
    private StreamlinedRegistrationServiceClient streamlinedRegClient;

    @Autowired
    private UserFacilityAssociationMapper ufaMapper;

    public UserFacilityAssociationDto findById(Long id) {

        return ufaRepo.findById(id)
                .map(this.ufaMapper::toDto)
                .orElse(null);
    }

    public void deleteById(Long id) {
        this.ufaRepo.deleteById(id);
    }

    public List<UserFacilityAssociationDto> findByMasterFacilityRecordId(Long masterFacilityRecordId) {

        List<UserFacilityAssociation> entities = this.ufaRepo.findByMasterFacilityRecordId(masterFacilityRecordId);

        return entities.stream().map(this::mapAssociation).collect(Collectors.toList());
    }

    private UserFacilityAssociationDto mapAssociation(UserFacilityAssociation association) {
        
        RegistrationUser user = this.registerServiceClient.retrieveUserByUserRoleId(association.getUserRoleId());
        RegistrationUserSearchCriteria criteria = new RegistrationUserSearchCriteria();
        criteria.setUserId(user.getUserId());
        criteria.setDataflow("CAER");
        RegistrationNewUserProfile profile = this.streamlinedRegClient.retrieveUsersByCriteria(criteria)
                .stream()
                .filter(r ->association.getUserRoleId().equals(r.getRole().getUserRoleId()))
                .findFirst()
                .orElse(null);
        profile.getRole().getType().setDescription(AppRole.RoleType.fromId(profile.getRole().getType().getCode()).roleName());
        return this.ufaMapper.toDto(association, profile);
    }
}
