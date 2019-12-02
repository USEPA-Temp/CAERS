package gov.epa.cef.web.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.CalculationMethodCode;

@Service
public class LookupRepositories {

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
    
    @Autowired
    private ContactTypeCodeRepository contactTypeRepo;
    
    @Autowired
    private FipsStateCodeRepository stateCodeRepo;
    
    @Autowired
    private ReleasePointTypeCodeRepository releasePtCodeRepo;
    
    @Autowired
    private ProgramSystemCodeRepository programSystemCodeRepo;
    

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
    
    public ContactTypeCodeRepository contactTypeRepo() {
        return contactTypeRepo;
    }
    
    public FipsStateCodeRepository stateCodeRepo() {
        return stateCodeRepo;
    }

	public ReleasePointTypeCodeRepository releasePtCodeRepo() {
        return releasePtCodeRepo;
    }
	
	public ProgramSystemCodeRepository programSystemCodeRepo() {
        return programSystemCodeRepo;
    }

}
