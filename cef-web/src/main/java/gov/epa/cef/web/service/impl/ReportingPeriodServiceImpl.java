package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportingPeriodRepository;
import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.ReportingPeriodService;
import gov.epa.cef.web.service.dto.EmissionBulkEntryHolderDto;
import gov.epa.cef.web.service.dto.ReportingPeriodBulkEntryDto;
import gov.epa.cef.web.service.dto.ReportingPeriodDto;
import gov.epa.cef.web.service.dto.ReportingPeriodUpdateResponseDto;
import gov.epa.cef.web.service.mapper.ReportingPeriodMapper;
import gov.epa.cef.web.util.CalculationUtils;
import gov.epa.cef.web.util.MassUomConversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportingPeriodServiceImpl implements ReportingPeriodService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportingPeriodRepository repo;

    @Autowired
    private EmissionsReportRepository reportRepo;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private ReportingPeriodMapper mapper;

    @Autowired
    private EmissionsReportStatusServiceImpl reportStatusService;

    @Autowired
    private EmissionServiceImpl emissionService;

    public ReportingPeriodDto create(ReportingPeriodDto dto) {

        ReportingPeriod period = mapper.fromDto(dto);

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
            period.setEmissionsOperatingTypeCode(lookupService.retrieveEmissionsOperatingTypeCodeEntityByCode(dto.getEmissionsOperatingTypeCode().getCode()));
        }

        if (dto.getReportingPeriodTypeCode() != null) {
            period.setReportingPeriodTypeCode(lookupService.retrieveReportingPeriodCodeEntityByCode(dto.getReportingPeriodTypeCode().getCode()));
        }

        period.getOperatingDetails().forEach(od -> {
            od.setReportingPeriod(period);
        });

        ReportingPeriodDto result = mapper.toDto(repo.save(period));
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(result.getId()), ReportingPeriodRepository.class);
        return result;
    }

    public ReportingPeriodUpdateResponseDto update(ReportingPeriodDto dto) {

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
            period.setEmissionsOperatingTypeCode(lookupService.retrieveEmissionsOperatingTypeCodeEntityByCode(dto.getEmissionsOperatingTypeCode().getCode()));
        }

        if (dto.getReportingPeriodTypeCode() != null) {
            period.setReportingPeriodTypeCode(lookupService.retrieveReportingPeriodCodeEntityByCode(dto.getReportingPeriodTypeCode().getCode()));
        }

        ReportingPeriodUpdateResponseDto result = new ReportingPeriodUpdateResponseDto();

        period.getEmissions().forEach(emission -> {
            if (!(Boolean.TRUE.equals(emission.getTotalManualEntry()) 
                    || Boolean.TRUE.equals(emission.getEmissionsCalcMethodCode().getTotalDirectEntry()))) {
                try {
                    Emission updatedEmission = emissionService.calculateTotalEmissions(emission, period);
                    emission.setEmissionsFactor(updatedEmission.getEmissionsFactor());
                    emission.setTotalEmissions(updatedEmission.getTotalEmissions());
                    result.getUpdatedEmissions().add(emission.getPollutant().getPollutantName());
                } catch (ApplicationException e) {
                    result.getFailedEmissions().add(emission.getPollutant().getPollutantName());
                }
            } else {
                result.getNotUpdatedEmissions().add(emission.getPollutant().getPollutantName());
            }

            emission.setCalculatedEmissionsTons(calculateEmissionTons(emission));
        });

        ReportingPeriodDto resultDto = mapper.toDto(repo.save(period));
        reportStatusService.resetEmissionsReportForEntity(Collections.singletonList(resultDto.getId()), ReportingPeriodRepository.class);

        result.setReportingPeriod(resultDto);
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

    /**
     * Retrieve Reporting Periods for Bulk Entry for a specific facility site
     * @param facilitySiteId
     * @return
     */
    public List<ReportingPeriodBulkEntryDto> retrieveBulkEntryReportingPeriodsForFacilitySite(Long facilitySiteId) {

        List<ReportingPeriod> entities = repo.findByFacilitySiteId(facilitySiteId).stream()
                .filter(rp -> !"PS".equals(rp.getEmissionsProcess().getOperatingStatusCode().getCode()))
                .collect(Collectors.toList());

        List<ReportingPeriodBulkEntryDto> result = mapper.toBulkEntryDtoList(entities);

        if (!entities.isEmpty()) {
            // find the last year reported
            Optional<EmissionsReport> lastReport = reportRepo.findFirstByEisProgramIdAndYearLessThanOrderByYearDesc(
                    entities.get(0).getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEisProgramId(),
                    entities.get(0).getEmissionsProcess().getEmissionsUnit().getFacilitySite().getEmissionsReport().getYear());

            if (lastReport.isPresent()) {
                result.forEach(dto -> {
                    List<ReportingPeriod> oldEntities = repo.retrieveByTypeIdentifierParentFacilityYear(dto.getReportingPeriodTypeCode().getCode(),
                            dto.getEmissionsProcessIdentifier(),
                            dto.getUnitIdentifier(), 
                            lastReport.get().getEisProgramId(), 
                            lastReport.get().getYear());
                    if (!oldEntities.isEmpty()) {
                        dto.setPreviousCalculationParameterValue(oldEntities.get(0).getCalculationParameterValue());
                        if (oldEntities.get(0).getCalculationParameterUom() != null) {
                        	dto.setPreviousCalculationParameterUomCode(oldEntities.get(0).getCalculationParameterUom().getCode());
                        } else {
                        	dto.setPreviousCalculationParameterUomCode(null);
                        }
                    }
                });
            }
        }

        return result;
    }

    /**
     * Update the throughput for multiple Reporting Periods at once
     * @param facilitySiteId
     * @param dtos
     * @return
     */
    public List<EmissionBulkEntryHolderDto> bulkUpdate(Long facilitySiteId, List<ReportingPeriodBulkEntryDto> dtos) {

        List<ReportingPeriod> periods = dtos.stream().map(dto -> {
            ReportingPeriod period = repo.findById(dto.getReportingPeriodId())
                    .orElseThrow(() -> new NotExistException("Reporting Period", dto.getReportingPeriodId()));
            period.setCalculationParameterValue(dto.getCalculationParameterValue());
            return period;
        }).collect(Collectors.toList());

        repo.saveAll(periods);

        return emissionService.bulkUpdate(facilitySiteId, Collections.emptyList());
    }

    private BigDecimal calculateEmissionTons(Emission emission) {
        try {
            BigDecimal calculatedEmissionsTons = CalculationUtils.convertMassUnits(emission.getTotalEmissions(), 
                    MassUomConversion.valueOf(emission.getEmissionsUomCode().getCode()), 
                    MassUomConversion.TON);
            return calculatedEmissionsTons;
        } catch (IllegalArgumentException ex) {
            logger.debug("Could not perform emission conversion. {}", ex.getLocalizedMessage());
            return null;
        }
    }

}
