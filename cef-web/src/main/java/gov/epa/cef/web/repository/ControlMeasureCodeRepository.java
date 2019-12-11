package gov.epa.cef.web.repository;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.ControlMeasureCode;

public interface ControlMeasureCodeRepository extends CrudRepository<ControlMeasureCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<ControlMeasureCode> findAll(Sort sort);
}
