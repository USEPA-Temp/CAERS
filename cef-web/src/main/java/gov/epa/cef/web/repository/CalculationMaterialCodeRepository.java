package gov.epa.cef.web.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.CalculationMaterialCode;

import java.util.List;

import javax.persistence.QueryHint;

public interface CalculationMaterialCodeRepository extends CrudRepository<CalculationMaterialCode, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<CalculationMaterialCode> findAll(Sort sort);
    
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select fmc from CalculationMaterialCode fmc where fmc.fuelUseMaterial = true")
    List<CalculationMaterialCode> findAllFuelUseMaterial(Sort sort);
}
