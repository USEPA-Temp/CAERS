package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.ControlAssignment;

public interface ControlAssignmentRepository extends CrudRepository<ControlAssignment, Long> {

	/***
	 * Retrieves all control assignments that belong to the parent path (e.g. Path B has several control assignment records; one of those records has Path A as a child; this method 
	 * returns all Path B control assignment records for Path A)
	 * @param childPathId
	 * @return
	 */
	List<ControlAssignment> findByControlPathChildId(Long controlPathChildId);

}
