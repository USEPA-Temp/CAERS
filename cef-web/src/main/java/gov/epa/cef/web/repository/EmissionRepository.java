package gov.epa.cef.web.repository;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.Emission;

public interface EmissionRepository extends CrudRepository<Emission, Long> {

}
