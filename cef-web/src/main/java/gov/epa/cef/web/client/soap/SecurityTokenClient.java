package gov.epa.cef.web.client.soap;

import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.AuthMethod;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.DomainTypeCode;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.SecurityTokenPortType;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 * A client to consume the CDX SecurityToken service
 *
 * @author ahmahfou
 *
 */
@Component
public class SecurityTokenClient extends AbstractClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterFacilityClient.class);

    /**
     * Create a SecurityTokenPortType for invoking service methods
     * @param endpoint
     * @return
     */
    protected SecurityTokenPortType getClient(URL endpoint) {
        return this.getClient(endpoint.toString(),
            SecurityTokenPortType.class, false, true);
    }

    /**
     * Creates a new NAAS toke for the user using the admin user credentials
     * @param endpoint
     * @param adminUserId
     * @param adminPassword
     * @param userId
     * @param ip
     * @return
     */
    public String createSecurityToken(URL endpoint, String adminUserId, String adminPassword, String userId, String ip) {
        try {
            String token = getClient(endpoint).createSecurityToken(adminUserId, adminPassword, DomainTypeCode.DEFAULT,
                    TokenType.CSM, adminUserId, AuthMethod.PASSWORD, userId, "userId="+userId, ip);
            return token;
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }
    }
}
