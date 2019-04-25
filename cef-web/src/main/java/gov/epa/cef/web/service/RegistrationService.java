package gov.epa.cef.web.service;

import java.net.URL;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.soap.RegisterFacilityClient;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

/**
 * Service for invoking Register Webservice methods
 * @author tfesperm
 *
 */
@Service("registrationService")
public class RegistrationService {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

  @Autowired
  private RegisterFacilityClient registerFacilityClient;
  
  @Autowired
  private CefConfig cefConfig;

  /**
   * Retrieve CDX facilities associated with the user
   * @param userRoleId
   * @return
   * @throws ApplicationException
   */
  public List<ProgramFacility> retrieveFacilities(Long userRoleId) throws ApplicationException {
    try {

      URL registerFacilityServiceUrl = new URL(cefConfig.getCdxConfig().getRegisterProgramFacilityServiceEndpoint());
      String token = authenticate(cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorUser(),
          cefConfig.getCdxConfig().getRegisterProgramFacilityOperatorPassword());
      List<ProgramFacility> facilities = registerFacilityClient
          .getFacilitiesByUserRoleId(registerFacilityServiceUrl, token, userRoleId);
      if (CollectionUtils.isNotEmpty(facilities)) {
        for (ProgramFacility facility : facilities) {
          logger.info("Facility Name: " + facility.getFacilityName());
        }
      } else {
        logger.info("No Facility found for user role: " + userRoleId);
      }
      return facilities;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
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
      logger.error(e.getMessage(), e);
      throw ApplicationException.asApplicationException(e);
    }
  }

}
