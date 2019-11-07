package gov.epa.cef.web.security;

import gov.epa.cdx.shared.security.ApplicationUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        Long userRoleId = ((ApplicationUser) authentication.getPrincipal()).getUserRoleId();
        if (userRoleId != null) {

            HttpSession session = httpServletRequest.getSession();

            logger.debug("Adding userRoleId {} to HttpSession {}.",
                userRoleId, session.getAttribute(SessionKey.SessionUuid.key()));

            session.setAttribute(SessionKey.UserRoleId.key(), userRoleId);

        } else {

            logger.warn("Authenticated User does not have a valid userRoleId.");
        }
    }
}
