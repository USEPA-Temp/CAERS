package gov.epa.cef.web.client.soap;

import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import net.exchangenetwork.wsdl.register.program_facility._1.RegisterProgramFacilityService;
import net.exchangenetwork.wsdl.register.program_facility._1.RegistrationFacility;
import net.exchangenetwork.wsdl.register.program_facility._1.RegistrationOrganization;
import net.exchangenetwork.wsdl.register.program_facility._1.RegistrationProgramFacilityNaicsCode;
import net.exchangenetwork.wsdl.register.program_facility._1.RegistrationRoleType;
import net.exchangenetwork.wsdl.register.program_facility._1.RegistrationUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Component
public class RegisterFacilityClient extends AbstractClient<RegisterProgramFacilityService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterFacilityClient.class);

    /**
     * Create a RegisterProgramFacilityService for invoking service methods
     * @param endpoint
     * @param mtom
     * @param chunking
     * @return
     */
    protected RegisterProgramFacilityService getClient(URL endpoint, boolean mtom, boolean chunking) {

        return this.getClient(endpoint.toString(), RegisterProgramFacilityService.class, mtom, chunking);
    }

    /**
     * Create a NAAS token for authentication into Register services
     * @param endpoint
     * @param userId
     * @param password
     * @return
     * @throws ApplicationException
     */
    public String authenticate(URL endpoint, String userId, String password)  {
        try {
            return getClient(endpoint, false, true).authenticate(userId, password, DOMAIN, AUTH_METHOD);
        } catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
            throw this.handleException(convertFault(fault), LOGGER);
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }
    }

    /**
     * Retrieve CDX facilities associated with the user
     * @param endpoint
     * @param token
     * @param userRoleId
     * @return
     * @throws ApplicationException
     */
    public List<ProgramFacility> getFacilitiesByUserRoleId(URL endpoint, String token, Long userRoleId) {
        try {
            return getClient(endpoint, false, true).retrieveFacilitiesByUserRoleId(token, userRoleId);
        } catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
            throw this.handleException(convertFault(fault), LOGGER);
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }
    }

    /**
     * Retrieve CDX facilities by id
     * @param endpoint
     * @param token
     * @param programId
     * @return
     * @throws ApplicationException
     */
    public List<ProgramFacility> getFacilityByProgramId(URL endpoint, String token, String programId) {
        try {
            return getClient(endpoint, false, true).retrieveProgramFacilitiesByProgramIds(token, Arrays.asList(new String[]{programId}));
        } catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
            throw this.handleException(convertFault(fault), LOGGER);
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }
    }

    /**
     * Retrieve CDX facilities associated with the user
     * @param endpoint
     * @param token
     * @param user
     * @param org
     * @param roleType
     * @return
     * @throws ApplicationException
     */
    public List<RegistrationFacility> getFacilities(URL endpoint, String token, RegistrationUser user, RegistrationOrganization  org, RegistrationRoleType roleType) {
        try {
            return getClient(endpoint, false, true).retrieveFacilities(token, user, org, roleType);
        } catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
            throw this.handleException(convertFault(fault), LOGGER);
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }
    }

    /**
     * Retrieve NAICS codes for a program
     * @param endpoint
     * @param token
     * @param programAcronym
     * @return
     * @throws ApplicationException
     */
    public List<RegistrationProgramFacilityNaicsCode> getNaicsCodes(URL endpoint, String token, String programAcronym) {
        try {
            return getClient(endpoint, false, true).retrieveNaicsCodesFromFrs(token, programAcronym);
        } catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
            throw this.handleException(convertFault(fault), LOGGER);
        } catch (Exception e) {
            throw this.handleException(e, LOGGER);
        }
    }

    /**
     * Convert Register Service exceptions into application exceptions
     * @param fault
     * @return
     */
    ApplicationException convertFault(net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
        if (fault.getFaultInfo() == null) {
            return new ApplicationException(ApplicationErrorCode.E_REMOTE_SERVICE_ERROR, fault.getMessage());
        } else {
            ApplicationErrorCode code;
            try {
                code = ApplicationErrorCode.valueOf(fault.getFaultInfo().getErrorCode().value());
            } catch (Exception e) {
                LOGGER.warn("Could not translate fault.", e);
                code = ApplicationErrorCode.E_REMOTE_SERVICE_ERROR;
            }
            return new ApplicationException(code, fault.getFaultInfo().getDescription());
        }
    }
}
