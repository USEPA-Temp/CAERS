package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.service.EmissionsProcessService;
import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.dto.EmissionsProcessDto;
import gov.epa.cef.web.service.dto.EmissionsProcessSaveDto;
import gov.epa.cef.web.service.mapper.EmissionsProcessMapper;

@Service
public class EmissionsProcessServiceImpl implements EmissionsProcessService {

    @Autowired
    private EmissionsProcessRepository processRepo;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private EmissionsProcessMapper emissionsProcessMapper;


    public EmissionsProcessDto create(EmissionsProcessSaveDto dto) {

        EmissionsProcess process = emissionsProcessMapper.fromSaveDto(dto);

        process.getReportingPeriods().forEach(period -> {
            if (period.getCalculationMaterialCode() != null) {
                period.setCalculationMaterialCode(lookupService.retrieveCalcMaterialCodeEntityByCode(period.getCalculationMaterialCode().getCode()));
            }

            if (period.getCalculationParameterTypeCode() != null) {
                period.setCalculationParameterTypeCode(lookupService.retrieveCalcParamTypeCodeEntityByCode(period.getCalculationParameterTypeCode().getCode()));
            }

            if (period.getCalculationParameterUom() != null) {
                period.setCalculationParameterUom(lookupService.retrieveUnitMeasureCodeEntityByCode(period.getCalculationParameterUom().getCode()));
            }

            if (period.getEmissionsOperatingTypeCode() != null) {
                period.setEmissionsOperatingTypeCode(lookupService.retrieveOperatingStatusCodeEntityByCode(period.getEmissionsOperatingTypeCode().getCode()));
            }

            if (period.getReportingPeriodTypeCode() != null) {
                period.setReportingPeriodTypeCode(lookupService.retrieveReportingPeriodCodeEntityByCode(period.getReportingPeriodTypeCode().getCode()));
            }

            period.setEmissionsProcess(process);

            period.getOperatingDetails().forEach(od -> {
                od.setReportingPeriod(period);
            });
        });

        EmissionsProcessDto result = emissionsProcessMapper.emissionsProcessToEmissionsProcessDto(processRepo.save(process));
        return result;
    }

    public EmissionsProcessDto update(EmissionsProcessSaveDto dto) {

        EmissionsProcess process = processRepo.findById(dto.getId()).orElse(null);
        emissionsProcessMapper.updateFromSaveDto(dto, process);

        EmissionsProcessDto result = emissionsProcessMapper.emissionsProcessToEmissionsProcessDto(processRepo.save(process));
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.EmissionsProcessService#retrieveById(java.lang.Long)
     */
    @Override
    public EmissionsProcessDto retrieveById(Long id) {
        EmissionsProcess result = processRepo
            .findById(id)
            .orElse(null);
        return emissionsProcessMapper.emissionsProcessToEmissionsProcessDto(result);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.EmissionsProcessService#retrieveForReleasePoint(java.lang.Long)
     */
    @Override
    public List<EmissionsProcessDto> retrieveForReleasePoint(Long pointId) {
        List<EmissionsProcess> result = processRepo.findByReleasePointApptsReleasePointId(pointId);
        return emissionsProcessMapper.emissionsProcessesToEmissionsProcessDtos(result);
    }


    /**
     * Retrieve Emissions Processes for an Emissions Unit
     * @param emissionsUnitid
     * @return
     */
    public List<EmissionsProcessDto> retrieveForEmissionsUnit(Long emissionsUnitId) {
        List<EmissionsProcess> result = processRepo.findByEmissionsUnitId(emissionsUnitId);
        return emissionsProcessMapper.emissionsProcessesToEmissionsProcessDtos(result);
    }


}
