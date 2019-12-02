package gov.epa.cef.web.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.UnitTypeCode;

public interface UnitTypeCodeRepository extends CrudRepository<UnitTypeCode, String> {
	
    @Override
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<UnitTypeCode> findAll();
}
