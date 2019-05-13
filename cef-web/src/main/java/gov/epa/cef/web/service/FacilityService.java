package gov.epa.cef.web.service;

import java.util.List;
import gov.epa.cef.web.domain.facility.Facility;

public interface FacilityService {

	/**
	 * Find common form facilities for a given state
	 * @param state Two-character state code
	 * @return
	 */	
	List<Facility> findByState(String state);

}