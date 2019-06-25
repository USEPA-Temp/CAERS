package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.Control;

public interface ControlRepository extends CrudRepository<Control, Long> {

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    List<Control> findByFacilitySiteId(Long facilitySiteId);

    /**
     * Retrieve Controls for an emissions process
     * @param processId
     * @return
     */
    List<Control> findByAssignmentsEmissionsProcessId(Long processId);

    /**
     * Retrieve Controls for an emissions unit
     * @param unitId
     * @return
     */
    List<Control> findByAssignmentsEmissionsUnitId(Long unitId);

    /**
     * Retrieve Controls for a release point
     * @param pointId
     * @return
     */
    List<Control> findByAssignmentsReleasePointId(Long pointId);

}
