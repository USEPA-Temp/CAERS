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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.exception.AppValidationException;
import gov.epa.cef.web.repository.ControlAssignmentRepository;
import gov.epa.cef.web.repository.ControlPollutantRepository;
import gov.epa.cef.web.repository.ControlRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.ControlService;
import gov.epa.cef.web.service.dto.ControlDto;
import gov.epa.cef.web.service.dto.ControlPollutantDto;
import gov.epa.cef.web.service.dto.EmissionsReportItemDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlAssignmentBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPollutantBulkUploadDto;
import gov.epa.cef.web.service.dto.postOrder.ControlPostOrderDto;
import gov.epa.cef.web.service.mapper.BulkUploadMapper;
import gov.epa.cef.web.service.mapper.ControlMapper;
import gov.epa.cef.web.service.mapper.ControlPollutantMapper;

@Service
public class ControlServiceImpl implements ControlService {

    @Autowired
    private ControlRepository repo;
    
    @Autowired
    private ControlPollutantRepository pollutantRepo;

    @Autowired
    private ControlAssignmentRepository assignmentRepo;

    @Autowired
    private ControlMapper mapper;
    
    @Autowired
    private ControlPollutantMapper pollutantMapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;

    @Autowired
    private EmissionsReportRepository reportRepo;
    
    @Autowired
    private BulkUploadMapper bulkUploadMapper;

