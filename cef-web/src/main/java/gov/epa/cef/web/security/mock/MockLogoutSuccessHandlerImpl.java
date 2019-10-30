package gov.epa.cef.web.security.mock;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class MockLogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        // send back the servlet context path to "log back in" ... no logout in mock mode

        httpServletResponse.setContentType("text/plain");
        try (Writer writer = httpServletResponse.getWriter()) {

            writer.write(String.format("%s?e=mc2",
                httpServletRequest.getServletContext().getContextPath()));
            writer.flush();
        }

        httpServletResponse.getWriter();
    }
}
