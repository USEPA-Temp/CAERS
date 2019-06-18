package gov.epa.cef.web.security;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RunWith(MockitoJUnitRunner.class)
public class CefPreAuthenticationUserDetailsServiceTest {
   
    @InjectMocks
    CefPreAuthenticationUserDetailsService cefPreAuthenticationUserDetailsService;
    
    
    @Test
    public void getRoles_Should_ReturnListOfAuthorities_When_ValidRoleIdPassed() {
        Map<String, String> userProperties=new HashMap<>();
        userProperties.put("RoleId", "142710");
        Collection<SimpleGrantedAuthority> authorities=cefPreAuthenticationUserDetailsService.getRoles(userProperties);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_Preparer", authorities.iterator().next().getAuthority());
    }
    
    @Test
    public void getRoles_Should_ReturnEmptyList_When_InvalidRoleIdPassed() {
        Map<String, String> userProperties=new HashMap<>();
        userProperties.put("RoleId", "124");
        Collection<SimpleGrantedAuthority> authorities=cefPreAuthenticationUserDetailsService.getRoles(userProperties);
        assertEquals(0, authorities.size());
    }

}
