package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.repository.FacilityNAICSXrefRepository;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilityNAICSDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilityNAICSMapper;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FacilitySiteServiceImpl implements FacilitySiteService {

    private final FacilitySiteRepository facSiteRepo;

    private final FacilityNAICSXrefRepository facilityNaicsXrefRepo;

    private final EmissionsReportStatusServiceImpl reportStatusService;

    private final FacilitySiteMapper facilitySiteMapper;

    private final FacilityNAICSMapper facilityNaicsMapper;

    @Autowired
    FacilitySiteServiceImpl(FacilitySiteRepository facSiteRepo,
                            FacilitySiteMapper facilitySiteMapper,
                            FacilityNAICSXrefRepository facilityNaicsXrefRepo,
                            FacilityNAICSMapper facilityNaicsMapper,
                            EmissionsReportStatusServiceImpl reportStatusService) {

        this.facSiteRepo = facSiteRepo;
        this.facilitySiteMapper = facilitySiteMapper;
        this.facilityNaicsXrefRepo = facilityNaicsXrefRepo;
        this.facilityNaicsMapper = facilityNaicsMapper;
        this.reportStatusService = reportStatusService;
    }

    @Override
    public FacilitySiteDto findByEisProgramIdAndReportId(String eisProgramId, Long emissionsReportId) {

        return facSiteRepo.findByEisProgramIdAndEmissionsReportId(eisProgramId, emissionsReportId)
            .stream()
            .findFirst()
            .map(facilitySiteMapper::toDto)
            .orElse(null);
    }

    @Override
    public FacilitySiteDto findById(Long id) {

        return facSiteRepo.findById(id)
            .map(facilitySiteMapper::toDto)
            .orElse(null);
    }

    @Override
    public FacilitySite transform(FacilitySiteDto dto) {

        return this.facilitySiteMapper.fromDto(dto);
    }

    public FacilitySiteDto update(FacilitySiteDto dto) {

    	FacilitySite facilitySite = facSiteRepo.findById(dto.getId()).orElse(null);
    	
    	if(!(dto.getOperatingStatusCode().getCode().equals(facilitySite.getOperatingStatusCode().getCode()))){
    		
			OperatingStatusCode tempOperatingStatusCode = new OperatingStatusCode();
			tempOperatingStatusCode.setCode(dto.getOperatingStatusCode().getCode());
			
        	Short tempStatusYear = dto.getStatusYear();
        	
        	facilitySite.getEmissionsUnits().forEach(unit -> {
        		if(!unit.getOperatingStatusCode().getCode().contentEquals("PS")){
    	        	unit.setOperatingStatusCode(tempOperatingStatusCode);
    	        	unit.setStatusYear(tempStatusYear);
    	        	unit.getEmissionsProcesses().forEach(process -> {
    	        		if(!process.getOperatingStatusCode().getCode().contentEquals("PS")){
            	        	process.setOperatingStatusCode(tempOperatingStatusCode);
            	        	process.setStatusYear(tempStatusYear);
    	        		}
    	        	});
        		}
        	});
        	
        	facilitySite.getControls().forEach(control -> {
        		if(!control.getOperatingStatusCode().getCode().contentEquals("PS")){
        			control.setOperatingStatusCode(tempOperatingStatusCode);
        		}
        	});
        	
        	facilitySite.getReleasePoints().forEach(releasePoint -> {
        		if(!releasePoint.getOperatingStatusCode().getCode().contentEquals("PS")){
    	        	releasePoint.setOperatingStatusCode(tempOperatingStatusCode);
    	        	releasePoint.setStatusYear(tempStatusYear);
        		}
        	});
        }
   
    	facilitySiteMapper.updateFromDto(dto, facilitySite);
    	
    	FacilitySiteDto result = facilitySiteMapper.toDto(facSiteRepo.save(facilitySite));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), FacilitySiteRepository.class);

    	return result;
    }

    /**
     * Create Facility Site
     * @param facilitySite
     */
	public FacilitySiteDto create(FacilitySite facilitySite){

    	FacilitySiteDto result = facilitySiteMapper.toDto(facSiteRepo.save(facilitySite));

    	return result;
	}

    /**
     * Create Facility NAICS
     * @param dto
     */
    public FacilityNAICSDto createNaics(FacilityNAICSDto dto) {
    	FacilityNAICSXref facilityNaics = facilityNaicsMapper.fromDto(dto);

    	FacilityNAICSDto result = facilityNaicsMapper.facilityNAICSXrefToFacilityNAICSDto(facilityNaicsXrefRepo.save(facilityNaics));
			reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), FacilityNAICSXrefRepository.class);

    	return result;
    }

    /**
     * Update existing Facility NAICS
     */
    public FacilityNAICSDto updateNaics(FacilityNAICSDto dto) {

    	FacilityNAICSXref facilityNaics = facilityNaicsXrefRepo.findById(dto.getId()).orElse(null);
    	facilityNaicsMapper.updateFromDto(dto, facilityNaics);

    	FacilityNAICSDto result = facilityNaicsMapper.facilityNAICSXrefToFacilityNAICSDto(facilityNaicsXrefRepo.save(facilityNaics));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), FacilityNAICSXrefRepository.class);

    	return result;
    }

    /**
     * Delete Facility NAICS by id
     * @param facilityNaicsId
     */
    public void deleteFacilityNaics(Long facilityNaicsId) {
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(facilityNaicsId), FacilityNAICSXrefRepository.class);
    	facilityNaicsXrefRepo.deleteById(facilityNaicsId);
    }
}
