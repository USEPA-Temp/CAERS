package gov.epa.cef.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.ReportAttachment;

public interface ReportAttachmentRepository extends CrudRepository<ReportAttachment, Long>, ProgramIdRetriever {

	/**
    *
    * @param id
    * @return EIS Program ID
    */
   @Query("select r.eisProgramId from ReportAttachment ra join ra.emissionsReport r where ra.id = :id")
   Optional<String> retrieveEisProgramIdById(@Param("id") Long id);

}