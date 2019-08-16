package gov.epa.cef.web.repository;

import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;

public interface CalculationParameterTypeCodeRepository extends CrudRepository<CalculationParameterTypeCode, String> {

    @Override
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<CalculationParameterTypeCode> findAll();
}
