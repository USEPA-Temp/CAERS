package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.TokenDto;
import gov.epa.cef.web.service.dto.UserDto;

public interface UserService {

    /**
     * Creates a new NAAS token for the currently authenticated user
     *
     * @return NAAS token
     */
    TokenDto createToken();

    /**
     * Returns current authenticated user
     * @return
     */
    UserDto getCurrentUser();

}
