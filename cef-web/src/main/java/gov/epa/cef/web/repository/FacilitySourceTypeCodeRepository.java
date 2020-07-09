package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.FacilitySourceTypeCode;


public interface FacilitySourceTypeCodeRepository extends CrudRepository<FacilitySourceTypeCode, String> {
	
	@QueryHints({
    @QueryHint(name = "org.hibernate.cacheable", value = "true")})
	Iterable<FacilitySourceTypeCode> findAll(Sort sort);
	
	@QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select fstc from FacilitySourceTypeCode fstc where fstc.lastInventoryYear = null or fstc.lastInventoryYear >= :year")
    List<FacilitySourceTypeCode> findAllCurrent(@Param("year") int year, Sort sort);
}
