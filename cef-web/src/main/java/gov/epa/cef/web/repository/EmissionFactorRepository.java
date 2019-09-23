package gov.epa.cef.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.epa.cef.web.domain.EmissionFactor;

public interface EmissionFactorRepository extends JpaRepository<EmissionFactor, Long> {

}
