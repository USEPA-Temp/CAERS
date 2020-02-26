package gov.epa.cef.web.repository;

import gov.epa.cef.web.domain.UnitMeasureCode;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import javax.persistence.QueryHint;

public interface UnitMeasureCodeRepository extends CrudRepository<UnitMeasureCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<UnitMeasureCode> findAll(Sort sort);

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select uom from UnitMeasureCode uom where uom.legacy = false")
    List<UnitMeasureCode> findAllCurrent(Sort sort);
}
