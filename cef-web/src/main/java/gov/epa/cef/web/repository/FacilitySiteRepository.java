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

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.FacilitySite;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilitySiteRepository extends CrudRepository<FacilitySite, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve facilities by emissions report
     * @param emissionsReportId
     * @return
     */
    List<FacilitySite> findByEmissionsReportId(Long emissionsReportId);

    /***
     * Retrieve the common form facilities based on a given state code
     * @param stateCode : 2 character state code
     * @return
     */
    List<FacilitySite> findByStateCode(String stateCode);

    @Cacheable(value = CacheName.FacilityMasterIds)
    @Query("select mfr.id from FacilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where fs.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Facility Site
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.FacilityEmissionsReportIds)
    @Query("select r.id from FacilitySite fs join fs.emissionsReport r where fs.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
