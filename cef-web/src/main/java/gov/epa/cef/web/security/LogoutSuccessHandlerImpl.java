package gov.epa.cef.web.security;

import gov.epa.cdx.shared.ConfigurationHelper;
import gov.epa.cdx.shared.security.naas.HandoffServletService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {

        WebApplicationContext wac =
            WebApplicationContextUtils.getWebApplicationContext(httpServletRequest.getServletContext());

        String cdxHandoffUrl = wac.getBean(ConfigurationHelper.class).getCdxHandoffUrl();

        String logoutResponse =
            wac.getBean(HandoffServletService.class).generateCdxHandoffUrl(cdxHandoffUrl, "/CDX/Logout");

        httpServletResponse.setContentType("text/plain");
        try (Writer writer = httpServletResponse.getWriter()) {

            writer.write(logoutResponse);
            writer.flush();
        }
    }

}
