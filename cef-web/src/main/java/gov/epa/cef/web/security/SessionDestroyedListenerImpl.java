package gov.epa.cef.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionDestroyedListenerImpl implements HttpSessionListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        // do nothing
        logger.debug("HttpSession {} created.", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();
        Long userRoleId = (Long) session.getAttribute(SessionKey.UserRoleId.key());

        if (userRoleId != null) {

            logger.debug("Pulled userRoleId {} from HttpSession {}.", userRoleId, session.getId());

            WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());

            context.getBean(SecurityService.class).evictUserCachedItems(userRoleId);

        } else {

            logger.warn("No UserRoleId found in HttpSession{}. No cache items were evicted.", session.getId());
        }
    }
}
