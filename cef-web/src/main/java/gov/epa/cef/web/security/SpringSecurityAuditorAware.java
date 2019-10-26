package gov.epa.cef.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private final SecurityService securityService;

    @Autowired
    SpringSecurityAuditorAware(SecurityService securityService) {

        this.securityService = securityService;
    }

    public Optional<String> getCurrentAuditor() {

        if (this.securityService.hasSecurityContext()) {

            return Optional.of(this.securityService.getCurrentUserId());
        }

        return Optional.empty();
    }
}
