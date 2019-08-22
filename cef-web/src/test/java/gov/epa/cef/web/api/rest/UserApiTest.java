package gov.epa.cef.web.api.rest;

import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.TokenDto;
import gov.epa.cef.web.service.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserApiTest extends BaseApiTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserApi userApi;


    @Test
    public void retrieveCurrentUser_Should_ReturnUserDtoObject_WithStatusOk_WhenValidAuthenticatedUserExist() {
        UserDto userDto=new UserDto();
        when(userService.getCurrentUser()).thenReturn(userDto);
        ResponseEntity<UserDto> result=userApi.retrieveCurrentUser();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDto, result.getBody());
    }

    @Test
    public void createToken_Should_ReturnNewGeneratedTokenWithStatusOk_WhenValidAuthenticatedUserExist() {
        TokenDto tokenDto=new TokenDto();
        when(userService.createToken()).thenReturn(tokenDto);
        ResponseEntity<TokenDto> result=userApi.createToken();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(tokenDto, result.getBody());
    }

}
