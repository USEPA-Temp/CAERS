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

}
