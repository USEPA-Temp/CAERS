package gov.epa.cef.web.security.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.ApplicationSecurityUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Mocks the handoff process by setting a user in the spring context prior to the rest of the chain.
 *
 */
public class MockHandoffFilter implements Filter {

    private final ApplicationSecurityUtils applicationSecurityUtils;

    @Autowired
    public MockHandoffFilter(ApplicationSecurityUtils applicationSecurityUtils) {
        this.applicationSecurityUtils = applicationSecurityUtils;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpSession session = httpReq.getSession();
        applicationSecurityUtils.mockApplicationUser("THOMAS.FESPERMAN", "thomas.fesperman@cgifederal.com", "Thomas", "Fesperman", AppRole.RoleType.Certifier);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig config) {

    }

}
