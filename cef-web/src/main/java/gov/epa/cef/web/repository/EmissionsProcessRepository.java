package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EmissionsProcess;

public interface EmissionsProcessRepository extends CrudRepository<EmissionsProcess, Long> {


    List<EmissionsProcess> findByReleasePointApptsReleasePointId(Long releasePointId);

}
