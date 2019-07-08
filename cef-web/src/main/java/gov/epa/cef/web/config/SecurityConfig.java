package gov.epa.cef.web.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import gov.epa.cdx.shared.security.naas.CdxHandoffPreAuthenticationFilter;
import gov.epa.cdx.shared.security.naas.HandoffToCdxServlet;
import gov.epa.cdx.shared.security.naas.LoginRedirectServlet;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.CefPreAuthenticationUserDetailsService;

@Profile("prod")
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "gov.epa.cdx.shared" })
@ImportResource(locations = { "file:${spring.config.dir}/cdx-shared/cdx-shared-config.xml" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**")
            .csrf().disable()
            .addFilter(cdxWebPreAuthFilter())
            .authorizeRequests()
            .antMatchers("/api/public/**").permitAll()
            .antMatchers("/J2AHandoff*").permitAll()
            .antMatchers("/**")
            .hasAnyRole(
                AppRole.RoleType.PREPARER.roleName(),
                AppRole.RoleType.CERTIFIER.roleName(),
                AppRole.RoleType.REVIEWER.roleName())
            .anyRequest().denyAll();
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> ngnToCdxWebHandoff() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new HandoffToCdxServlet());
        servRegBean.addUrlMappings("/J2AHandoff");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> loginRedirect() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new LoginRedirectServlet());
        servRegBean.addUrlMappings("/LoginRedirect");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }

    @Bean
    public AbstractPreAuthenticatedProcessingFilter cdxWebPreAuthFilter() {
        CdxHandoffPreAuthenticationFilter result = new CdxHandoffPreAuthenticationFilter();
        result.setAuthenticationManager(authenticationManager());
        return result;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        PreAuthenticatedAuthenticationProvider cdxPreAuth = new PreAuthenticatedAuthenticationProvider();
        CefPreAuthenticationUserDetailsService cefUserDetailService = getApplicationContext()
            .getBean(CefPreAuthenticationUserDetailsService.class);
        cdxPreAuth.setPreAuthenticatedUserDetailsService(cefUserDetailService);
        providers.add(cdxPreAuth);
        return new ProviderManager(providers);
    }
}
