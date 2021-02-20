package gov.epa.cef.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.FuelUseSccCode;

public interface FuelUseSccCodeRepository extends CrudRepository<FuelUseSccCode, Long>{
	
	@Query("select f from FuelUseSccCode f where f.sccCode.code = :code")
	Optional<FuelUseSccCode> findByScc(@Param("code") String code);
}
