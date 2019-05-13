package gov.epa.cef.web.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.facility.Facility;

public interface FacilityRepository extends CrudRepository<Facility, Long> {

	/***
	 * Retrieve the common form facilities based on a given state code
	 * @param 2 character state code
	 * @return
	 */
	List<Facility> findByState(String state);

}
