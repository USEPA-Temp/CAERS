package gov.epa.cef.web.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.ReleasePointTypeCode;

public interface ReleasePointTypeCodeRepository extends CrudRepository<ReleasePointTypeCode, String> {
	
	@QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<ReleasePointTypeCode> findAll(Sort sort);
	
	@QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select rptc from ReleasePointTypeCode rptc where rptc.lastInventoryYear = null or rptc.lastInventoryYear >= :year")
    List<ReleasePointTypeCode> findAllCurrent(@Param("year") int year, Sort sort);
	
}
