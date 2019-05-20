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
  List<ProgramFacility> retrieveFacilities(Long userRoleId);

  /**
   * Retrieve CDX facility by id
   * @param programId
   * @return
   */
  ProgramFacility retrieveFacilityByProgramId(String programId);

    /**
     * Returns current user CDX NAAS Token
     * 
     * @return naasToken
     */
    String getCurrentUserNaasToken();

}