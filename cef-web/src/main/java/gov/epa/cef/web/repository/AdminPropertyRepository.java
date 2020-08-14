package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.AdminProperty;

public interface AdminPropertyRepository extends CrudRepository<AdminProperty, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<AdminProperty> findAll();

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<AdminProperty> findAll(Sort sort);

}
