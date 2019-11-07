package gov.epa.cef.web.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CefPreAuthenticationUserDetailsServiceTest extends BaseSecurityTest {

    @Mock
    SecurityService securityService;

    @InjectMocks
    UserDetailsServiceImpl cefPreAuthenticationUserDetailsService;

    @Before
    public void init() {

        when(securityService.createUserRoles("FRED", 142710L, 1111142710L))
            .thenReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_Preparer")));
    }

    @Test
    public void getRoles_Should_ReturnListOfAuthorities_When_ValidRoleIdPassed() {
        Map<String, String> userProperties=new HashMap<>();
        userProperties.put("uid", "FRED");
        userProperties.put("UserRoleId", "1111142710");
        userProperties.put("RoleId", "142710");
        Collection<GrantedAuthority> authorities =
            cefPreAuthenticationUserDetailsService.getRoles(userProperties);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_Preparer", authorities.iterator().next().getAuthority());
    }

    @Test
    public void getRoles_Should_ReturnEmptyList_When_InvalidRoleIdPassed() {
        Map<String, String> userProperties=new HashMap<>();
        userProperties.put("RoleId", "124");
        Collection<GrantedAuthority> authorities =
            cefPreAuthenticationUserDetailsService.getRoles(userProperties);
        assertEquals(0, authorities.size());
    }
}
