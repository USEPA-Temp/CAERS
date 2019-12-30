package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.CacheName; 
import gov.epa.cef.web.domain.ControlPollutant;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ControlPollutantRepository extends CrudRepository<ControlPollutant, Long>{

    /**
     * Retrieve Control Pollutants for a control
     * @param facilitySiteId
     * @return
     */
    List<ControlPollutant> findByControlId(Long controlId);

}

