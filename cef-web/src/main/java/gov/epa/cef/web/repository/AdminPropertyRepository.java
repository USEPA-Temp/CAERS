package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.AdminProperty;

public interface AdminPropertyRepository extends CrudRepository<AdminProperty, String> {

    List<AdminProperty> findAll();

    List<AdminProperty> findAll(Sort sort);

}
