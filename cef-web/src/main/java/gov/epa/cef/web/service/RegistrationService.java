package gov.epa.cef.web.service;

import java.net.URL;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.soap.RegisterFacilityClient;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

@Service("registrationService")
public class RegistrationService {

  private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
  
  private static final String CDX_REGISTER_FACILITY_ENDPOINT = "https://devngn.epacdxnode.net/cdx-register-II/services/RegisterProgramFacilityService";

  @Autowired
  private RegisterFacilityClient registerFacilityClient;

  public List<ProgramFacility> retrieveFacilities(Long userRoleId) throws ApplicationException {
    try {

      URL registerFacilityServiceUrl = new URL(CDX_REGISTER_FACILITY_ENDPOINT);
      String token = authenticate("caer-cef-admin@epa.gov", "CDXcaercef01");
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

  private String authenticate(String user, String password) {
    try {
      URL registerServiceUrl = new URL(CDX_REGISTER_FACILITY_ENDPOINT);
      return registerFacilityClient.authenticate(registerServiceUrl, user, password);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw ApplicationException.asApplicationException(e);
    }
  }

}
