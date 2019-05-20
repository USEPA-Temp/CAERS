package gov.epa.cef.web.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.security.ApplicationSecurityUtils;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.TokenDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.ApplicationUserMapper;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private ApplicationSecurityUtils applicationSecurityUtils;
    
    @Autowired
    private UserService userService;

    /**
     * Retrieve the currently authenticated user
     * @return
     */
    @GetMapping(value = "/me")
    @ResponseBody
    public ResponseEntity<UserDto> retrieveCurrentUser() {

        UserDto result = ApplicationUserMapper.toUserDto().apply(applicationSecurityUtils.getCurrentApplicationUser());

        return new ResponseEntity<UserDto>(result, HttpStatus.OK);
    }
    
    
    /**
     * Retrieve a new NAAS token for the currently authenticated user
     * @return
     */
    @GetMapping(value = "/token")
    @ResponseBody
    public ResponseEntity<TokenDto> createToken() {
            Long userRoleId=applicationSecurityUtils.getCurrentApplicationUser().getUserRoleId();
            String token = userService.createToken(applicationSecurityUtils.getCurrentApplicationUser().getUserId());
            TokenDto tokenDto=new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setUserRoleId(userRoleId);
            return new ResponseEntity<TokenDto>(tokenDto, HttpStatus.OK);     
        }

}
