package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.FipsCounty;

public interface FipsCountyRepository extends CrudRepository<FipsCounty, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<FipsCounty> findAll(Sort sort);
    
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select fc from FipsCounty fc where fc.lastInventoryYear = null or fc.lastInventoryYear >= :year")
    List<FipsCounty> findAllCurrent(@Param("year") int year, Sort sort);
    
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select fc from FipsCounty fc where fc.fipsStateCode.code = :stateCode and (fc.lastInventoryYear = null or fc.lastInventoryYear >= :year)")
    List<FipsCounty> findCurrentByFipsStateCodeCode(@Param("stateCode") String stateCode, @Param("year") int year, Sort sort);

    List<FipsCounty> findByFipsStateCodeCode(String stateCode, Sort sort);

    Optional<FipsCounty> findByFipsStateCodeCodeAndName(String stateCode, String name);

    Optional<FipsCounty> findByFipsStateCodeCodeAndCountyCode(String stateCode, String countyCode);

}