    /**
     * Create a new Control from a DTO object
     */
    public ControlDto create(ControlDto dto) {
    	Control control = mapper.fromDto(dto);
    	
    	ControlDto result = mapper.toDto(repo.save(control));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ControlRepository.class);
    	return result;
    }
    
    /**
     * Update an existing Control from a DTO
     */
    public ControlDto update(ControlDto dto) {
    	
    	Control control = repo.findById(dto.getId()).orElse(null);
    	mapper.updateFromDto(dto, control);
    	
    	ControlDto result = mapper.toDto(repo.save(control));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ControlRepository.class);
    	return result;
    }

    /**
     * Delete a Control for a given id
     * @Param controlId
     */
    public void delete(Long controlId) {
        Control control= repo
                .findById(controlId)
                .orElse(null);

        Long mfrId = repo.retrieveMasterFacilityRecordIdById(controlId).orElse(null);

        this.findPrevious(mfrId, control.getFacilitySite().getEmissionsReport().getYear(), control.getIdentifier())
            .stream()
            .findFirst()
            .ifPresent(oldUnit -> {
                throw new AppValidationException("This Control has been submitted on previous years' facility reports, so it cannot be deleted. "
                        + "If this Control is no longer operational, please use the \"Operating Status\" field to mark this Control as \"Permanently Shutdown\".");
            });

        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(controlId), ControlRepository.class);
    	repo.deleteById(controlId);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ControlService#retrieveById(java.lang.Long)
     */
    @Override
    public ControlPostOrderDto retrieveById(Long id) {
        Control result = repo
            .findById(id)
            .orElse(null);
        return mapper.toDto(result);
    }

    /**
     * Retrieve versions of this control from the last year reported
     * @param id
     * @return
     */
    public ControlPostOrderDto retrievePreviousById(Long id) {
        Control control= repo
                .findById(id)
                .orElse(null);

        Long mfrId = repo.retrieveMasterFacilityRecordIdById(id).orElse(null);

        Control result = this.findPrevious(mfrId, control.getFacilitySite().getEmissionsReport().getYear(), control.getIdentifier())
                .stream()
                .findFirst()
                .orElse(null);

        return mapper.toDto(result);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ControlService#retrieveForFacilitySite(java.lang.Long)
     */
    @Override
    public List<ControlPostOrderDto> retrieveForFacilitySite(Long facilitySiteId) {
        List<Control> result = repo.findByFacilitySiteIdOrderByIdentifier(facilitySiteId);
        return mapper.toDtoList(result);
    }

    
    /***
     * Retrieve a DTO containing all of the related sub-facility components for the given control
     * @param controlId
     * @return
     */
    @Override
    public List<EmissionsReportItemDto> retrieveControlComponents(Long controlId) {
    	Control control = repo.findById(controlId).orElse(null);
    	List<EmissionsReportItemDto> emissionsReportItems = new ArrayList<EmissionsReportItemDto>();
    	if (control != null) {
    	    for (ControlAssignment assignment : control.getAssignments()) {
    	        createReportItems(assignment.getControlPath(), emissionsReportItems);
            }
    	}
    	return emissionsReportItems;
    }
    
    /**
     * Create a new Control Pollutant from a DTO object
     */
    public ControlPollutantDto createPollutant(ControlPollutantDto dto) {
    	ControlPollutant control = pollutantMapper.fromDto(dto);
    	
    	ControlPollutantDto result = pollutantMapper.toDto(pollutantRepo.save(control));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getControlId()), ControlRepository.class);
    	return result;
    }
    
    /**
     * Update an existing Control Pollutant from a DTO
     */
    public ControlPollutantDto updateControlPollutant(ControlPollutantDto dto) {
    	
    	ControlPollutant control = pollutantRepo.findById(dto.getId()).orElse(null);
    	pollutantMapper.updateFromDto(dto, control);
    	
    	ControlPollutantDto result = pollutantMapper.toDto(pollutantRepo.save(control));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getControlId()), ControlRepository.class);
    	return result;
    }
    
    /**
     * Delete a Control Pollutant for a given id
     * @Param controlId
     */
    public void deleteControlPollutant(Long controlPollutantId) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(controlPollutantId), ControlPollutantRepository.class);
    	pollutantRepo.deleteById(controlPollutantId);
    }

    /**
     * Find versions of this control from the last year reported
     * @param mfrId
     * @param year
     * @param identifier
     * @return
     */
    private List<Control> findPrevious(Long mfrId, Short year, String identifier) {

        // find the last year reported
        Optional<EmissionsReport> lastReport = reportRepo.findFirstByMasterFacilityRecordIdAndYearLessThanOrderByYearDesc(mfrId,
                year);

        // check if the control was reported last year
        if (lastReport.isPresent()) {
            return repo.retrieveByIdentifierFacilityYear(identifier,
                    mfrId,
                    lastReport.get().getYear());
        }

        return Collections.emptyList();
    }
    
    /***
     * Create and populate the emissions report items for the control path and parent paths.
     * @param path
     * @param emissionsReportItems
     */
	private void createReportItems(ControlPath path, List<EmissionsReportItemDto> emissionsReportItems) {
		List<ControlAssignment> parentAssignments = assignmentRepo.findByControlPathChildId(path.getId());
		for (ControlAssignment parentAssignment : parentAssignments) {
			if (parentAssignment.getControlPath() != null) {
				createReportItems(parentAssignment.getControlPath(), emissionsReportItems);
			}
		}
		
		for (ReleasePointAppt rpa : path.getReleasePointAppts()) {
			if (!isDuplicateItem(rpa.getEmissionsProcess().getId(), "process", emissionsReportItems)) {
				EmissionsReportItemDto eri = new EmissionsReportItemDto();
				eri.setDescription(rpa.getEmissionsProcess().getDescription());
				eri.setId(rpa.getEmissionsProcess().getId());
				eri.setIdentifier(rpa.getEmissionsProcess().getEmissionsProcessIdentifier());
				eri.setType("process");
				eri.setTypeDesc("Emissions Process");
				emissionsReportItems.add(eri);
			}
			
			if (!isDuplicateItem(rpa.getReleasePoint().getId(), "release", emissionsReportItems)) {
				EmissionsReportItemDto eri = new EmissionsReportItemDto();
				eri.setDescription(rpa.getReleasePoint().getDescription());
				eri.setId(rpa.getReleasePoint().getId());
				eri.setIdentifier(rpa.getReleasePoint().getReleasePointIdentifier());
				eri.setType("release");
				eri.setTypeDesc("Release Point");
				emissionsReportItems.add(eri);
			}
			
			if (!isDuplicateItem(rpa.getEmissionsProcess().getEmissionsUnit().getId(), "emissionUnit", emissionsReportItems)) {
				EmissionsReportItemDto eri = new EmissionsReportItemDto();
				eri.setDescription(rpa.getEmissionsProcess().getEmissionsUnit().getDescription());
				eri.setId(rpa.getEmissionsProcess().getEmissionsUnit().getId());
				eri.setIdentifier(rpa.getEmissionsProcess().getEmissionsUnit().getUnitIdentifier());
				eri.setType("emissionUnit");
				eri.setTypeDesc("Emissions Unit");
				emissionsReportItems.add(eri);
			}
		}
		
	}



	/***
	 * Determine if the item already exists in the emissionsReportItems list
	 * @param emissionsUnits
	 * @param emissionsUnit
	 * @return
	 */
	private boolean isDuplicateItem(Long id, String reportItemType, List<EmissionsReportItemDto> emissionsUnits) {
		if (emissionsUnits != null) {
			for (EmissionsReportItemDto itemDto : emissionsUnits) {
				if (id.equals(itemDto.getId()) && reportItemType.equals(itemDto.getType())) {
					return true;
				}
			}
		}
		return false;
	}


    /**
     * Retrieve a list of controls for the given program system code and emissions report year
     * @param programSystemCode
     * @param emissionsReportYear
     * @return
     */  
    public List<ControlBulkUploadDto> retrieveControls(String programSystemCode, Short emissionsReportYear) {
    	List<Control> controls = repo.findByPscAndEmissionsReportYear(programSystemCode, emissionsReportYear);
    	return bulkUploadMapper.controlToDtoList(controls);
    }


    /**
     * Retrieve a list of control assignments for the given program system code and emissions report year
     * @param programSystemCode
     * @param emissionsReportYear
     * @return
     */ 
    public List<ControlAssignmentBulkUploadDto> retrieveControlAssignments(String programSystemCode, Short emissionsReportYear) {
    	List<ControlAssignment> controlAssignments = assignmentRepo.findByPscAndEmissionsReportYear(programSystemCode, emissionsReportYear);
    	return bulkUploadMapper.controlAssignmentToDtoList(controlAssignments);
    }


    /**
     * Retrieve a list of control pollutants for the given program system code and emissions report year
     * @param programSystemCode
     * @param emissionsReportYear
     * @return
     */ 
    public List<ControlPollutantBulkUploadDto> retrieveControlPollutants(String programSystemCode, Short emissionsReportYear) {
    	List<ControlPollutant> controlPollutants = pollutantRepo.findByPscAndEmissionsReportYear(programSystemCode, emissionsReportYear);
    	return bulkUploadMapper.controlPollutantToDtoList(controlPollutants);
    }
}
