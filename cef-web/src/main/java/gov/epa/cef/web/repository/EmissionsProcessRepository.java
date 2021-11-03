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
import gov.epa.cef.web.domain.EmissionsProcess;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmissionsProcessRepository extends CrudRepository<EmissionsProcess, Long>, ProgramIdRetriever, ReportIdRetriever {

    List<EmissionsProcess> findByReleasePointApptsReleasePointIdOrderByEmissionsProcessIdentifier(Long releasePointId);

    List<EmissionsProcess> findByEmissionsUnitIdOrderByEmissionsProcessIdentifier(Long emissionsUnitId);

    /**
     * Find Emissions Process with the specified identifier, parent identifier, master facility record id, and year
     * @param identifier
     * @param parentIdentifier
     * @param mfrId
     * @param year
     * @return
     */
    @Query("select ep from EmissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr "
            + "where ep.emissionsProcessIdentifier = :identifier and eu.unitIdentifier = :parentIdentifier and mfr.id = :mfrId and r.year = :year")
    List<EmissionsProcess> retrieveByIdentifierParentFacilityYear(@Param("identifier") String identifier, @Param("parentIdentifier") String parentIdentifier, 
                                                                      @Param("mfrId") Long mfrId, @Param("year") Short year);
    
    /**
     * Find Emissions Process with the specified identifier, parent identifier, master facility record id, and year
     * @param parentIdentifier
     * @param mfrId
     * @param year
     * @return
     */
    @Query("select ep from EmissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr "
            + "where eu.unitIdentifier = :parentIdentifier and mfr.id = :mfrId and r.year = :year")
    List<EmissionsProcess> retrieveByParentFacilityYear(@Param("parentIdentifier") String parentIdentifier,
    															  @Param("mfrId") Long mfrId, @Param("year") Short year);

    @Cacheable(value = CacheName.ProcessMasterIds)
    @Query("select mfr.id from EmissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where p.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for an Emissions Process
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ProcessEmissionsReportIds)
    @Query("select r.id from EmissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where p.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
