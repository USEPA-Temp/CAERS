package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class UserFacilityAssociationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private MasterFacilityRecordDto masterFacilityRecord;
    private Long userRoleId;
    private Boolean approved;
    private String cdxUserId;
    private String firstName;
    private String lastName;
    private String email;
    private Long roleId;
    private String roleDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MasterFacilityRecordDto getMasterFacilityRecord() {
        return masterFacilityRecord;
    }

    public void setMasterFacilityRecord(MasterFacilityRecordDto masterFacilityRecord) {
        this.masterFacilityRecord = masterFacilityRecord;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getCdxUserId() {
        return cdxUserId;
    }

    public void setCdxUserId(String cdxUserId) {
        this.cdxUserId = cdxUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

}
