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
import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.FacilitySiteContact;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacilitySiteContactRepository extends CrudRepository<FacilitySiteContact, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @return
     */
    List<FacilitySiteContact> findByFacilitySiteId(Long facilitySiteId);
    
    /**
     * Retrieve Facility Site Contacts for a facility site
     * @param facilitySiteId
     * @param contactTypeCode
     * @return
     */
    List<FacilitySiteContact> findByFacilitySiteIdAndType(Long facilitySiteId, ContactTypeCode contactTypeCode);

    @Cacheable(value = CacheName.FacilitySiteContactMasterIds)
    @Query("select mfr.id from FacilitySiteContact c join c.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where c.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Facility Site Contact
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.FacilitySiteContactEmissionsReportIds)
    @Query("select r.id from FacilitySiteContact c join c.facilitySite fs join fs.emissionsReport r where c.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
    
    /**
     * Retrieve a list of all facility site contacts for a specific program system code and emissions reporting year
     * @param psc Program System Code
     * @param emissionsReportYear
     * @return
     */
    @Query("select fsc from FacilitySiteContact fsc join fsc.facilitySite fs join fs.emissionsReport er where er.programSystemCode.code = :psc and er.year = :emissionsReportYear")
    List<FacilitySiteContact> findByPscAndEmissionsReportYear(String psc, Short emissionsReportYear);
}
