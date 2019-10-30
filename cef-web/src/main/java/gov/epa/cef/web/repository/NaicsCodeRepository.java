package gov.epa.cef.web.repository;

import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.NaicsCode;

public interface NaicsCodeRepository extends CrudRepository<NaicsCode, Integer> {
}
