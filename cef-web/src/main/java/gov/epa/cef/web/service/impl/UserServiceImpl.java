package gov.epa.cef.web.service.impl;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.client.soap.SecurityTokenClient;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.security.SecurityService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.TokenDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.ApplicationUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private SecurityTokenClient tokenClient;

    @Autowired
    private CefConfig cefConfig;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ApplicationUserMapper applicationUserMapper;


    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.UserService#createToken(java.lang.String)
     */
    @Override
    public TokenDto createToken() {
        TokenDto tokenDto=new TokenDto();
        try {
            Long userRoleId= securityService.getCurrentApplicationUser().getUserRoleId();
            String userId= securityService.getCurrentApplicationUser().getUserId();
            URL securityTokenUrl = new URL(cefConfig.getCdxConfig().getNaasTokenUrl());
            String token=tokenClient.createSecurityToken(securityTokenUrl, cefConfig.getCdxConfig().getNaasUser(), cefConfig.getCdxConfig().getNaasPassword(), userId, cefConfig.getCdxConfig().getNaasIp());
            tokenDto=new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setUserRoleId(userRoleId);
            tokenDto.setBaseServiceUrl(cefConfig.getCdxConfig().getFrsBaseUrl());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw ApplicationException.asApplicationException(e);
        }

        return tokenDto;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.UserService#getCurrentUser()
     */
    @Override
    public UserDto getCurrentUser() {
        ApplicationUser applicationUser= securityService.getCurrentApplicationUser();
        return applicationUserMapper.toUserDto(applicationUser);
    }

}
