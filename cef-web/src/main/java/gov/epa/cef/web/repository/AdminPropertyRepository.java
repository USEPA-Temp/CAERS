package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.AdminProperty;

public interface AdminPropertyRepository extends CrudRepository<AdminProperty, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<AdminProperty> findAll();

    List<AdminProperty> findAll(Sort sort);

    Optional<AdminProperty> findById(String id);

    <S extends AdminProperty> S save(S entity);

    void deleteById(String id);
}
