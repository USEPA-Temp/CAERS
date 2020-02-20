package gov.epa.cef.web.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.AircraftEngineTypeCode;

import java.util.List;

import javax.persistence.QueryHint;

public interface AircraftEngineTypeCodeRepository extends CrudRepository<AircraftEngineTypeCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<AircraftEngineTypeCode> findAll(Sort sort);
    
    List<AircraftEngineTypeCode> findByScc(String scc, Sort sort);
}
