package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.Pollutant;

public interface PollutantRepository extends CrudRepository<Pollutant, String> {

    @Override
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<Pollutant> findAll();
    
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select p from Pollutant p where p.lastInventoryYear = null or p.lastInventoryYear >= :year")
    List<Pollutant> findAllCurrent(int year, Sort sort);
}
