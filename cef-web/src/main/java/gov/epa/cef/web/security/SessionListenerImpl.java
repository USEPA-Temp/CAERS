package gov.epa.cef.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.UUID;

public class SessionListenerImpl implements HttpSessionListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        String uuid = UUID.randomUUID().toString();
        se.getSession().setAttribute(SessionKey.SessionUuid.key(), uuid);
        logger.debug("HttpSession {} created.", uuid);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();

        String uuid = (String) session.getAttribute(SessionKey.SessionUuid.key());
        Long userRoleId = (Long) session.getAttribute(SessionKey.UserRoleId.key());

        if (userRoleId != null) {

            logger.debug("Pulled userRoleId {} from HttpSession {}.", userRoleId, uuid);

            WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());

            context.getBean(SecurityService.class).evictUserCachedItems(userRoleId);

        } else {

            logger.warn("No UserRoleId found in HttpSession{}. No cache items were evicted.", uuid);
        }
    }
}
