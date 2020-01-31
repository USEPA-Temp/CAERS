package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.repository.PointSourceSccCodeRepository;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsProcessValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsProcessValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private EmissionsProcessValidator validator;
    
    @Mock
		private PointSourceSccCodeRepository sccRepo;
    
    @Before
    public void init(){
    	
    	List<PointSourceSccCode> sccList = new ArrayList<PointSourceSccCode>();
    	
    	PointSourceSccCode ps1 = new PointSourceSccCode();
    	ps1.setCode("30503506");
    	PointSourceSccCode ps2 = new PointSourceSccCode();
    	ps2.setCode("40500701");
    	ps2.setLastInventoryYear((short)2016);
    	PointSourceSccCode ps3 = new PointSourceSccCode();
    	ps3.setCode("2862000000");
    	
    	sccList.add(ps1);
    	sccList.add(ps2);
    	sccList.add(ps3);
    	
      when(sccRepo.findById("30503506")).thenReturn(Optional.of(ps1));
      when(sccRepo.findById("40500701")).thenReturn(Optional.of(ps2));
      when(sccRepo.findById("2862000000")).thenReturn(Optional.of(ps3));
    	
    }

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void nullEisProgramIdTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();
        testData.getReleasePointAppts().get(0).setPercent((double)49);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_RP_PCT.value()) && errorMap.get(ValidationField.PROCESS_RP_PCT.value()).size() == 1);
    }
    
    @Test
    public void invalidSccCodeTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();
        testData.setSccCode("40500701");
     
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_INFO_SCC.value()) && errorMap.get(ValidationField.PROCESS_INFO_SCC.value()).size() == 1);
    }

    private EmissionsProcess createBaseEmissionsProcess() {

        EmissionsReport report = new EmissionsReport();
        report.setYear(new Short("2020"));
        report.setId(1L);
        
        FacilitySite facility = new FacilitySite();
        facility.setId(1L);
        facility.setEmissionsReport(report);
        
        EmissionsUnit unit = new EmissionsUnit();
        unit.setId(1L);
        unit.setFacilitySite(facility);
        
        EmissionsProcess result = new EmissionsProcess();
        
        OperatingStatusCode os = new OperatingStatusCode();
        os.setCode("OP");
        
        ReleasePointAppt rpa1 = new ReleasePointAppt();
        ReleasePointAppt rpa2 = new ReleasePointAppt();
        rpa1.setPercent((double) 50);
        rpa2.setPercent((double) 50);
        result.getReleasePointAppts().add(rpa1);
        result.getReleasePointAppts().add(rpa2);
        result.setId(1L);
        result.setEmissionsUnit(unit);
        result.setAircraftEngineTypeCode(null);
        result.setOperatingStatusCode(os);
        result.setEmissionsProcessIdentifier("Boiler 001");
        result.setSccCode("30503506");

        return result;
    }
}
