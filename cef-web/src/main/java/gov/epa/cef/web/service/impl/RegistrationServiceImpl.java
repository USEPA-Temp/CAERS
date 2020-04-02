package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.client.soap.RegisterFacilityClient;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.service.RegistrationService;
import net.exchangenetwork.wsdl.register.program_facility._1.ProgramFacility;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Service for invoking Register Webservice methods
 *
 * @author tfesperm
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final RegisterFacilityClient registerFacilityClient;

    /**
     * Instantiates a new Registration service.
     *
     * @param registerFacilityClient the register facility client
     */
    @Autowired
    RegistrationServiceImpl(RegisterFacilityClient registerFacilityClient) {

        this.registerFacilityClient = registerFacilityClient;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.RegisterService#retrieveFacilities(java.lang.Long)
     */
    @Override
    public List<ProgramFacility> retrieveFacilities(Long userRoleId) {

        try {

            List<ProgramFacility> facilities = registerFacilityClient.getFacilitiesByUserRoleId(userRoleId);
            if (CollectionUtils.isNotEmpty(facilities)) {

                for (ProgramFacility facility : facilities) {
                    logger.debug("Facility Name: {}", facility.getFacilityName());
                }
            } else {
                logger.debug("No Facility found for user role: {}", userRoleId);
            }

            return facilities == null ? Collections.emptyList() : facilities;

        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            throw ApplicationException.asApplicationException(e);
        }
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.RegisterService#retrieveFacilityByProgramId(java.lang.String)
     */
    @Override
    public ProgramFacility retrieveFacilityByProgramId(String programId) {

        return retrieveFacilityByProgramIds(Collections.singletonList(programId)).stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<ProgramFacility> retrieveFacilityByProgramIds(List<String> programIds) {

        try {

            List<ProgramFacility> facilities = this.registerFacilityClient.getFacilityByProgramIds(programIds);
            if (CollectionUtils.isNotEmpty(facilities)) {

                for (ProgramFacility facility : facilities) {
                    logger.debug("Facility Name: {}", facility.getFacilityName());
                }

            } else {

                logger.debug("No Facilities found for program(s) : {}", String.join(", ", programIds));
            }

            return facilities == null ? Collections.emptyList() : facilities;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.asApplicationException(e);
        }
    }
}
