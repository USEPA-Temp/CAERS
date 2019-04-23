package gov.epa.cef.web.rest.api;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.RegistrationService;

import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@RestController
@RequestMapping("api/registration")
public class RegistrationResource {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationResource.class);

  @Autowired
  private RegistrationService registrationService;

  @RequestMapping(value = "/user/{userRoleId}/facilities", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<Collection<ProgramFacility>> retrieveFacilitiesForUser(@PathVariable Long userRoleId) {

    Collection<ProgramFacility> result = registrationService.retrieveFacilities(userRoleId);

    return new ResponseEntity<Collection<ProgramFacility>>(result, HttpStatus.OK);
  }
}
