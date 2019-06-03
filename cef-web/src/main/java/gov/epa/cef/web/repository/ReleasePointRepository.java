package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.ReleasePoint;

public interface ReleasePointRepository extends CrudRepository<ReleasePoint, Long> {

    /**
     * Retrieve Release Points for a facility
     * @param facilityId
     * @return
     */
    List<ReleasePoint> findByFacilityId(Long facilityId);

}
