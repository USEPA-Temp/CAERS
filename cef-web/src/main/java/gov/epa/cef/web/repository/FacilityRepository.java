package gov.epa.cef.web.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.facility.Facility;

public interface FacilityRepository extends CrudRepository<Facility, Long> {

	List<Facility> findByState(String state);

}
