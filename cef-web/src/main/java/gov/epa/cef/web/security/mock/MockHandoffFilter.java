package gov.epa.cef.web.security.mock;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * Mocks the cdx handoff filter.
 *
 */
public class MockHandoffFilter extends AbstractPreAuthenticatedProcessingFilter {
		  
	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException
	  {
	    super.doFilter(request, response, chain);
	  }
	  
	  protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest)
	  {
	    return "mock";
	  }
	  
	  protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest)
	  {
	    return "mock";
	  }
	  
	  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
	    throws IOException, ServletException
	  {
	    super.successfulAuthentication(request, response, authResult);
	  }
	}
