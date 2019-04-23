package gov.epa.cef.web.rest.exceptions;

import org.apache.commons.lang3.StringUtils;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

public class CorsResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        MultivaluedMap<String, Object> headers = containerResponseContext.getHeaders();
//        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        headers.add("Access-Control-Expose-Headers", "Location");
        if (!StringUtils.isEmpty(containerRequestContext.getHeaderString("Access-Control-Request-Headers"))) {
            headers.add("Access-Control-Allow-Headers", containerRequestContext.getHeaderString("Access-Control-Request-Headers"));
        }
        else {
        	headers.add("Access-Control-Allow-Headers", "Authorization");
        }
    }    
}
