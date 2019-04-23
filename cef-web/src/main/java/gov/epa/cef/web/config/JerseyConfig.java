package gov.epa.cef.web.config;

import java.util.logging.Logger;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import gov.epa.cef.web.rest.api.RegistrationResource;
import gov.epa.cef.web.rest.exceptions.ApplicationExceptionMapper;
import gov.epa.cef.web.rest.exceptions.CorsResponseFilter;

@Configuration
public class JerseyConfig extends ResourceConfig {
	 private static Logger logger = Logger.getLogger(JerseyConfig.class.getName());

	    public JerseyConfig() {
	    	
	    	// custom jackson handling
	        ObjectMapper mapper = new ObjectMapper()
	                .registerModule(new Jdk8Module())
	                .registerModule(new JavaTimeModule())
	                .registerModule(new Hibernate5Module())
	                .enable(SerializationFeature.INDENT_OUTPUT)
	                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
	        register(provider);

	        // custom exception mapper
	        register(ApplicationExceptionMapper.class);

	        // handle CORS responses
	        register(CorsResponseFilter.class);

	        // xss filter
	        //register(new XssFilter(xssHelper));

	     // multi part file handling so we can upload files to the rest api
            register(MultiPartFeature.class);

	        register(RegistrationResource.class);
	        logger.info("Initialized Jersey");
	    }
}
