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
package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.exception.AppValidationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.EmissionsUnitRepository;
import gov.epa.cef.web.service.EmissionsUnitService;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.service.mapper.EmissionsUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmissionsUnitServiceImpl implements EmissionsUnitService {

    @Autowired
    private EmissionsReportRepository reportRepo;

    @Autowired
    private EmissionsUnitRepository unitRepo;

    @Autowired
    private EmissionsUnitMapper emissionsUnitMapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;

    @Autowired
    private BulkUploadMapper bulkUploadMapper;

    /**
     * Retrieve Emissions Unit by its id
     * @param unitId
     * @return
     */
    public EmissionsUnitDto retrieveUnitById(Long unitId) {
        EmissionsUnit emissionsUnit= unitRepo
            .findById(unitId)
            .orElse(null);
        EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(emissionsUnit);
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.EmissionsUnitService#retrieveEmissionUnitsForFacility(java.lang.Long)
     */
    @Override
    public List<EmissionsUnitDto> retrieveEmissionUnitsForFacility(Long facilitySiteId) {
        List<EmissionsUnit> emissionUnits= unitRepo.findByFacilitySiteIdOrderByUnitIdentifier(facilitySiteId);
        return emissionsUnitMapper.emissionsUnitsToEmissionUnitsDtos(emissionUnits);
    }

    /**
     * Retrieve versions of this unit from the last year reported
     * @param unitId
     * @return
     */
    public EmissionsUnitDto retrievePreviousById(Long unitId) {
        EmissionsUnit emissionsUnit= unitRepo
                .findById(unitId)
                .orElse(null);

        Long mfrId = unitRepo.retrieveMasterFacilityRecordIdById(unitId).orElse(null);

        EmissionsUnit result = this.findPrevious(mfrId, emissionsUnit.getFacilitySite().getEmissionsReport().getYear(), emissionsUnit.getUnitIdentifier())
                .stream()
                .findFirst()
                .orElse(null);
        return emissionsUnitMapper.emissionsUnitToDto(result);
    }

    /**
     * Delete an Emissions Unit for a given id
     * @param unitId
     */
    public void delete(Long unitId) {
        EmissionsUnit emissionsUnit= unitRepo
                .findById(unitId)
                .orElse(null);

        Long mfrId = unitRepo.retrieveMasterFacilityRecordIdById(unitId).orElse(null);

        this.findPrevious(mfrId, emissionsUnit.getFacilitySite().getEmissionsReport().getYear(), emissionsUnit.getUnitIdentifier())
            .stream()
            .findFirst()
            .ifPresent(oldUnit -> {
                throw new AppValidationException("This Unit has been submitted on previous years' facility reports, so it cannot be deleted. "
                        + "If this Unit is no longer operational, please use the \"Operating Status\" field to mark this Unit as \"Permanently Shutdown\".");
            });

        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(unitId), EmissionsUnitRepository.class);
        unitRepo.deleteById(unitId);
    }

    /**
     * Create a new Emissions Unit from a DTO object
     */
    public EmissionsUnitDto create(EmissionsUnitDto dto) {

    	EmissionsUnit emissionUnit = emissionsUnitMapper.emissionsUnitFromDto(dto);

    	EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(unitRepo.save(emissionUnit));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionsUnitRepository.class);
    	return result;
    }


    public EmissionsUnitDto update(EmissionsUnitDto dto) {

        EmissionsUnit unit = unitRepo.findById(dto.getId()).orElse(null);

    	if(!(dto.getOperatingStatusCode().getCode().equals(unit.getOperatingStatusCode().getCode()))){

        	OperatingStatusCode tempOperatingStatusCode = new OperatingStatusCode();
        	tempOperatingStatusCode.setCode(dto.getOperatingStatusCode().getCode());
            
        	Short tempStatusYear = dto.getStatusYear();

        	unit.getEmissionsProcesses().forEach(process -> {
        		if(!process.getOperatingStatusCode().getCode().contentEquals("PS")){
		        	process.setOperatingStatusCode(tempOperatingStatusCode);
		        	process.setStatusYear(tempStatusYear);
        		}
        	});
        }

        emissionsUnitMapper.updateFromDto(dto, unit);
        EmissionsUnitDto result = emissionsUnitMapper.emissionsUnitToDto(unitRepo.save(unit));

        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), EmissionsUnitRepository.class);
        return result;
    }

    /**
     * Find versions of this unit from the last year reported
     * @param mfrId
     * @param year
     * @param identifier
     * @return
     */
    private List<EmissionsUnit> findPrevious(Long mfrId, Short year, String identifier) {

        // find the last year reported
        Optional<EmissionsReport> lastReport = reportRepo.findFirstByMasterFacilityRecordIdAndYearLessThanOrderByYearDesc(mfrId,
                year);

        // check if the emissions unit was reported last year
        if (lastReport.isPresent()) {
            return unitRepo.retrieveByIdentifierFacilityYear(identifier,
                    mfrId,
                    lastReport.get().getYear());
        }

        return Collections.emptyList();
    }


    /**
     * Retrieve a list of emissions units for the given program system code and emissions report year
     * @param programSystemCode
     * @param emissionsReportYear
     * @return
     */ 
    public List<EmissionsUnitBulkUploadDto> retrieveEmissionsUnits(String programSystemCode, Short emissionsReportYear) {
    	List<EmissionsUnit> units = unitRepo.findByPscAndEmissionsReportYear(programSystemCode, emissionsReportYear);
    	return bulkUploadMapper.emissionsUnitToDtoList(units);
    }

}
