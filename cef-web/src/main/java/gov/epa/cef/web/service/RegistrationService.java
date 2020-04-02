package gov.epa.cef.web.service;

import gov.epa.cef.web.exception.ApplicationException;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;

import java.util.List;

public interface RegistrationService {

    /**
     * Retrieve CDX facilities associated with the user
     *
     * @param userRoleId
     * @return
     * @throws ApplicationException
     */
    List<ProgramFacility> retrieveFacilities(Long userRoleId);

    /**
     * Retrieve CDX facilities by program ids
     *
     * @param programIds
     * @return
     * @throws ApplicationException
     */
    List<ProgramFacility> retrieveFacilityByProgramIds(List<String> programIds);

    /**
     * Retrieve CDX facility by id
     *
     * @param programId
     * @return
     */
    ProgramFacility retrieveFacilityByProgramId(String programId);
}
