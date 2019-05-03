package gov.epa.cef.web.dto;

import java.io.Serializable;

import gov.epa.cef.web.security.AppRole;

public class UserDto implements Serializable {
	
    private String cdxUserId;

    private String email;

    private String firstName;

    private String lastName;
    
    private AppRole.RoleType role = AppRole.RoleType.Unknown;
    
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

	
}
