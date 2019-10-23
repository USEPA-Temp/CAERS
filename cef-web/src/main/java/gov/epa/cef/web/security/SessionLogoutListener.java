package gov.epa.cef.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionLogoutListener implements HttpSessionListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        // do nothing
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();

        WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());

        context.getBean(SecurityService.class).evictUserCachedItems();
    }
}
