package gov.epa.cef.web.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import gov.epa.cdx.shared.security.ApplicationUser;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationSecurityUtilsTest {

    @Mock
    ApplicationUser applicationUser;
    
    @InjectMocks
    private ApplicationSecurityUtils applicationSecurityUtils;
    
    private SimpleGrantedAuthority ROLE_CERTIFIER=new SimpleGrantedAuthority("CERTIFIER");

    @Before
    public void init() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(applicationUser);
        
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(ROLE_CERTIFIER);
        when(applicationUser.getAuthorities()).thenReturn(authorities);
    }

    @Test
    public void getCurrentApplicationUser_ShouldReturnApplicationUserPrincipal_WhenValidAuthenticatedUserExist() {
        assertEquals(applicationUser, applicationSecurityUtils.getCurrentApplicationUser());
    }

    @Test
    public void getCurrentUserId_Should_ReturnsTheCurrentAuthenticatedUserId_WhenValidAuthenticatedUserExist() {
        when(applicationUser.getUsername()).thenReturn("mock-user");
        assertEquals("mock-user", applicationSecurityUtils.getCurrentUserId());
    }

    @Test
    public void asUser_Should_AddsTheUserToSecurityContext() {
        applicationSecurityUtils.asUser(applicationUser);
        assertEquals(1, applicationSecurityUtils.getCurrentApplicationUser().getAuthorities().size());
    }

    @Test
    public void addUserToSecurityContext_Should_AddsTheUserToSecurityContext() {
        applicationSecurityUtils.addUserToSecurityContext(applicationUser);
        assertEquals(1, applicationSecurityUtils.getCurrentApplicationUser().getAuthorities().size());
    }

    @Test
    public void hasRole_Should_ReturnTrue_When_TheUserHasTheRolePassed() {
        assertEquals(Boolean.FALSE, applicationSecurityUtils.hasRole(ROLE_CERTIFIER));
    }

}
