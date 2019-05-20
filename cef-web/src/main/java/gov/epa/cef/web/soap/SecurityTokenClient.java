package gov.epa.cef.web.soap;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.AuthMethod;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.DomainTypeCode;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.SecurityTokenPortType;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.TokenType;

@Component
public class SecurityTokenClient extends AbstractClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterFacilityClient.class);
    
    /**
     * Create a SecurityTokenPortType for invoking service methods
     * @param endpoint
     * @param mtom
     * @param chunking
     * @return
     */
    protected SecurityTokenPortType getClient(URL endpoint, boolean mtom, boolean chunking) {
        return (SecurityTokenPortType) this.getClient(endpoint.toString(),
                SecurityTokenPortType.class, mtom, chunking);
    }
    
    public String createSecurityToken(URL endpoint, String adminUserId, String adminPassword, String userId, String ip) {
        try {
            String token= getClient(endpoint, false, true).createSecurityToken(adminUserId, adminPassword, DomainTypeCode.DEFAULT,
                    TokenType.CSM, adminUserId, AuthMethod.PASSWORD, userId, "userId="+userId, ip);
            return token;
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }
    }
}
