package gov.epa.cef.web.service.impl;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.config.CdxConfig;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.security.ApplicationSecurityUtils;
import gov.epa.cef.web.service.dto.TokenDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.ApplicationUserMapper;
import gov.epa.cef.web.client.soap.SecurityTokenClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest extends BaseServiceTest {

    @Mock
    SecurityTokenClient tokenClient;

    @Mock
    private CefConfig cefConfig;

    @Mock
    private ApplicationSecurityUtils applicationSecurityUtils;

    @Mock
    private CdxConfig cdxConfig;

    @Mock
    private ApplicationUser applicationUser;

    @Mock
    private ApplicationUserMapper applicationUserMapper;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Before
    public void init() {
        when(applicationSecurityUtils.getCurrentApplicationUser()).thenReturn(applicationUser);
        when(applicationSecurityUtils.getCurrentApplicationUser().getUserId()).thenReturn("mock-user");
        when(applicationSecurityUtils.getCurrentApplicationUser().getUserRoleId()).thenReturn(123L);
        when(cefConfig.getCdxConfig()).thenReturn(cdxConfig);
        when(cefConfig.getCdxConfig().getNaasUser()).thenReturn("naas-user");
        when(cefConfig.getCdxConfig().getNaasPassword()).thenReturn("naas-password");
        when(cefConfig.getCdxConfig().getNaasIp()).thenReturn("127.0.0.1");
        when(cefConfig.getCdxConfig().getFrsBaseUrl()).thenReturn("http:\\frs-url");
    }


    @Test
    public void createToken_Should_ReturnValidToken_When_ValidUserIdPassed() throws MalformedURLException {
        when(tokenClient.createSecurityToken(new URL("http:\\mockurl"), "naas-user", "naas-password", "mock-user", "127.0.0.1")).thenReturn("token");
        when(cefConfig.getCdxConfig().getNaasTokenUrl()).thenReturn("http:\\mockurl");
        TokenDto token=userServiceImpl.createToken();
        assertEquals("token", token.getToken());
        assertEquals(new Long(123), token.getUserRoleId());
        assertEquals("token", token.getToken());
        assertEquals("http:\\frs-url", token.getBaseServiceUrl());
    }

    @Test(expected=ApplicationException.class)
    public void createToken_Should_ThrowException_When_ConfigurationMissing() throws MalformedURLException {
        when(cefConfig.getCdxConfig().getNaasTokenUrl()).thenReturn(null);
        userServiceImpl.createToken();
    }

    @Test
    public void getCurrentUser_Should_ReturnUserDtoObject_WhenAuthenticatedUserAlreadyExist(){
        UserDto userDto=userServiceImpl.getCurrentUser();
        when(applicationUserMapper.toUserDto(applicationUser)).thenReturn(userDto);
        assertEquals(userDto, userServiceImpl.getCurrentUser());
    }
}
