package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.ControlMeasureCode;

public interface ControlMeasureCodeRepository extends CrudRepository<ControlMeasureCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<ControlMeasureCode> findAll(Sort sort);

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select cmc from ControlMeasureCode cmc where cmc.lastInventoryYear = null or cmc.lastInventoryYear >= :year")
    List<ControlMeasureCode> findAllCurrent(int year, Sort sort);
}
