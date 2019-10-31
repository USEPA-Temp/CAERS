package gov.epa.cef.web.config;

import gov.epa.cef.web.security.AccessDeniedHandlerImpl;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.AuthenticationEntryPointImpl;
import gov.epa.cef.web.security.AuthenticationSuccessHandlerImpl;
import gov.epa.cef.web.security.mock.MockHandoffFilter;
import gov.epa.cef.web.security.mock.MockUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs only in local environment
 *
 */
@Profile("dev")
@Configuration
@EnableWebSecurity
public class LocalSecurityConfig extends WebSecurityConfigurerAdapter{

    private static final String LoginRedirectUrl = "/";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
            .authenticationEntryPoint(new AuthenticationEntryPointImpl(LoginRedirectUrl))
            .accessDeniedHandler(new AccessDeniedHandlerImpl(LoginRedirectUrl)).and()
            .headers().frameOptions().disable().and()
            .csrf().disable()
            .addFilter(mockCdxPreAuthFilter())
            .authorizeRequests()
            .antMatchers("/**")
            .hasAnyRole(
                AppRole.RoleType.PREPARER.roleName(),
                AppRole.RoleType.CERTIFIER.roleName(),
                AppRole.RoleType.REVIEWER.roleName())
            .anyRequest().denyAll().and()
            .logout()
            .logoutSuccessUrl("/");
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter mockCdxPreAuthFilter() {

        MockHandoffFilter result = new MockHandoffFilter();
        result.setAuthenticationManager(authenticationManager());
        result.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());

        return result;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        PreAuthenticatedAuthenticationProvider cdxPreAuth = new PreAuthenticatedAuthenticationProvider();
        MockUserDetailsServiceImpl mockCefUserDetailService =
            getApplicationContext().getBean(MockUserDetailsServiceImpl.class);

        cdxPreAuth.setPreAuthenticatedUserDetailsService(mockCefUserDetailService);
        providers.add(cdxPreAuth);

        return new ProviderManager(providers);
    }
}
