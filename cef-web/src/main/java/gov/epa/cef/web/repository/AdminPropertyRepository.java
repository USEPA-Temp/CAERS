package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.AdminProperty;

public interface AdminPropertyRepository extends CrudRepository<AdminProperty, String> {

    List<AdminProperty> findAll();

    List<AdminProperty> findAll(Sort sort);

    @Cacheable(value = CacheName.ConfigProperties)
    Optional<AdminProperty> findById(String id);

    @CachePut(value = CacheName.ConfigProperties)
    <S extends AdminProperty> S save(S entity);

    @CacheEvict(value = CacheName.ConfigProperties)
    void deleteById(String id);
}
