package gov.epa.cef.web.repository;

import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.ReleasePointTypeCode;

public interface ReleasePointTypeCodeRepository extends CrudRepository<ReleasePointTypeCode, String> {
    ReleasePointTypeCode findByCode(String code);
}
