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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpringSecurityAuditorAwareTest extends BaseSecurityTest {

    @Mock
    ApplicationUser applicationUser;

    @Mock
    Authentication authentication;

    @InjectMocks
    private SpringSecurityAuditorAware springSecurityAuditorAware;

    @Before
    public void init() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(applicationUser);
        when(applicationUser.getUserId()).thenReturn("mock-user");
    }


    @Test
    public void getCurrentAuditor_Should_ReturnCurrentAuthenticatedUser_When_AuthenticatedUserAlreadyExist() {
        when(authentication.isAuthenticated()).thenReturn(true);
        Optional<String> currentAuditor=springSecurityAuditorAware.getCurrentAuditor();
        assertEquals("mock-user", currentAuditor.get());
    }

    @Test
    public void getCurrentAuditor_Should_ReturnNull_When_NoAuthenticatedUserAvailable() {
        when(authentication.isAuthenticated()).thenReturn(false);
        assertEquals(null, springSecurityAuditorAware.getCurrentAuditor());
    }
}
