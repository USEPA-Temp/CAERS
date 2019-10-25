package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.ReportingPeriodService;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import gov.epa.cef.web.service.mapper.ReportingPeriodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingPeriodServiceImpl implements ReportingPeriodService {

    @Autowired
    private ReportingPeriodRepository repo;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private ReportingPeriodMapper mapper;

    public ReportingPeriodDto update(ReportingPeriodDto dto) {

        ReportingPeriod period = repo.findById(dto.getId())
            .orElseThrow(() -> new NotExistException("Reporting Period", dto.getId()));

        mapper.updateFromDto(dto, period);

        if (dto.getCalculationMaterialCode() != null) {
            period.setCalculationMaterialCode(lookupService.retrieveCalcMaterialCodeEntityByCode(dto.getCalculationMaterialCode().getCode()));
        }

        if (dto.getCalculationParameterTypeCode() != null) {
            period.setCalculationParameterTypeCode(lookupService.retrieveCalcParamTypeCodeEntityByCode(dto.getCalculationParameterTypeCode().getCode()));
        }

        if (dto.getCalculationParameterUom() != null) {
            period.setCalculationParameterUom(lookupService.retrieveUnitMeasureCodeEntityByCode(dto.getCalculationParameterUom().getCode()));
        }

        if (dto.getEmissionsOperatingTypeCode() != null) {
            period.setEmissionsOperatingTypeCode(lookupService.retrieveOperatingStatusCodeEntityByCode(dto.getEmissionsOperatingTypeCode().getCode()));
        }

        if (dto.getReportingPeriodTypeCode() != null) {
            period.setReportingPeriodTypeCode(lookupService.retrieveReportingPeriodCodeEntityByCode(dto.getReportingPeriodTypeCode().getCode()));
        }

        ReportingPeriodDto result = mapper.toDto(repo.save(period));
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportingPeriodService#retrieveById(java.lang.Long)
     */
    @Override
    public ReportingPeriodDto retrieveById(Long id) {
        ReportingPeriod result = repo
            .findById(id)
            .orElse(null);
        return mapper.toDto(result);
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.ReportingPeriodService#retrieveForReleasePoint(java.lang.Long)
     */
    @Override
    public List<ReportingPeriodDto> retrieveForEmissionsProcess(Long processId) {
        List<ReportingPeriod> result = repo.findByEmissionsProcessId(processId);
        return mapper.toDtoList(result);
    }

}
