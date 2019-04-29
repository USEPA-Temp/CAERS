package gov.epa.cef.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import gov.epa.cdx.shared.security.naas.CdxHandoffPreAuthenticationFilter;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.ApplicationSecurityUtils;
import gov.epa.cef.web.security.mock.MockHandoffFilter;

@Profile("dev")
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "gov.epa.cdx.shared" })
@ImportResource(locations = { "file:${spring.config.dir}/cdx-shared/cdx-shared-config.xml" })
public class LocalSecurityConfig extends SecurityConfig {

	@Autowired
	ApplicationSecurityUtils applicationSecurityUtils;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
			.csrf().disable()
			.addFilter(cdxWebPreAuthFilter())
			.addFilterBefore(mockHandoffFilter(), CdxHandoffPreAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/**")
			.hasAnyRole(
					AppRole.RoleType.Peparer.roleName(),
					AppRole.RoleType.Certifier.roleName(),
					AppRole.RoleType.Reviewer.roleName())
			.anyRequest().denyAll();
	}
	

	@Bean
	MockHandoffFilter mockHandoffFilter() {
		return new MockHandoffFilter(applicationSecurityUtils);
	}
	
}
