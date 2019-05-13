package gov.epa.cef.web.service.impl;

import java.net.URL;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.RegistrationService;
import gov.epa.cef.web.soap.RegisterFacilityClient;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

/**
 * Service for invoking Register Webservice methods
 * @author tfesperm
 *
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    @Autowired
    private RegisterFacilityClient registerFacilityClient;

    @Autowired
    private CefConfig cefConfig;

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.RegisterService#retrieveFacilities(java.lang.Long)
     */
    @Override
    public List<ProgramFacility> retrieveFacilities(Long userRoleId) {
        try {

            URL registerFacilityServiceUrl = new URL(cefConfig.getCdxConfig().getRegisterProgramFacilityServiceEndpoint());
            String token = authenticate(cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorUser(),
                    cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorPassword());
            List<ProgramFacility> facilities = registerFacilityClient
                    .getFacilitiesByUserRoleId(registerFacilityServiceUrl, token, userRoleId);
            if (CollectionUtils.isNotEmpty(facilities)) {
                for (ProgramFacility facility : facilities) {
                    LOGGER.debug("Facility Name: " + facility.getFacilityName());
                }
            } else {
                LOGGER.debug("No Facility found for user role: " + userRoleId);
            }
            return facilities;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw ApplicationException.asApplicationException(e);
        }
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.RegisterService#retrieveFacilityByProgramId(java.lang.String)
     */
    @Override
    public ProgramFacility retrieveFacilityByProgramId(String programId) {
        try {

            URL registerFacilityServiceUrl = new URL(cefConfig.getCdxConfig().getRegisterProgramFacilityServiceEndpoint());
            String token = authenticate(cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorUser(),
                    cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorPassword());
            List<ProgramFacility> facilities = registerFacilityClient
                    .getFacilityByProgramId(registerFacilityServiceUrl, token, programId);
            if (CollectionUtils.isNotEmpty(facilities)) {
                for (ProgramFacility facility : facilities) {
                    LOGGER.debug("Facility Name: " + facility.getFacilityName());
                }
            } else {
                LOGGER.debug("No Facility found for program : " + programId);
            }
            return facilities.stream().findFirst().orElse(null);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw ApplicationException.asApplicationException(e);
        }
    }

    /**
     * Create NAAS token for authenticating into CDX Register services
     * @param user
     * @param password
     * @return
     */
    private String authenticate(String user, String password) {
        try {
            URL registerServiceUrl = new URL(cefConfig.getCdxConfig().getRegisterProgramFacilityServiceEndpoint());
            return registerFacilityClient.authenticate(registerServiceUrl, user, password);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw ApplicationException.asApplicationException(e);
        }
    }

}
