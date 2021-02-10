package gov.epa.cef.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.ReportAttachment;

public interface ReportAttachmentRepository extends CrudRepository<ReportAttachment, Long>, ProgramIdRetriever, ReportIdRetriever {

	/**
   * Find all attachments with the specified report id
   * @param reportId
   * @return
   */
	@Query("select ra from ReportAttachment ra join ra.emissionsReport r where r.id = :reportId")	
	List<ReportAttachment> findAllByReportId(@Param("reportId") Long reportId);

   @Query("select mfr.id from ReportAttachment ra join ra.emissionsReport r join r.masterFacilityRecord mfr where ra.id = :id")
   Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);
   
   
   /**
   * Retrieve Emissions Report id for a report attachment
   * @param id
   * @return Emissions Report id
   */
  @Cacheable(value = CacheName.ReportAttachmentReportIds)
  @Query("select r.id from ReportAttachment ra join ra.emissionsReport r where ra.id = :id")
  Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);

}