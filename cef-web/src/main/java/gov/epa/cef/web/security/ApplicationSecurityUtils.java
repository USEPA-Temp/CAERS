package gov.epa.cef.web.security;


import java.util.Collection;

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

@Component 
public class ApplicationSecurityUtils {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationSecurityUtils.class);

    public ApplicationUser getCurrentApplicationUser() {
        return (ApplicationUser) getCurrentPrincipal();
    }

    public String getCurrentUserId() {
        return getCurrentApplicationUser().getUsername();
    }

    /**
     * Add the provided user to the security context
     * @param user
     */
    public void asUser(ApplicationUser user){
        addUserToSecurityContext(user);
    }

    /**
     * Return the current security principal
     * @return
     */
    private Object getCurrentPrincipal() {
        // checking security context
        checkSecurityContext();
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Check if the security context is empty
     * @throws ApplicationException
     */
    private void checkSecurityContext() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            throw new ApplicationException(
                    ApplicationErrorCode.E_AUTHORIZATION,
                    "Security Context, authentication or principal is empty.");
        }
    }

    /**
     * Add the provided user to the security context
     * @param appUser
     */
    public void addUserToSecurityContext(ApplicationUser appUser) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities()));
        logger.debug(String.format("User %s explicitly added to security context.", appUser.getUsername()));
        for(GrantedAuthority a: appUser.getAuthorities()) {
            logger.debug(String.format("Role %s explicitly added to security context.", a.getAuthority()));
        }
        
    }

    /**
     * Check if the current user has any of the provided roles
     * @param roles
     * @return
     */
    public boolean hasRole(SimpleGrantedAuthority... roles) {
        for (SimpleGrantedAuthority role : roles) {
            if (getCurrentRoles().contains(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the security roles of the current user
     * @return
     */
    private Collection<? extends GrantedAuthority> getCurrentRoles() {
        checkSecurityContext();
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
