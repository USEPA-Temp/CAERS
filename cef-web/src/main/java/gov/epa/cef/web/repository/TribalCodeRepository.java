package gov.epa.cef.web.repository;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.TribalCode;


public interface TribalCodeRepository extends CrudRepository<TribalCode, String> {

    TribalCode findByCode(String code);
}
