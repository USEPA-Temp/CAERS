package gov.epa.cef.web.repository;

import org.springframework.data.repository.CrudRepository;
import gov.epa.cef.web.domain.ProgramSystemCode;


public interface ProgramSystemCodeRepository extends CrudRepository<ProgramSystemCode, String> {
}
