package gov.epa.cef.web.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LookupRepositories {

    @Autowired
    private CalculationMaterialCodeRepository materialCodeRepo;

    @Autowired
    private CalculationMethodCodeRepository methodCodeRepo;
    
    @Autowired
    private UnitTypeCodeRepository UnitTypeCodeRepo;

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
    
    @Autowired
    private ContactTypeCodeRepository contactTypeRepo;
    
    @Autowired
    private FipsStateCodeRepository stateCodeRepo;
    

    public CalculationMaterialCodeRepository materialCodeRepo() {
        return materialCodeRepo;
    }

    public CalculationMethodCodeRepository methodCodeRepo() {
        return methodCodeRepo;
    }

    public CalculationParameterTypeCodeRepository paramTypeCodeRepo() {
        return paramTypeCodeRepo;
    }

    public OperatingStatusCodeRepository operatingStatusRepo() {
        return operatingStatusRepo;
    }

    public PollutantRepository pollutantRepo() {
        return pollutantRepo;
    }

    public ReportingPeriodCodeRepository periodCodeRepo() {
        return periodCodeRepo;
    }

    public UnitMeasureCodeRepository uomRepo() {
        return uomRepo;
    }
    
<<<<<<< HEAD
    public ContactTypeCodeRepository contactTypeRepo() {
        return contactTypeRepo;
    }
    
    public FipsStateCodeRepository stateCodeRepo() {
        return stateCodeRepo;
=======
    public UnitTypeCodeRepository UnitTypeCodeRepo() {
        return UnitTypeCodeRepo;
>>>>>>> CEF-409-create-functionality-emission-units
    }

}
