package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.exception.ApplicationException;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

public interface RegistrationService {

  /**
   * Retrieve CDX facilities associated with the user
   * @param userRoleId
   * @return
   * @throws ApplicationException
   */
  List<ProgramFacility> retrieveFacilities(Long userRoleId) throws ApplicationException;

}