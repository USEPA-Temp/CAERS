package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EmissionsUnit;

public interface EmissionsUnitRepository extends CrudRepository<EmissionsUnit, Long> {

    /**
     * Retrieve Emissions Units for a facility
     * @param facilityId
     * @return
     */
    List<EmissionsUnit> findByFacilityId(Long facilityId);

}
