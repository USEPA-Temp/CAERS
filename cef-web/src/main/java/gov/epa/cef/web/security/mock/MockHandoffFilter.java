package gov.epa.cef.web.security.mock;


import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * Mocks the cdx handoff filter.
 *
 */
public class MockHandoffFilter extends AbstractPreAuthenticatedProcessingFilter {

    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
        return "mock";
    }

    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return "mock";
    }
}
