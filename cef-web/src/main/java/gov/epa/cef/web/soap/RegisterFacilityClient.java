package gov.epa.cef.web.soap;

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
public class RegisterFacilityClient extends AbstractClient {

	private static final Logger logger = LoggerFactory.getLogger(RegisterFacilityClient.class);

	protected RegisterProgramFacilityService getClient(URL endpoint, boolean mtom, boolean chunking) {
		return (RegisterProgramFacilityService) this.getClient(endpoint.toString(),
				RegisterProgramFacilityService.class, mtom, chunking);
	}

	public String authenticate(URL endpoint, String userId, String password) throws ApplicationException {
		try {
			return getClient(endpoint, false, true).authenticate(userId, password, domain, authMethod);
		} catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
			throw this.handleException(convertFault(fault), logger);
		} catch (Exception e) {
			throw this.handleException(e, logger);
		}
	}

	public List<ProgramFacility> getFacilitiesByUserRoleId(URL endpoint, String token, Long userRoleId)
			throws ApplicationException {
		try {
			return getClient(endpoint, false, true).retrieveFacilitiesByUserRoleId(token, userRoleId);
		} catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
			throw this.handleException(convertFault(fault), logger);
		} catch (Exception e) {
			throw this.handleException(e, logger);
		}
	}
	
	public List<ProgramFacility> getFacilityByProgramId(URL endpoint, String token, String siteProgramId)
			throws ApplicationException {
		try {
			return getClient(endpoint, false, true).retrieveProgramFacilitiesByProgramIds(token, Arrays.asList(new String[]{siteProgramId}));
		} catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
			throw this.handleException(convertFault(fault), logger);
		} catch (Exception e) {
			throw this.handleException(e, logger);
		}
	}
	
	public List<RegistrationFacility> getFacilities(URL endpoint, String token, RegistrationUser user, RegistrationOrganization  org, RegistrationRoleType roleType)
			throws ApplicationException {
		try {
			return getClient(endpoint, false, true).retrieveFacilities(token, user, org, roleType);
		} catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
			throw this.handleException(convertFault(fault), logger);
		} catch (Exception e) {
			throw this.handleException(e, logger);
		}
	}
	
	public List<RegistrationProgramFacilityNaicsCode> getNaicsCodes(URL endpoint, String token, String programAccronym)
			throws ApplicationException {
		try {
			return getClient(endpoint, false, true).retrieveNaicsCodesFromFrs(token, programAccronym);
		} catch (net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
			throw this.handleException(convertFault(fault), logger);
		} catch (Exception e) {
			throw this.handleException(e, logger);
		}
	}

	ApplicationException convertFault(net.exchangenetwork.wsdl.register.program_facility._1.RegisterException fault) {
		if (fault.getFaultInfo() == null) {
			return new ApplicationException(ApplicationErrorCode.E_RemoteServiceError, fault.getMessage());
		} else {
			ApplicationErrorCode code;
			try {
				code = ApplicationErrorCode.valueOf(fault.getFaultInfo().getErrorCode().value());
			} catch (Exception e) {
				logger.warn("Could not translate fault.");
				code = ApplicationErrorCode.E_RemoteServiceError;
			}
			return new ApplicationException(code, fault.getFaultInfo().getDescription());
		}
	}
}
