package gov.epa.cef.web.client.soap;

import net.exchangenetwork.wsdl.register.streamlined._1.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.epa.cef.web.config.CdxConfig;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;

@Component
public class StreamlinedRegistrationServiceClient extends AbstractClient {

    private static final Logger logger = LoggerFactory.getLogger(StreamlinedRegistrationServiceClient.class);

    private final CdxConfig config;

    @Autowired
    StreamlinedRegistrationServiceClient(CdxConfig config) {

        this.config = config;
    }

    protected StreamlinedRegistrationService getClient(boolean mtom, boolean chunking) {

        String endpoint = this.config.getStreamlinedRegistrationServiceEndpoint();

        return this.getClient(endpoint, StreamlinedRegistrationService.class, mtom, chunking);
    }

    public List<RegistrationNewUserProfile> retrieveUsersByCriteria(RegistrationUserSearchCriteria criteria) {

        try {
            String token = authenticate();
            return getClient(false, true).retrieveUsersByCriteria(token, criteria);
        } catch (RegisterException fault) {
            throw this.handleException(convertFault(fault), logger);
        } catch (Exception e) {
            throw this.handleException(e, logger);
        }
    }

    private String authenticate() {

        try {
            String userId = this.config.getNaasUser();
            String password = this.config.getNaasPassword();

            return getClient(false, true).authenticate(userId, password, DOMAIN, AUTH_METHOD);
        } catch (RegisterException fault) {
            throw this.handleException(convertFault(fault), logger);
        } catch (Exception e) {
            throw this.handleException(e, logger);
        }
    }

    ApplicationException convertFault(RegisterException fault) {
        if (fault.getFaultInfo() == null) {
            return new ApplicationException(ApplicationErrorCode.E_REMOTE_SERVICE_ERROR, fault.getMessage());
        } else {
            ApplicationErrorCode code;
            try {
                code = ApplicationErrorCode.valueOf(fault.getFaultInfo().getErrorCode().value());
            } catch (Exception e) {
                logger.warn("Could not translate fault.");
                code = ApplicationErrorCode.E_REMOTE_SERVICE_ERROR;
            }
            return new ApplicationException(code, fault.getFaultInfo().getDescription());
        }
    }
}
