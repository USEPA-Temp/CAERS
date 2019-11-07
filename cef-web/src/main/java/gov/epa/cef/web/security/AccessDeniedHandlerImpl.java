package gov.epa.cef.web.security;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final String forwardUrl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public AccessDeniedHandlerImpl(String forwardUrl) {

        this.forwardUrl = forwardUrl;
    }

    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        this.logger.warn("Access Denied", e);
        String fetch = request.getHeader("X-Requested-With");
        if (Strings.nullToEmpty(fetch).equalsIgnoreCase("XMLHttpRequest")) {

            this.logger.debug("HTTP XMLHttpRequest call, sending unauthorized.");
            response.sendError(401);

        } else {

            response.sendRedirect(this.forwardUrl);
        }

    }
}