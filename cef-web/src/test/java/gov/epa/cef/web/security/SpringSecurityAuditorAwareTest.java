package gov.epa.cef.web.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpringSecurityAuditorAwareTest extends BaseSecurityTest {

    @Mock
    SecurityService securityService;

    @InjectMocks
    private SpringSecurityAuditorAware springSecurityAuditorAware;

    @Before
    public void init() {

        when(securityService.getCurrentUserId()).thenReturn("mock-user");
    }

    @Test
    public void getCurrentAuditor_Should_ReturnCurrentAuthenticatedUser_When_AuthenticatedUserAlreadyExist() {

        when(securityService.hasSecurityContext()).thenReturn(true);

        Optional<String> currentAuditor = springSecurityAuditorAware.getCurrentAuditor();
        assertTrue(currentAuditor.isPresent());
        assertEquals("mock-user", currentAuditor.get());
    }

    @Test
    public void getCurrentAuditor_Should_ReturnNull_When_NoAuthenticatedUserAvailable() {

        when(securityService.hasSecurityContext()).thenReturn(false);

        assertFalse(springSecurityAuditorAware.getCurrentAuditor().isPresent());
    }
}
