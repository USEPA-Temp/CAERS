package gov.epa.cef.web.security.mock;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.AppRole.RoleType;
import gov.epa.cef.web.security.SecurityService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Mock the CDX PreAuthenticationUserDetailsService to return a mocked user for local development
 *
 * @author ahmahfou
 */
@Profile("dev")
@Service
public class MockUserDetailsServiceImpl implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private static final Long ORGANIZATION_ID = 86819L;

    private static final Long USER_ROLE_ID = 220632L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SecurityService securityService;

    MockUserDetailsServiceImpl(SecurityService securityService) {

        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) {

        ApplicationUser user = null;

        String cdxWebUserFile = System.getProperty("cdxWebUser.properties");
        if (StringUtils.isNotBlank(cdxWebUserFile)) {
            File file = new File(cdxWebUserFile);
            if (file.exists()) {

                logger.info("Reading cdxWebUser file for authentication/authorization");
                user = readCdxWebUserFile(file);
            } else {

                logger.info("Unable to find cdxWebUserFile: {}", cdxWebUserFile);
            }
        }

        if (user == null) {
            try {

                logger.info("Using hard coded user for authentication/authorization");
                RoleType role = AppRole.RoleType.REVIEWER;

                String userId = "thomas.fesperman";
                List<GrantedAuthority> roles = this.securityService.createUserRoles(userId, role, USER_ROLE_ID);

                user = new ApplicationUser(userId, roles);

                user.setEmail("thomas.fesperman@cgifederal.com");
                user.setFirstName("Thomas");
                user.setLastName("Fesperman");
                user.setOrganization("CGI Federal");
                user.setTitle("Mr.");
                user.setPhoneNumber("7032276001");
                user.setIdTypeCode((int) role.getId());
                user.setIdTypeText(role.roleName());
                user.setRoleId(role.getId());
                user.setUserRoleId(USER_ROLE_ID);
                user.setClientId("GA");
                //60632 or 95092
                user.setUserOrganizationId(ORGANIZATION_ID);

            } catch (Exception e) {
                logger.error("unable to load user details: {}", e.getMessage(), e);
                throw new AuthenticationServiceException("unable to load user details");
            }
        }

        // detailed logging of the roles from a handoff
        logger.info("User {} Roles granted: {}", user.getUserId(),
            user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", ")));

        return user;
    }

    private ApplicationUser readCdxWebUserFile(File file) {

        ApplicationUser result = null;
        try (InputStream is = new FileInputStream(file)) {

            Properties properties = new Properties();
            properties.load(is);

            String userId = properties.getProperty("userId");
            RoleType role = RoleType.fromId(Long.parseLong(properties.getProperty("roleId")));
            long userRoleId = Long.parseLong(properties.getProperty("userRoleId"));

            List<GrantedAuthority> roles = this.securityService.createUserRoles(userId, role, userRoleId);

            result = new ApplicationUser(userId, roles);

            Collection<String> ignoreFields = Arrays.asList("userId", "roleId");
            BeanUtils.populate(result, properties.entrySet().stream()
                .filter(e -> ignoreFields.contains(e.getKey().toString()) == false)
                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));

        } catch (Exception e) {

            logger.error("Unable to load user details from file: {}", e.getMessage(), e);
            throw new AuthenticationServiceException("unable to load user details");
        }

        return result;
    }
}
