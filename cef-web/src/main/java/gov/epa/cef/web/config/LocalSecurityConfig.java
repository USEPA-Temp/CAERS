package gov.epa.cef.web.config;

import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.AuthDeniedHandler;
import gov.epa.cef.web.security.LoginAuthEntryPoint;
import gov.epa.cef.web.security.mock.MockHandoffFilter;
import gov.epa.cef.web.security.mock.MockLogoutHandler;
import gov.epa.cef.web.security.mock.MockPreAuthenticationUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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
@EnableJpaAuditing
@EnableWebSecurity
public class LocalSecurityConfig extends WebSecurityConfigurerAdapter{

    private static final String LoginRedirectUrl = "/";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
            .authenticationEntryPoint(new LoginAuthEntryPoint(LoginRedirectUrl))
            .accessDeniedHandler(new AuthDeniedHandler(LoginRedirectUrl)).and()
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
            .logoutSuccessHandler(new MockLogoutHandler());
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter mockCdxPreAuthFilter() {
        MockHandoffFilter result = new MockHandoffFilter();
        result.setAuthenticationManager(authenticationManager());
        return result;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        PreAuthenticatedAuthenticationProvider cdxPreAuth = new PreAuthenticatedAuthenticationProvider();
        MockPreAuthenticationUserDetailsService mockCefUserDetailService = getApplicationContext()
            .getBean(MockPreAuthenticationUserDetailsService.class);
            cdxPreAuth.setPreAuthenticatedUserDetailsService(mockCefUserDetailService);
        providers.add(cdxPreAuth);
        return new ProviderManager(providers);
    }
}
