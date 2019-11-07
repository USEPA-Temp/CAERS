package gov.epa.cef.web.security;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String loginUrl;

    public AuthenticationEntryPointImpl(String loginUrl) {

        this.loginUrl = loginUrl;
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
        throws IOException, ServletException {

        String fetch = request.getHeader("X-Requested-With");
        if (Strings.nullToEmpty(fetch).equalsIgnoreCase("XMLHttpRequest")) {

            this.logger.debug("HTTP XMLHttpRequest call, sending unauthorized.");
            response.sendError(401);

        } else {

            response.sendRedirect(this.loginUrl);
        }
    }
}
