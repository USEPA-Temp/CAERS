package gov.epa.cef.web.client.soap;

import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.AuthMethod;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.DomainTypeCode;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.SecurityTokenPortType;
import gov.epa.cdx.shared.generatedwsdl.securitytoken._3.TokenType;
import gov.epa.cef.web.config.CdxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.OffsetDateTime;

/**
 * A client to consume the CDX SecurityToken service
 *
 * @author ahmahfou
 */
@Component
public class SecurityTokenClient extends AbstractClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterFacilityClient.class);

    private final CdxConfig config;

    @Autowired
    public SecurityTokenClient(CdxConfig config) {

        this.config = config;
    }

    /**
     * Create a SecurityTokenPortType for invoking service methods
     *
     * @param endpoint
     * @return
     */
    protected SecurityTokenPortType getClient(URL endpoint) {

        return this.getClient(endpoint.toString(),
            SecurityTokenPortType.class, false, true);
    }

    /**
     * Creates a new NAAS toke for the user using the admin user credentials
     *
     * @param userId
     * @param ip
     * @return
     */
    public SecurityToken createSecurityToken(String userId, String ip) {

        String result = null;

        try {
            result = getClient(this.config.getNaasTokenUrl())
                .createSecurityToken(this.config.getNaasUser(), this.config.getNaasPassword(), DomainTypeCode.DEFAULT,
                    TokenType.CSM, this.config.getNaasUser(), AuthMethod.PASSWORD, userId, "userId=" + userId, ip);
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }

        return new SecurityToken().withCreated(OffsetDateTime.now()).withIp(ip).withToken(result);
    }
}
