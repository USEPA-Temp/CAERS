package gov.epa.cef.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cdx.shared.security.naas.CdxHandoffPreAuthenticationUserDetailsService;

@Service
public class CefPreAuthenticationUserDetailsService extends CdxHandoffPreAuthenticationUserDetailsService {

	private static final Log logger = LogFactory.getLog(CefPreAuthenticationUserDetailsService.class);

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken)
			throws UsernameNotFoundException {
		try {
			// Load user details from NAAS token via cdx-shared handoff code
			ApplicationUser applicationUser = (ApplicationUser) super.loadUserDetails(
					preAuthenticatedAuthenticationToken);
			return applicationUser;
		} catch (Exception e) {
			logger.error("unable to load user details: " + e.getMessage(), e);
			throw new AuthenticationServiceException("unable to load user details");
		}
	}

	@Override
	protected Collection<SimpleGrantedAuthority> getRoles(Map<String, String> userProperties) {
		Long roleId = nullSafeLong(userProperties.get(ROLE_ID));
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		try {
			AppRole.RoleType appRole = AppRole.RoleType.fromId(roleId);
			roles.add(new SimpleGrantedAuthority(appRole.grantedRoleName()));
		} catch (IllegalArgumentException e) {

			logger.warn(e.getMessage());
		}

		// detailed logging of the roles from a handoff
		if (CollectionUtils.isNotEmpty(roles)) {
			logger.info("Roles granted:");
			for (SimpleGrantedAuthority role : roles) {
				logger.info(role.getAuthority());
			}
		}
		return roles;
	}

}
