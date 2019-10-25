package gov.epa.cef.web.repository;

import gov.epa.cef.web.domain.UnitMeasureCode;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;

public interface UnitMeasureCodeRepository extends CrudRepository<UnitMeasureCode, String> {

    @Override
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<UnitMeasureCode> findAll();

    UnitMeasureCode findByCode(String code);
}
