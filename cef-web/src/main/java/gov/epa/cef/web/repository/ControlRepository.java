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
import gov.epa.cef.web.domain.Control;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ControlRepository extends CrudRepository<Control, Long>, ProgramIdRetriever, ReportIdRetriever {

    /**
     * Retrieve Controls for a facility site
     * @param facilitySiteId
     * @return
     */
    List<Control> findByFacilitySiteIdOrderByIdentifier(Long facilitySiteId);

    /**
     * Find Controls with the specified identifier, master facility record id, and year
     * @param identifier
     * @param mfrId
     * @param year
     * @return
     */
    @Query("select c from Control c join c.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where c.identifier = :identifier and mfr.id = :mfrId and r.year = :year")
    List<Control> retrieveByIdentifierFacilityYear(@Param("identifier") String identifier, @Param("mfrId") Long mfrId, @Param("year") Short year);
    
    /**
     * Find Controls with the specified identifier, master facility record id, and year
     * @param mfrId
     * @param year
     * @return
     */
    @Query("select c from Control c join c.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where mfr.id = :mfrId and r.year = :year")
    List<Control> retrieveByFacilityYear(@Param("mfrId") Long mfrId, @Param("year") Short year);

    @Cacheable(value = CacheName.ControlMasterIds)
    @Query("select mfr.id from Control c join c.facilitySite fs join fs.emissionsReport r join r.masterFacilityRecord mfr where c.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for a Control
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.ControlEmissionsReportIds)
    @Query("select r.id from Control c join c.facilitySite fs join fs.emissionsReport r where c.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
}
