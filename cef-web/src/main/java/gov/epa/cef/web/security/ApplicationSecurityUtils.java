package gov.epa.cef.web.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;

import java.util.Arrays;
import java.util.Collection;

@Component 
public class ApplicationSecurityUtils {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationSecurityUtils.class);

    public ApplicationUser getCurrentApplicationUser() {
        return (ApplicationUser) getCurrentPrincipal();
    }

    public String getCurrentUserId() {
        return getCurrentApplicationUser().getUsername();
    }

    public void asUser(ApplicationUser user){
        addUserToSecurityContext(user);
    }

    public void mockApplicationUser(String userId, String email, String firstName, String lastName, AppRole.RoleType role) {
        ApplicationUser user = new ApplicationUser(userId, Arrays.asList(new SimpleGrantedAuthority(role.grantedRoleName())));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setOrganization("CGI Federal");
        user.setTitle("Mr.");
        user.setPhoneNumber("7032276001");
        user.setIdTypeCode((int)role.getId());
        user.setIdTypeText(role.roleName());
        user.setRoleId(role.getId());
        user.setUserRoleId(220632L);
        //60632 or 95092
      	user.setUserOrganizationId(86819L);
        addUserToSecurityContext(user);

    }

    private Object getCurrentPrincipal() {
        // checking security context
        checkSecurityContext();
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void checkSecurityContext() throws ApplicationException {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            throw new ApplicationException(
                    ApplicationErrorCode.E_Authorization,
                    "Security Context, authentication or principal is empty.");
        }
    }

    public void addUserToSecurityContext(ApplicationUser appUser) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities()));
        logger.info(String.format("User %s explicitly added to security context.", appUser.getUsername()));
        for(GrantedAuthority a: appUser.getAuthorities()) {
        	logger.info(String.format("Role %s explicitly added to security context.", a.getAuthority()));
        }
        
    }

    public boolean hasRole(SimpleGrantedAuthority... roles) {
        for (SimpleGrantedAuthority role : roles) {
            if (getCurrentRoles().contains(role)) {
                return true;
            }
        }
        return false;
    }

    private Collection<? extends GrantedAuthority> getCurrentRoles() {
        checkSecurityContext();
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
