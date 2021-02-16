package gov.epa.cef.web.service.impl;

import java.util.Collections;  
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.exception.AppValidationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReleasePointApptRepository;
import gov.epa.cef.web.repository.ReleasePointRepository;
import gov.epa.cef.web.service.ReleasePointService;
import gov.epa.cef.web.service.dto.ControlPathDto;
import gov.epa.cef.web.service.dto.ReleasePointApptDto;
import gov.epa.cef.web.service.dto.ReleasePointDto;
import gov.epa.cef.web.service.mapper.ReleasePointMapper;
import gov.epa.cef.web.service.mapper.ControlPathMapper;
import gov.epa.cef.web.service.mapper.ReleasePointApptMapper;

@Service
public class ReleasePointServiceImpl implements ReleasePointService {

    @Autowired
    private EmissionsReportRepository reportRepo;

    @Autowired
    private ReleasePointRepository releasePointRepo;
    
    @Autowired
    private ReleasePointApptRepository releasePointApptRepo;
    
    @Autowired
    private ReleasePointMapper releasePointMapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;
    
    @Autowired
    private ReleasePointApptMapper releasePointApptMapper;
    
    @Autowired
    private ControlPathServiceImpl controlPathService;
    
    @Autowired
    private ControlPathMapper controlPathMapper;
    
    /**
     * Create a new Release Point from a DTO object
     */
    public ReleasePointDto create(ReleasePointDto dto) {
    	ReleasePoint releasePoint = releasePointMapper.fromDto(dto);
    	
    	ReleasePointDto result = releasePointMapper.toDto(releasePointRepo.save(releasePoint));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ReleasePointRepository.class);
 
    	return result;
    }
    
    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReleasePointService#retrieveById(java.lang.Long)
     */ 
    @Override
    public ReleasePointDto retrieveById(Long releasePointId) {
        ReleasePoint releasePoint= releasePointRepo
            .findById(releasePointId)
            .orElse(null);
        return releasePointMapper.toDto(releasePoint);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReleasePointService#retrieveByFacilityId(java.lang.Long)
     */
    public List<ReleasePointDto> retrieveByFacilitySiteId(Long facilitySiteId) {
        List<ReleasePoint> releasePoints=releasePointRepo.findByFacilitySiteIdOrderByReleasePointIdentifier(facilitySiteId);
        return releasePointMapper.toDtoList(releasePoints);
    }
    
    /**
     * Update an existing Release Point from a DTO
     */
    public ReleasePointDto update(ReleasePointDto dto) {
    	
    	ReleasePoint releasePoint = releasePointRepo.findById(dto.getId()).orElse(null);
    	releasePointMapper.updateFromDto(dto, releasePoint);
    	
    	ReleasePointDto result = releasePointMapper.toDto(releasePointRepo.save(releasePoint));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ReleasePointRepository.class);

        return result;
    }
    
    /**
     * Delete Release Point for a given id
     * @param releasePointId
     */
    public void delete(Long releasePointId) {
        ReleasePoint rp = releasePointRepo
                .findById(releasePointId)
                .orElse(null);

        Long mfrId = releasePointRepo.retrieveMasterFacilityRecordIdById(releasePointId).orElse(null);

        // find the last year reported
        Optional<EmissionsReport> lastReport = reportRepo.findFirstByMasterFacilityRecordIdAndYearLessThanOrderByYearDesc(mfrId,
                rp.getFacilitySite().getEmissionsReport().getYear());

        // check if the release point was reported last year
        if (lastReport.isPresent()) {
            releasePointRepo.retrieveByIdentifierFacilityYear(rp.getReleasePointIdentifier(), 
                    mfrId, 
                    lastReport.get().getYear())
                    .stream().findFirst().ifPresent(oldRp -> {
                        throw new AppValidationException("This Release Point has been submitted on previous years' facility reports, so it cannot be deleted. "
                                + "If this Release Point is no longer operational, please use the \"Operating Status\" field to mark this Release Point as \"Permanently Shutdown\".");
                    });
        }

        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(releasePointId), ReleasePointRepository.class);
    	releasePointRepo.deleteById(releasePointId);
    }
    
    /**
     * Delete Release Point Apportionment for a given id
     * @param releasePointApptId
     */
    public void deleteAppt(Long releasePointApptId) {
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(releasePointApptId), ReleasePointApptRepository.class);
    	releasePointApptRepo.deleteById(releasePointApptId);
    }

    /**
     * Create a new Release Point Apportionment from a DTO object
     */
    public ReleasePointApptDto createAppt(ReleasePointApptDto dto) {
    	ReleasePointAppt releasePointAppt = releasePointApptMapper.fromDto(dto);

    	ReleasePointApptDto results = releasePointApptMapper.toDto(releasePointApptRepo.save(releasePointAppt));
    	
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(results.getReleasePointId()), ReleasePointRepository.class);
    	return results;
    }
    
    /**
     * Update an existing Release Point Apportionment from a DTO
     */
    public ReleasePointApptDto updateAppt(ReleasePointApptDto dto) {
    	ControlPathDto controlPathDto = new ControlPathDto();
    	
    	if(dto.getControlPath() != null){
        	controlPathDto = controlPathService.retrieveById(dto.getControlPath().getId());
    	}
    	
    	ReleasePointAppt releasePointAppt = releasePointApptRepo.findById(dto.getId()).orElse(null);
    	releasePointAppt.setReleasePoint(null);
    	dto.setControlPath(null);
    	releasePointApptMapper.updateFromDto(dto, releasePointAppt);
    	
    	if(controlPathDto.getId() != null){
        	ControlPath controlPath = controlPathMapper.fromDto(controlPathDto);
        	releasePointAppt.setControlPath(controlPath);
    	}
    	
    	ReleasePointApptDto result = releasePointApptMapper.toDto(releasePointApptRepo.save(releasePointAppt));
    	reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ReleasePointApptRepository.class);

        return result;
    }
    
}
