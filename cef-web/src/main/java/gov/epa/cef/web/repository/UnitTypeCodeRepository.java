package gov.epa.cef.web.repository;

import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.UnitTypeCode;

public interface UnitTypeCodeRepository extends CrudRepository<UnitTypeCode, String> {
    UnitTypeCode findByCode(String code);
}
