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
import gov.epa.cef.web.domain.ControlAssignment;

public interface ControlAssignmentRepository extends CrudRepository<ControlAssignment, Long>, ProgramIdRetriever,ReportIdRetriever  {

	/***
	 * Retrieves all control assignments that belong to the parent path (e.g. Path B has several control assignment records; one of those records has Path A as a child; this method 
	 * returns all Path B control assignment records for Path A)
	 * @param childPathId
	 * @return
	 */
	List<ControlAssignment> findByControlPathChildId(Long controlPathChildId);

	List<ControlAssignment> findByControlPathIdOrderBySequenceNumber(Long controlPathId);
	
	List<ControlAssignment> findBySequenceNumber(Integer sequenceNumber);

	
    /**
     * Retrieve Emissions Report id for a Control Assignment
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ControlAssignmentsEmissionsReportIds)
    @Query("select r.id from ControlAssignment ca join ca.controlPath cp join cp.facilitySite fs join fs.emissionsReport r where ca.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
    
    @Cacheable(value = CacheName.ControlAssignmentsMasterIds)
    @Query("select mfr.id from ControlAssignment ca join ca.controlPath cp join cp.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where ca.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);
    
}
