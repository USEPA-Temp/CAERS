package gov.epa.cef.web.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.TokenDto;
import gov.epa.cef.web.service.dto.UserDto;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    
    @Autowired
    private UserService userService;
    
    /**
     * Retrieve the currently authenticated user
     * @return
     */
    @GetMapping(value = "/me")
    @ResponseBody
    public ResponseEntity<UserDto> retrieveCurrentUser() {
        UserDto result=userService.getCurrentUser();
        return new ResponseEntity<UserDto>(result, HttpStatus.OK);
    }
    
    
    /**
     * Retrieve a new NAAS token for the currently authenticated user
     * @return
     */
    @GetMapping(value = "/token")
    @ResponseBody
    public ResponseEntity<TokenDto> createToken() {
            TokenDto tokenDto=userService.createToken();
            return new ResponseEntity<TokenDto>(tokenDto, HttpStatus.OK);     
        }

}
