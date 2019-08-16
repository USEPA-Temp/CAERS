package gov.epa.cef.web.repository;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.ReportingPeriodCode;

import javax.persistence.QueryHint;

public interface ReportingPeriodCodeRepository extends CrudRepository<ReportingPeriodCode, String> {

    @Override
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<ReportingPeriodCode> findAll();
}
