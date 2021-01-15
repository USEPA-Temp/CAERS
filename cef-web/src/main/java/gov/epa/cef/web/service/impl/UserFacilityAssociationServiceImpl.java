package gov.epa.cef.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.client.soap.RegisterServiceClient;
import gov.epa.cef.web.client.soap.StreamlinedRegistrationServiceClient;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTBaseConfig;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.UserFacilityAssociation;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.UserFacilityAssociationRepository;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;
import gov.epa.cef.web.service.dto.UserFacilityAssociationDto;
import gov.epa.cef.web.service.mapper.MasterFacilityRecordMapper;
import gov.epa.cef.web.service.mapper.UserFacilityAssociationMapper;
import gov.epa.cef.web.util.SLTConfigHelper;
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
    private MasterFacilityRecordMapper mfrMapper;

    @Autowired
    private UserFacilityAssociationMapper ufaMapper;

    @Autowired
    private CefConfig cefConfig;

    @Autowired
    private SLTConfigHelper sltConfigHelper;

    @Autowired
    private NotificationService notificationService;

    public UserFacilityAssociationDto findById(Long id) {

        return ufaRepo.findById(id)
                .map(this.ufaMapper::toDto)
                .orElse(null);
    }

    public void deleteById(Long id) {
        this.ufaRepo.deleteById(id);
    }

    public UserFacilityAssociationDto requestFacilityAssociation(MasterFacilityRecordDto facilityDto, ApplicationUser user) {

        MasterFacilityRecord facility = this.mfrMapper.fromDto(facilityDto);

        SLTBaseConfig sltConfig = sltConfigHelper.getCurrentSLTConfig(facility.getProgramSystemCode().getCode());

        // throw an exception if the SLT is not yet supported
        if (Strings.emptyToNull(sltConfig.getSltEmail()) == null) {
            throw new NotExistException("SltConfigProperty", facility.getProgramSystemCode().getCode());
        }

        UserFacilityAssociation result = this.createFacilityAssociation(facility, user.getUserRoleId(), false);

        //send an email notification to the SLT's predefined address that a user has requested access
        notificationService.sendUserAccessRequestNotification(sltConfig.getSltEmail(),
                cefConfig.getDefaultEmailAddress(),
                facility.getAgencyFacilityId(),
                user.getFirstName() + " " + user.getLastName());

        return this.ufaMapper.toDto(result);
    }

    public UserFacilityAssociation createFacilityAssociation(MasterFacilityRecord facility, Long userRoleId, Boolean approved) {

        UserFacilityAssociation association = new UserFacilityAssociation();
        association.setMasterFacilityRecord(facility);
        association.setUserRoleId(userRoleId);
        association.setApproved(approved);
        return this.ufaRepo.save(association);
    }

    public List<UserFacilityAssociationDto> findByUserRoleId(Long userRoleId) {

        List<UserFacilityAssociation> entities = this.ufaRepo.findByUserRoleId(userRoleId);

        return this.ufaMapper.toDtoList(entities);
    }

    public List<UserFacilityAssociationDto> findDetailsByMasterFacilityRecordId(Long masterFacilityRecordId) {

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
