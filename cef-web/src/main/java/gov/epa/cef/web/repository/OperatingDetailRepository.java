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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReportingPeriod;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OperatingDetailRepository extends CrudRepository<OperatingDetail, Long>, ProgramIdRetriever, ReportIdRetriever {

    @Cacheable(value = CacheName.OperatingDetailMasterIds)
    @Query("select mfr.id "
            + "from OperatingDetail od join od.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs "
            + "join fs.emissionsReport r join r.masterFacilityRecord mfr where od.id = :id")
    Optional<Long> retrieveMasterFacilityRecordIdById(@Param("id") Long id);

    /**
     * Retrieve Emissions Report id for an Operating Detail
     * @param id
     * @return Emissions Report id
     */
    @Cacheable(value = CacheName.OperatingDetailEmissionsReportIds)
    @Query("select r.id from OperatingDetail od join od.reportingPeriod rp join rp.emissionsProcess p join p.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport r where od.id = :id")
    Optional<Long> retrieveEmissionsReportById(@Param("id") Long id);
    
    /**
     * Retrieve a list of all operating details for a specific program system code and emissions reporting year
     * @param psc
     * @param emissionsReportYear
     * @return
     */
    @Query("select od from OperatingDetail od join od.reportingPeriod rp join rp.emissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport er where er.programSystemCode.code = :psc and er.year = :emissionsReportYear")
    List<OperatingDetail> findByPscAndEmissionsReportYear(String psc, Short emissionsReportYear);
}
