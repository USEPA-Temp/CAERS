package gov.epa.cef.web.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.security.ApplicationSecurityUtils;

@RestController
@RequestMapping("/api/user")
public class UserApi {

	private static final Logger logger = LoggerFactory.getLogger(UserApi.class);
	
	@Autowired
	private ApplicationSecurityUtils applicationSecurityUtils;

	/**
	 * Retrieve the currently authenticated user
	 * @return
	 */
	@GetMapping(value = "/me")
	@ResponseBody
	public ResponseEntity<ApplicationUser> retrieveCurrentUser() {

		ApplicationUser result = applicationSecurityUtils.getCurrentApplicationUser();

		return new ResponseEntity<ApplicationUser>(result, HttpStatus.OK);
	}

}
