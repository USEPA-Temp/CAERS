package gov.epa.cef.web.config;

import gov.epa.cef.web.security.AccessDeniedHandlerImpl;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.AuthenticationEntryPointImpl;
import gov.epa.cef.web.security.AuthenticationSuccessHandlerImpl;
import gov.epa.cef.web.security.LogoutSuccessHandlerImpl;
import gov.epa.cef.web.security.mock.MockHandoffFilter;
import gov.epa.cef.web.security.mock.MockUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.ArrayList;
import java.util.List;

import static gov.epa.cef.web.controller.HandoffLandingController.HANDOFF_LANDING_PATH;

/**
 * Runs only in local environment
 *
 */
@Profile("dev")
@Configuration
@EnableWebSecurity
public class LocalSecurityConfig extends WebSecurityConfigurerAdapter{

    private final Environment environment;

    private final CdxConfig cdxConfig;

    @Autowired
    LocalSecurityConfig(Environment environment, CdxConfig cdxConfig) {

        this.environment = environment;
        this.cdxConfig = cdxConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String loginRedirectUrl = this.environment.getRequiredProperty("server.servlet.context-path");

        http.exceptionHandling()
            .authenticationEntryPoint(new AuthenticationEntryPointImpl(loginRedirectUrl))
            .accessDeniedHandler(new AccessDeniedHandlerImpl(loginRedirectUrl)).and()
            .headers().frameOptions().disable().and()
            .csrf().ignoringAntMatchers(HANDOFF_LANDING_PATH)
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .cors().configurationSource(this.cdxConfig.createCorsConfigurationSource()).and()
            .addFilter(mockCdxPreAuthFilter())
            .authorizeRequests()
            .antMatchers("/**")
            .hasAnyRole(
                AppRole.RoleType.PREPARER.roleName(),
                AppRole.RoleType.NEI_CERTIFIER.roleName(),
                AppRole.RoleType.REVIEWER.roleName(),
                AppRole.RoleType.CAERS_ADMIN.roleName())
            .anyRequest().denyAll().and()
            .logout().logoutSuccessHandler(new LogoutSuccessHandlerImpl(loginRedirectUrl));
    }

    @Bean
    AbstractPreAuthenticatedProcessingFilter mockCdxPreAuthFilter() {

        MockHandoffFilter result = new MockHandoffFilter();
        result.setAuthenticationManager(authenticationManager());
        result.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());

        return result;
    }

    @Bean
    protected AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        PreAuthenticatedAuthenticationProvider cdxPreAuth = new PreAuthenticatedAuthenticationProvider();
        MockUserDetailsServiceImpl mockCefUserDetailService =
            getApplicationContext().getBean(MockUserDetailsServiceImpl.class);

        cdxPreAuth.setPreAuthenticatedUserDetailsService(mockCefUserDetailService);
        providers.add(cdxPreAuth);

        return new ProviderManager(providers);
    }
}
