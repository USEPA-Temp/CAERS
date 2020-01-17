package gov.epa.cef.web.security;

import gov.epa.cdx.shared.security.ApplicationUser;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceTest extends BaseSecurityTest {

    @Mock
    ApplicationUser applicationUser;

    @InjectMocks
    private SecurityService securityService;

    @Before
    public void init() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(applicationUser);

        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AppRole.ROLE_NEI_CERTIFIER));
        when(applicationUser.getAuthorities()).thenReturn(authorities);
    }

    @Test
    public void getCurrentApplicationUser_ShouldReturnApplicationUserPrincipal_WhenValidAuthenticatedUserExist() {
        assertEquals(applicationUser, securityService.getCurrentApplicationUser());
    }

    @Test
    public void getCurrentUserId_Should_ReturnsTheCurrentAuthenticatedUserId_WhenValidAuthenticatedUserExist() {
        when(applicationUser.getUsername()).thenReturn("mock-user");
        assertEquals("mock-user", securityService.getCurrentUserId());
    }

    @Test
    public void asUser_Should_AddsTheUserToSecurityContext() {
        securityService.asUser(applicationUser);
        assertEquals(1, securityService.getCurrentApplicationUser().getAuthorities().size());
    }

    @Test
    public void addUserToSecurityContext_Should_AddsTheUserToSecurityContext() {
        securityService.addUserToSecurityContext(applicationUser);
        assertEquals(1, securityService.getCurrentApplicationUser().getAuthorities().size());
    }

    @Test
    public void hasRole_Should_ReturnTrue_When_TheUserHasTheRolePassed() {
        assertEquals(Boolean.FALSE, securityService.hasRole(AppRole.RoleType.NEI_CERTIFIER));
    }

}
