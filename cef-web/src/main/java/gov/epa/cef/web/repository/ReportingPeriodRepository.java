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
import gov.epa.cef.web.domain.ReportingPeriod;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportingPeriodRepository extends CrudRepository<ReportingPeriod, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Reporting Periods for an emissions process
     * @param processId
     * @return
     */
    List<ReportingPeriod> findByEmissionsProcessId(Long processId);

    /**
     * Return all Reporting Periods for a facility site
     * @param facilitySiteId
     * @return
     */
    @Query("select rp from ReportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs where fs.id = :facilitySiteId")
    List<ReportingPeriod> findByFacilitySiteId(Long facilitySiteId);

    /**
     * Find Reporting Period with the specified type, process identifier, unit identifier, master facility record id, and year.
     * This combination should be unique and can be used to find a specific Reporting Period for a specific year
     * @param typeCode
     * @param processIdentifier
     * @param unitIdentifier
     * @param mfrId
     * @param year
     * @return
     */
    @Query("select rp from ReportingPeriod rp join rp.emissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr "
            + "where rp.reportingPeriodTypeCode.code = :typeCode and ep.emissionsProcessIdentifier = :processIdentifier and eu.unitIdentifier = :unitIdentifier "
            + "and mfr.id = :mfrId and r.year = :year")
    List<ReportingPeriod> retrieveByTypeIdentifierParentFacilityYear(@Param("typeCode") String typeCode,
            @Param("processIdentifier") String processIdentifier, @Param("unitIdentifier") String unitIdentifier,
            @Param("mfrId") Long mfrId, @Param("year") Short year);

    @Cacheable(value = CacheName.ReportingPeriodMasterIds)
    @Query("select mfr.id from ReportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where rp.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Reporting Period
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ReportingPeriodEmissionsReportIds)
    @Query("select r.id from ReportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where rp.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
