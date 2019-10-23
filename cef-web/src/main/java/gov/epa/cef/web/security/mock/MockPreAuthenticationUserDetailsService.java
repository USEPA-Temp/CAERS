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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
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
public class MockPreAuthenticationUserDetailsService implements AuthenticationUserDetailsService {

    private static final Long ORGANIZATION_ID = 86819L;

    private static final Long USER_ROLE_ID = 220632L;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final SecurityService securityService;

    MockPreAuthenticationUserDetailsService(SecurityService securityService) {

        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserDetails(Authentication token) {

        ApplicationUser user = null;

        String cdxWebUserFile = System.getProperty("cdxWebUser.properties");
        if (StringUtils.isNotBlank(cdxWebUserFile)) {
            File file = new File(cdxWebUserFile);
            if (file.exists()) {

                LOGGER.info("Reading cdxWebUser file for authentication/authorization");
                user = readCdxWebUserFile(file);
            } else {

                LOGGER.info("Unable to find cdxWebUserFile: {}", cdxWebUserFile);
            }
        }

        if (user == null) {
            try {

                LOGGER.info("Using hard coded user for authentication/authorization");
                RoleType role = AppRole.RoleType.PREPARER;

                List<GrantedAuthority> roles = this.securityService.createUserRoles(role, USER_ROLE_ID);

                user = new ApplicationUser("thomas.fesperman", roles);

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
                LOGGER.error("unable to load user details: {}", e.getMessage(), e);
                throw new AuthenticationServiceException("unable to load user details");
            }
        }

        return user;
    }

    private ApplicationUser readCdxWebUserFile(File file) {

        ApplicationUser result = null;
        try (InputStream is = new FileInputStream(file)) {

            Properties properties = new Properties();
            properties.load(is);

            RoleType role = RoleType.fromId(Long.parseLong(properties.getProperty("roleId")));
            long userRoleId = Long.parseLong(properties.getProperty("userRoleId"));

            List<GrantedAuthority> roles = this.securityService.createUserRoles(role, userRoleId);

            result = new ApplicationUser(properties.getProperty("userId"), roles);

            Collection<String> ignoreFields = Arrays.asList("userId", "roleId");
            BeanUtils.populate(result, properties.entrySet().stream()
                .filter(e -> ignoreFields.contains(e.getKey().toString()) == false)
                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));

        } catch (Exception e) {

            LOGGER.error("Unable to load user details from file: {}", e.getMessage(), e);
            throw new AuthenticationServiceException("unable to load user details");
        }

        return result;
    }
}
