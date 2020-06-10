package gov.epa.cef.web.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.AircraftEngineTypeCode;

import java.util.List;

import javax.persistence.QueryHint;

public interface AircraftEngineTypeCodeRepository extends CrudRepository<AircraftEngineTypeCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<AircraftEngineTypeCode> findAll(Sort sort);

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select aec from AircraftEngineTypeCode aec where aec.lastInventoryYear = null or aec.lastInventoryYear >= :year")
    List<AircraftEngineTypeCode> findAllCurrent(int year, Sort sort);

    List<AircraftEngineTypeCode> findByScc(String scc, Sort sort);

    @Query("select aec from AircraftEngineTypeCode aec where aec.scc = :scc and (aec.lastInventoryYear = null or aec.lastInventoryYear >= :year)")
    List<AircraftEngineTypeCode> findCurrentByScc(int year, String scc, Sort sort);
}
