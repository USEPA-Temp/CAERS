package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.NaicsCode;

public interface NaicsCodeRepository extends CrudRepository<NaicsCode, Integer> {

	@QueryHints({
    @QueryHint(name = "org.hibernate.cacheable", value = "true")})
	Iterable<NaicsCode> findAll(Sort sort);

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select nc from NaicsCode nc where nc.lastInventoryYear = null or nc.lastInventoryYear >= :year")
    List<NaicsCode> findAllCurrent(int year, Sort sort);
}
