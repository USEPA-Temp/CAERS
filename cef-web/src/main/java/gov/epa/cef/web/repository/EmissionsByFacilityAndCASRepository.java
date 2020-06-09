package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EmissionsByFacilityAndCAS;

public interface EmissionsByFacilityAndCASRepository extends CrudRepository<EmissionsByFacilityAndCAS, Long> {

    List<EmissionsByFacilityAndCAS> findByFrsFacilityIdAndPollutantCasIdAndYear(String facilitySiteId, String pollutantCasId, Short year);

    List<EmissionsByFacilityAndCAS> findByTrifidAndPollutantCasIdAndYear(String trifid, String pollutantCasId, Short year);

}