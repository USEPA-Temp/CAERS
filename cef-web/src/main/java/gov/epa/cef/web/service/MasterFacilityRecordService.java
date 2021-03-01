package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;

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
}
