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
            RoleType role=AppRole.RoleType.PREPARER;
            ApplicationUser user = new ApplicationUser("JOHN.DOE", Arrays.asList(new SimpleGrantedAuthority(role.grantedRoleName())));
            user.setEmail("doe.john@dnr.ga.gov");
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setOrganization("Georgia Department of Natural Resources");
            user.setTitle("Mr.");
            user.setPhoneNumber("9195551234");
            user.setIdTypeCode((int)role.getId());
            user.setIdTypeText(role.roleName());
            user.setRoleId(role.getId());
            user.setUserRoleId(USER_ROLE_ID);
            //60632 or 95092
              user.setUserOrganizationId(ORGANIZATION_ID);
              user.setClientId("GA");
              return user;
        } catch (Exception e) {
            LOGGER.error("unable to load user details: " + e.getMessage(), e);
            throw new AuthenticationServiceException("unable to load user details");
        }
    }
}
