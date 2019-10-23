package gov.epa.cef.web.security;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cdx.shared.security.naas.CdxHandoffPreAuthenticationUserDetailsService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Profile("prod")
@Service
public class CefPreAuthenticationUserDetailsService extends CdxHandoffPreAuthenticationUserDetailsService {

    private static final Log LOGGER = LogFactory.getLog(CefPreAuthenticationUserDetailsService.class);

    @Autowired
    private SecurityService securityService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) {

        try {
            // Load user details from NAAS token via cdx-shared handoff code
            return (ApplicationUser) super.loadUserDetails(preAuthenticatedAuthenticationToken);
        } catch (Exception e) {
            LOGGER.error("unable to load user details: " + e.getMessage(), e);
            throw new AuthenticationServiceException("unable to load user details");
        }
    }

    @Override
    protected Collection<GrantedAuthority> getRoles(Map<String, String> userProperties) {

        List<GrantedAuthority> roles = this.securityService.createUserRoles(
            nullSafeLong(userProperties.get(ROLE_ID)), nullSafeLong(userProperties.get(USER_ROLE_ID)));

        // detailed logging of the roles from a handoff
        if (CollectionUtils.isNotEmpty(roles)) {
            LOGGER.info("Roles granted:");
            for (GrantedAuthority role : roles) {
                LOGGER.info(role.getAuthority());
            }
        }

        return roles;
    }

}
