package gov.epa.cef.web.service.impl;

import java.util.ArrayList; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.ControlMeasureCode;
import gov.epa.cef.web.domain.EmissionsOperatingTypeCode;
import gov.epa.cef.web.domain.FipsStateCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.cef.web.domain.ReleasePointTypeCode;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.domain.UnitTypeCode;
import gov.epa.cef.web.repository.CalculationMaterialCodeRepository;
import gov.epa.cef.web.repository.CalculationMethodCodeRepository;
import gov.epa.cef.web.repository.CalculationParameterTypeCodeRepository;
import gov.epa.cef.web.repository.ContactTypeCodeRepository;
import gov.epa.cef.web.repository.ControlMeasureCodeRepository;
import gov.epa.cef.web.repository.EmissionsOperatingTypeCodeRepository;
import gov.epa.cef.web.repository.FipsStateCodeRepository;
import gov.epa.cef.web.repository.OperatingStatusCodeRepository;
import gov.epa.cef.web.repository.PollutantRepository;
import gov.epa.cef.web.repository.ProgramSystemCodeRepository;
import gov.epa.cef.web.repository.ReleasePointTypeCodeRepository;
import gov.epa.cef.web.repository.ReportingPeriodCodeRepository;
import gov.epa.cef.web.repository.UnitMeasureCodeRepository;
import gov.epa.cef.web.repository.UnitTypeCodeRepository;
import gov.epa.cef.web.service.LookupService;
import gov.epa.cef.web.service.dto.CalculationMethodCodeDto;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.FipsStateCodeDto;
import gov.epa.cef.web.service.dto.PollutantDto;
import gov.epa.cef.web.service.dto.UnitMeasureCodeDto;
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
    private EmissionsOperatingTypeCodeRepository emissionsOperatingTypeCodeRepo;

    @Autowired
    private PollutantRepository pollutantRepo;

    @Autowired
    private UnitTypeCodeRepository unitTypeCodeRepo;

    @Autowired
    private ReportingPeriodCodeRepository periodCodeRepo;

    @Autowired
    private UnitMeasureCodeRepository uomRepo;
    
    @Autowired
    private ContactTypeCodeRepository contactTypeRepo;
    
    @Autowired
    private FipsStateCodeRepository stateCodeRepo;
    
    @Autowired
    private ReleasePointTypeCodeRepository releasePtTypeRepository;
    
    @Autowired
    private ProgramSystemCodeRepository programSystemCodeRepo;
    
    @Autowired
    private ControlMeasureCodeRepository controlMeasureCodeRepo;
    
    // TODO: switch to using LookupRepositories, not currently done due to tests

    @Autowired
    private LookupEntityMapper lookupMapper;


    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.LookupService#retrieveCalcMaterialCodes()
     */
    @Override
    public List<CodeLookupDto> retrieveCalcMaterialCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<CalculationMaterialCode> entities = materialCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

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

    public List<CalculationMethodCodeDto> retrieveCalcMethodCodes() {

        List<CalculationMethodCodeDto> result = new ArrayList<CalculationMethodCodeDto>();
        Iterable<CalculationMethodCode> entities = methodCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

        entities.forEach(entity -> {
            result.add(lookupMapper.calculationMethodCodeToDto(entity));
        });
        return result;
    }

    /* (non-Javadoc)
     * @see gov.epa.cef.web.service.impl.LookupService#retrieveCalcParamTypeCodes()
     */
    @Override
    public List<CodeLookupDto> retrieveCalcParamTypeCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<CalculationParameterTypeCode> entities = paramTypeCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

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
        Iterable<OperatingStatusCode> entities = operatingStatusRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

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
    public List<CodeLookupDto> retrieveEmissionOperatingTypeCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<EmissionsOperatingTypeCode> entities = emissionsOperatingTypeCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "shortName"));

        entities.forEach(entity -> {
            result.add(lookupMapper.emissionsOperatingTypeCodeToDto(entity));
        });
        return result;
    }
    
    public EmissionsOperatingTypeCode retrieveEmissionsOperatingTypeCodeEntityByCode(String code) {
        
        EmissionsOperatingTypeCode result= emissionsOperatingTypeCodeRepo
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
        Iterable<ReportingPeriodCode> entities = periodCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "shortName"));

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
    public List<UnitMeasureCodeDto> retrieveUnitMeasureCodes() {

        List<UnitMeasureCodeDto> result = new ArrayList<UnitMeasureCodeDto>();
        Iterable<UnitMeasureCode> entities = uomRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "code"));

        entities.forEach(entity -> {
            result.add(lookupMapper.unitMeasureCodeToDto(entity));
        });
        return result;
    }

    public UnitMeasureCode retrieveUnitMeasureCodeEntityByCode(String code) {
        UnitMeasureCode result= uomRepo
            .findById(code)
            .orElse(null);
        return result;
    }

    public List<CodeLookupDto> retrieveContactTypeCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<ContactTypeCode> entities = contactTypeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "code"));
        
        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }
    
    public List<CodeLookupDto> retrieveUnitTypeCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<UnitTypeCode> entities = unitTypeCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }
    
    public ContactTypeCode retrieveContactTypeEntityByCode(String code) {
    	ContactTypeCode result= contactTypeRepo
            .findById(code)
            .orElse(null);
        return result;
    }

    public List<FipsStateCodeDto> retrieveStateCodes() {

        List<FipsStateCodeDto> result = new ArrayList<FipsStateCodeDto>();
        Iterable<FipsStateCode> entities = stateCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "code"));

        entities.forEach(entity -> {
            result.add(lookupMapper.fipsStateCodeToDto(entity));
        });
        return result;
    }
    
    public FipsStateCode retrieveStateCodeEntityByCode(String code) {
    	FipsStateCode result= stateCodeRepo
            .findById(code)
            .orElse(null);
        return result;
    }
    
    @Override
    public List<CodeLookupDto> retrieveReleasePointTypeCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<ReleasePointTypeCode> entities = releasePtTypeRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }
    
    public ReleasePointTypeCode retrieveReleasePointTypeCodeEntityByCode(String code) {
    	ReleasePointTypeCode result= releasePtTypeRepository
            .findById(code)
            .orElse(null);
        return result;
    }
    
    @Override
    public List<CodeLookupDto> retrieveProgramSystemTypeCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<ProgramSystemCode> entities = programSystemCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }
    
    public ProgramSystemCode retrieveProgramSystemTypeCodeEntityByCode(String code) {
    	ProgramSystemCode result= programSystemCodeRepo
            .findById(code)
            .orElse(null);
        return result;
    }
    
    @Override
    public List<CodeLookupDto> retrieveControlMeasureCodes() {

        List<CodeLookupDto> result = new ArrayList<CodeLookupDto>();
        Iterable<ControlMeasureCode> entities = controlMeasureCodeRepo.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC, "description"));

        entities.forEach(entity -> {
            result.add(lookupMapper.toDto(entity));
        });
        return result;
    }
    
    public ControlMeasureCode retrieveControlMeasureCodeEntityByCode(String code) {
    	ControlMeasureCode result = controlMeasureCodeRepo
            .findById(code)
            .orElse(null);
        return result;
    }
}
