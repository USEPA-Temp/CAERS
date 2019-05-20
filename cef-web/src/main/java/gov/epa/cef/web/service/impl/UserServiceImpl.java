package gov.epa.cef.web.service.impl;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.soap.SecurityTokenClient;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    SecurityTokenClient tokenClient;
    
    @Autowired
    private CefConfig cefConfig;

    @Override
    public String createToken(String userId) {
        String token="";
        try {
            URL securityTokenUrl = new URL(cefConfig.getCdxConfig().getNaasTokenUrl());
            token=tokenClient.createSecurityToken(securityTokenUrl, cefConfig.getCdxConfig().getNaasUser(), cefConfig.getCdxConfig().getNaasPassword(), userId, cefConfig.getCdxConfig().getNaasIp());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw ApplicationException.asApplicationException(e);
        }
        return token;
    }

}
