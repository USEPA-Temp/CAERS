package gov.epa.cef.web.repository;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EisTriXref;

public interface EisTriXrefRepository extends CrudRepository<EisTriXref, Long> {
	
	/**
    *
    * @param trifid
    * @return
    */
   List<EisTriXref> findByTrifid(@NotBlank String trifid);

}
