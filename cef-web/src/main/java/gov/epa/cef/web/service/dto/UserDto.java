package gov.epa.cef.web.service.dto;

import com.google.common.base.MoreObjects;
import gov.epa.cef.web.security.AppRole;

import java.io.Serializable;

public class UserDto implements Serializable {

    /**
     * default version
     */
    private static final long serialVersionUID = 1L;

    private String cdxUserId;

    private String email;

    private String firstName;

    private String lastName;

    private String programSystemCode;

    private AppRole.RoleType role = AppRole.RoleType.UNKNOWN;

    private Long userRoleId;

    public String getCdxUserId() {

        return cdxUserId;
    }

    public void setCdxUserId(String cdxUserId) {

        this.cdxUserId = cdxUserId;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
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

    public String getRole() {

        return role.roleName();
    }

    public void setRole(String role) {

        this.role = AppRole.RoleType.fromRoleName(role);
    }

    public void setRole(AppRole.RoleType role) {

        this.role = role;
    }

    public Long getUserRoleId() {

        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {

        this.userRoleId = userRoleId;
    }

    public String getProgramSystemCode() {

        return programSystemCode;
    }

    public void setProgramSystemCode(String programSystemCode) {

        this.programSystemCode = programSystemCode;
    }

    public UserDto withCdxUserId(String cdxUserId) {

        setCdxUserId(cdxUserId);
        return this;
    }

    public UserDto withEmail(String email) {

        setEmail(email);
        return this;
    }

    public UserDto withFirstName(String firstName) {

        setFirstName(firstName);
        return this;
    }

    public UserDto withLastName(String lastName) {

        setLastName(lastName);
        return this;
    }

    public UserDto withRole(String role) {

        setRole(role);
        return this;
    }

    public UserDto withRole(AppRole.RoleType role) {

        setRole(role);
        return this;
    }

    public UserDto withUserRoleId(Long userRoleId) {

        setUserRoleId(userRoleId);
        return this;
    }

    public UserDto withProgramSystemCode(String programSystemCode) {

        setProgramSystemCode(programSystemCode);
        return this;
    }

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
            .add("cdxUserId", cdxUserId)
            .add("email", email)
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("role", role)
            .add("userRoleId", userRoleId)
            .add("programSystemCode", programSystemCode)
            .toString();
    }
}
