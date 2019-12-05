package gov.epa.cef.web.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.CalculationMaterialCode;

import javax.persistence.QueryHint;

public interface CalculationMaterialCodeRepository extends CrudRepository<CalculationMaterialCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<CalculationMaterialCode> findAll(Sort sort);
}
