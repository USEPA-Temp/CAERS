package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.SLTConfigProperty;

public interface SLTConfigRepository extends CrudRepository<SLTConfigProperty, Long> {

    List<SLTConfigProperty> findAll();

    List<SLTConfigProperty> findAll(Sort sort);

    Optional<SLTConfigProperty> findByNameAndAgencyCode(String name, String agencyCode);

    List<SLTConfigProperty> findByAgencyCode(String agencyCode);

}
