package gov.epa.cef.web.service.mock;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.security.AppRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class JunitUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // potentially use this with a switch(username)

        ApplicationUser applicationUser = new ApplicationUser("FFLINTSTONE",
            Collections.singletonList(new SimpleGrantedAuthority(AppRole.ROLE_PREPARER)));
        applicationUser.setFirstName("Fred");
        applicationUser.setLastName("Flintstone");

        return applicationUser;
    }
}
