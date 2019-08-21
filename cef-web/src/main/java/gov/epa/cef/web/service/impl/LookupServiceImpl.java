package gov.epa.cef.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationMethodCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.PollutantRepository;
import gov.epa.cef.web.repository.ReportingPeriodCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.PollutantDto;
import gov.epa.cef.web.service.mapper.LookupEntityMapper;

@Service
public class LookupServiceImpl implements LookupService {

    @Autowired
    private CalculationMaterialCodeRepository materialCodeRepo;

    @Autowired
    private CalculationMethodCodeRepository methodCodeRepo;

    @Autowired
    private CalculationParameterTypeCodeRepository paramTypeCodeRepo;

    @Autowired
    private OperatingStatusCodeRepository operatingStatusRepo;

    @Autowired
    private PollutantRepository pollutantRepo;

    @Autowired
    private ReportingPeriodCodeRepository periodCodeRepo;

    @Autowired
    private UnitMeasureCodeRepository uomRepo;
    
    // TODO: switch to using LookupRepositories, not currently done due to tests

    @Autowired
    private LookupEntityMapper lookupMapper;


    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.LookupService#retrieveCalcMaterialCodes()
     */
    @Override
    public List<CodeLookupDto> retrieveCalcMaterialCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<CalculationMaterialCode> entities = materialCodeRepo.findAll();

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }

    public CalculationMaterialCode retrieveCalcMaterialCodeEntityByCode(String code) {
        CalculationMaterialCode result= materialCodeRepo
            .findById(code)
            .orElse(null);
        return result;
    }

    public List<CodeLookupDto> retrieveCalcMethodCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<CalculationMethodCode> entities = methodCodeRepo.findAll();

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.LookupService#retrieveCalcParamTypeCodes()
     */
    @Override
    public List<CodeLookupDto> retrieveCalcParamTypeCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<CalculationParameterTypeCode> entities = paramTypeCodeRepo.findAll();

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }

    public CalculationParameterTypeCode retrieveCalcParamTypeCodeEntityByCode(String code) {
        CalculationParameterTypeCode result= paramTypeCodeRepo
            .findById(code)
            .orElse(null);
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.LookupService#retrieveOperatingStatusCodes()
     */
    @Override
    public List<CodeLookupDto> retrieveOperatingStatusCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<OperatingStatusCode> entities = operatingStatusRepo.findAll();

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }

    public OperatingStatusCode retrieveOperatingStatusCodeEntityByCode(String code) {
        OperatingStatusCode result= operatingStatusRepo
            .findById(code)
            .orElse(null);
        return result;
    }

    @Override
    public List<PollutantDto> retrievePollutants() {

        List<PollutantDto> result = new ArrayList<PollutantDto>();
        Iterable<Pollutant> entities = pollutantRepo.findAll();

        entities.forEach(entity -> {
            result.add(lookupMapper.pollutantToDto(entity));
        });
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.LookupService#retrieveReportingPeriodCodes()
     */
    @Override
    public List<CodeLookupDto> retrieveReportingPeriodCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<ReportingPeriodCode> entities = periodCodeRepo.findAll();

        entities.forEach(entity -> {
            result.add(lookupMapper.reportingPeriodCodeToDto(entity));
        });
        return result;
    }

    public ReportingPeriodCode retrieveReportingPeriodCodeEntityByCode(String code) {
        ReportingPeriodCode result= periodCodeRepo
            .findById(code)
            .orElse(null);
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.LookupService#retrieveUnitMeasureCodes()
     */
    @Override
    public List<CodeLookupDto> retrieveUnitMeasureCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<UnitMeasureCode> entities = uomRepo.findAll();

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }

    public UnitMeasureCode retrieveUnitMeasureCodeEntityByCode(String code) {
        UnitMeasureCode result= uomRepo
            .findById(code)
            .orElse(null);
        return result;
    }


}
