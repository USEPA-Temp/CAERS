package gov.epa.cef.web.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.OperatingStatusCode;

import java.util.List;

import javax.persistence.QueryHint;

public interface OperatingStatusCodeRepository extends CrudRepository<OperatingStatusCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<OperatingStatusCode> findAll(Sort sort);

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select osc from OperatingStatusCode osc where osc.facilityStatus = true")
    List<OperatingStatusCode> findAllFacilityStatuses(Sort sort);

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select osc from OperatingStatusCode osc where osc.subFacilityStatus = true")
    List<OperatingStatusCode> findAllSubFacilityStatuses(Sort sort);
}
