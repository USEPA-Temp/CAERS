package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EmissionFormulaVariableCode;

public interface EmissionFormulaVariableCodeRepository extends CrudRepository<EmissionFormulaVariableCode, String> {

    // Explicitly defined query is required to use sql functions in sort
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query(value = "select c from EmissionFormulaVariableCode c")
    List<EmissionFormulaVariableCode> findAll(Sort sort);
}
