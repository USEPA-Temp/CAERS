package gov.epa.cef.web.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EmissionsOperatingTypeCode;

public interface EmissionsOperatingTypeCodeRepository extends CrudRepository<EmissionsOperatingTypeCode, String> {

    @Override
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<EmissionsOperatingTypeCode> findAll();
}
