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
package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;
import gov.epa.cef.web.service.dto.MasterFacilityNAICSDto;

import java.util.List;

public interface MasterFacilityRecordService {

    /**
     * Find a master facility record by id
     * @param ID for a master facility record
     * @return
     */
    MasterFacilityRecordDto findById(Long id);


    /**
     * Find a master facility record by eisProgramId
     * @param eisProgramId
     * @return
     */
    MasterFacilityRecordDto findByEisProgramId(String eisProgramId);


    /**
     * Retrieve a list of master facility records based on PSC
     * @param programSystemCode
     * @return
     */
    List<MasterFacilityRecordDto> findByProgramSystemCode(String programSystemCode) ;


    /**
     * Convert a facility site record to a master facility record
     * @param facilitySiteDto
     * @return
     */
    MasterFacilityRecord transformFacilitySite(FacilitySiteDto fs) ;


    /**
     * Update an existing masterFacilityRecord
     * @param masterFacilityRecordDto
     * @return
     */
    MasterFacilityRecordDto update(MasterFacilityRecordDto dto);


    /**
     * Create a new masterFacilityRecord
     * @param masterFacilityRecordDto
     * @return
     */
    MasterFacilityRecordDto create(MasterFacilityRecordDto dto);

    
    /**
     * 
     * @param 
     * @return
     */
    List<MasterFacilityRecordDto> findByExample(MasterFacilityRecordDto criteria);


    /**
     * 
     * @param 
     * @return
     */
    List<CodeLookupDto> findDistinctProgramSystems();


    /**
     * 
     * @param agencyFacilityId
     * @param programSystemCode
     * @return
     */
    Boolean isDuplicateAgencyId(String agencyFacilityId, String programSystemCode);
    
    /**
     * Create Master Facility Record NAICS
     * @param dto
     */
    MasterFacilityNAICSDto createMasterFacilityNaics(MasterFacilityNAICSDto dto);
    
    /**
     * Update Master Facility Record NAICS
     * @param dto
     */
    MasterFacilityNAICSDto updateMasterFacilityNaics(MasterFacilityNAICSDto dto);
    
    /**
     * Delete Master Facility Record NAICS by id
     * @param mfNaicsId
     */
    void deleteMasterFacilityNaics(Long mfNaicsId);
    
    /**
     * Update master facility record with changes made in emission report
     * @param mfr
     * @param fs
     */
    void updateMasterFacilityRecord(MasterFacilityRecord mfr, FacilitySite fs);

}
