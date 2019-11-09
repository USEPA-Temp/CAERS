package gov.epa.cef.web.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final String nextHopUrl;

    public LogoutSuccessHandlerImpl(String nextHopUrl) {

        this.nextHopUrl = nextHopUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        httpServletResponse.setContentType(MediaType.TEXT_PLAIN_VALUE);

        Writer writer = httpServletResponse.getWriter();
        writer.write(this.nextHopUrl);
        writer.flush();
    }
}
