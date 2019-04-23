package gov.epa.cef.web.rest.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.epa.cef.web.service.RegistrationService;

import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@Component
@Path("/registration")
public class RegistrationResource {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationResource.class);
  
  @Autowired
  private RegistrationService registrationService;
  
  @GET
  @Path("/user/{id}/facilities")
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveFacilitiesForUser(@PathParam("id") Long userRoleId) {

    Collection<ProgramFacility> result = registrationService.retrieveFacilities(userRoleId);

    return Response.ok(result).build();
  }
}
