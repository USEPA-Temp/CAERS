package gov.epa.cef.web.security.mock;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
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
