/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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