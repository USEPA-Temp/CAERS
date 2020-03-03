package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.FipsCounty;

public interface FipsCountyRepository extends CrudRepository<FipsCounty, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<FipsCounty> findAll(Sort sort);

    List<FipsCounty> findByFipsStateCodeCode(String stateCode, Sort sort);

    Optional<FipsCounty> findByFipsStateCodeCodeAndName(String stateCode, String name);

    Optional<FipsCounty> findByFipsStateCodeCodeAndCountyCode(String stateCode, String countyCode);

}
