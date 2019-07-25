package gov.epa.cef.web.security.mock;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.AppRole.RoleType;

/**
 * Mock the CDX PreAuthenticationUserDetailsService to return a mocked user for local development
 * 
 * @author ahmahfou
 *
 */
@Profile("dev")
@Service
public class MockPreAuthenticationUserDetailsService implements AuthenticationUserDetailsService{

    private static final Log LOGGER = LogFactory.getLog(MockPreAuthenticationUserDetailsService.class);
    private static final Long USER_ROLE_ID = 220632L;
    private static final Long ORGANIZATION_ID = 86819L;

    @Override
    public UserDetails loadUserDetails(Authentication token) {
        try {
            RoleType role=AppRole.RoleType.REVIEWER;
            ApplicationUser user = new ApplicationUser("thomas.fesperman", Arrays.asList(new SimpleGrantedAuthority(role.grantedRoleName())));
            user.setEmail("thomas.fesperman@cgifederal.com");
            user.setFirstName("Thomas");
            user.setLastName("Fesperman");
            user.setOrganization("CGI Federal");
            user.setTitle("Mr.");
            user.setPhoneNumber("7032276001");
            user.setIdTypeCode((int)role.getId());
            user.setIdTypeText(role.roleName());
            user.setRoleId(role.getId());
            user.setUserRoleId(USER_ROLE_ID);
            user.setClientId("GA");
            //60632 or 95092
              user.setUserOrganizationId(ORGANIZATION_ID);
              return user;
        } catch (Exception e) {
            LOGGER.error("unable to load user details: " + e.getMessage(), e);
            throw new AuthenticationServiceException("unable to load user details");
        }
    }
}
