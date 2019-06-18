package gov.epa.cef.web.service.impl;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.security.ApplicationSecurityUtils;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.TokenDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.ApplicationUserMapper;
import gov.epa.cef.web.soap.SecurityTokenClient;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    SecurityTokenClient tokenClient;
    
    @Autowired
    private CefConfig cefConfig;
    
    @Autowired
    private ApplicationSecurityUtils applicationSecurityUtils;
    
    @Autowired
    private ApplicationUserMapper applicationUserMapper;
    

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.UserService#createToken(java.lang.String)
     */
    @Override
    public TokenDto createToken() {
        TokenDto tokenDto=new TokenDto();
        try {
            Long userRoleId=applicationSecurityUtils.getCurrentApplicationUser().getUserRoleId();
            String userId=applicationSecurityUtils.getCurrentApplicationUser().getUserId();
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
        ApplicationUser applicationUser=applicationSecurityUtils.getCurrentApplicationUser();
        return applicationUserMapper.toUserDto(applicationUser);
    }
    
}
