package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import gov.epa.cef.web.domain.EmissionFactorVariableCode;

public interface EmissionFactorVariableCodeRepository extends JpaRepository<EmissionFactorVariableCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query(value = "select c from EmissionFactorVariableCode c")
    List<EmissionFactorVariableCode> findAll(Sort sort);
}
