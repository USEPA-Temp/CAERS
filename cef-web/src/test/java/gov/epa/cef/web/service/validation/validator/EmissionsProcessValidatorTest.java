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

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.AircraftEngineTypeCode;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.repository.AircraftEngineTypeCodeRepository;
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
    
    @Mock
    private AircraftEngineTypeCodeRepository aircraftEngCodeRepo;
    
    @Before
    public void init(){
    	
    	List<PointSourceSccCode> sccList = new ArrayList<PointSourceSccCode>();
    	
    	PointSourceSccCode ps1 = new PointSourceSccCode();
    	ps1.setCode("30503506"); // point source scc code
    	PointSourceSccCode ps2 = new PointSourceSccCode();
    	ps2.setCode("40500701"); // point source scc code
    	ps2.setLastInventoryYear((short)2016);
    	PointSourceSccCode ps3 = new PointSourceSccCode();
    	ps3.setCode("30700599"); // point source scc code
    	ps3.setLastInventoryYear((short)2019);
    	PointSourceSccCode ps4 = new PointSourceSccCode();
    	ps4.setCode("2275001000"); // point source scc code
    	PointSourceSccCode ps5 = new PointSourceSccCode();
    	ps5.setCode("2275050012"); // point source scc code
    	
    	sccList.add(ps1);
    	sccList.add(ps2);
    	sccList.add(ps3);
    	sccList.add(ps4);
    	sccList.add(ps5);
    	
      when(sccRepo.findById("30503506")).thenReturn(Optional.of(ps1));
      when(sccRepo.findById("40500701")).thenReturn(Optional.of(ps2));
      when(sccRepo.findById("2862000000")).thenReturn(Optional.empty());
      when(sccRepo.findById("30700599")).thenReturn(Optional.of(ps3));
      when(sccRepo.findById("2275001000")).thenReturn(Optional.of(ps4));
      when(sccRepo.findById("2275050012")).thenReturn(Optional.of(ps5));
      
      List<AircraftEngineTypeCode> aircraftList = new ArrayList<AircraftEngineTypeCode>();
      
      AircraftEngineTypeCode aircraft1 = new AircraftEngineTypeCode();
      aircraft1.setCode("1322");
      aircraft1.setScc("2275001000");
      AircraftEngineTypeCode aircraft2 = new AircraftEngineTypeCode();
      aircraft2.setCode("1850");
      aircraft2.setScc("2275050012");
      
      aircraftList.add(aircraft1);
      aircraftList.add(aircraft2);
      
      when(aircraftEngCodeRepo.findById("1322")).thenReturn(Optional.of(aircraft1));
      when(aircraftEngCodeRepo.findById("1850")).thenReturn(Optional.of(aircraft2));
    	
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
        
        cefContext = createContext();
        testData.setSccCode("2862000000");
     
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_INFO_SCC.value()) && errorMap.get(ValidationField.PROCESS_INFO_SCC.value()).size() == 1);
        
        cefContext = createContext();
        testData.setSccCode("30700599");
     
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_INFO_SCC.value()) && errorMap.get(ValidationField.PROCESS_INFO_SCC.value()).size() == 1);
    }
    
    @Test
    public void processHasReleasePointFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();
        testData.setReleasePointAppts(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_RP.value()) && errorMap.get(ValidationField.PROCESS_RP.value()).size() == 1);
    }
    
    @Test
    public void processHasReleasePointPassTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void processHasReleasePointDuplicateFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();
        ReleasePoint rp2 = new ReleasePoint();
        rp2.setId(2L);
        ReleasePointAppt rpa3 = new ReleasePointAppt();
        rpa3.setEmissionsProcess(testData);
        rpa3.setPercent(0D);
        rpa3.setReleasePoint(rp2);
        testData.getReleasePointAppts().add(rpa3);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_RP.value()) && errorMap.get(ValidationField.PROCESS_RP.value()).size() == 1);
    }
    
    @Test
    public void aircraftCodeRequiredFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();
        
        testData.setSccCode("2275050012");
        testData.setAircraftEngineTypeCode(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_AIRCRAFT_CODE.value()) && errorMap.get(ValidationField.PROCESS_AIRCRAFT_CODE.value()).size() == 1);
    }
    
    @Test
    public void aircraftCodeValidForSccFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();
        
        AircraftEngineTypeCode aircraft = new AircraftEngineTypeCode();
        aircraft.setCode("1850");
        aircraft.setScc("2275050012");
        testData.setAircraftEngineTypeCode(aircraft);
        testData.setSccCode("2275001000");

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_AIRCRAFT_CODE.value()) && errorMap.get(ValidationField.PROCESS_AIRCRAFT_CODE.value()).size() == 1);
    }
    
    @Test
    public void aircraftCodePassTest() {
    		
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	
    	AircraftEngineTypeCode aircraft = new AircraftEngineTypeCode();
    	aircraft.setCode("1322");
    	aircraft.setScc("2275001000");
    	testData.setAircraftEngineTypeCode(aircraft);
    	testData.setSccCode("2275001000");
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void simpleValidateReleasePointApportionmentRangeFailTest() {
	      
        CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
        ReleasePointAppt rpa1 = new ReleasePointAppt();
        ReleasePoint rp1 = new ReleasePoint();
        rp1.setReleasePointIdentifier("test");
        rp1.setId(5L);
        rpa1.setPercent(101D);
        rpa1.setReleasePoint(rp1);
        
        testData.getReleasePointAppts().add(rpa1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_RP_PCT.value()) && errorMap.get(ValidationField.PROCESS_RP_PCT.value()).size() == 2);
        
        cefContext = createContext();
        rpa1.setPercent(-1D);
        testData.getReleasePointAppts().add(rpa1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 4);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_RP_PCT.value()) && errorMap.get(ValidationField.PROCESS_RP_PCT.value()).size() == 3);
    }
    
    @Test
    public void aircraftCodeAndSccDuplicateCombinationFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsProcess testData = createBaseEmissionsProcess();
        EmissionsProcess ep1 = createBaseEmissionsProcess();

        
        AircraftEngineTypeCode aircraft = new AircraftEngineTypeCode();
        aircraft.setCode("1322");
        aircraft.setScc("2275001000");
        
        testData.setAircraftEngineTypeCode(aircraft);
        testData.setSccCode("2275001000");
        testData.setId(1L);
        ep1.setAircraftEngineTypeCode(aircraft);
        ep1.setSccCode("2275001000");
        ep1.setId(2L);
        
        testData.getEmissionsUnit().getEmissionsProcesses().add(ep1);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_AIRCRAFT_CODE_AND_SCC_CODE.value()) && errorMap.get(ValidationField.PROCESS_AIRCRAFT_CODE_AND_SCC_CODE.value()).size() == 1);
    }
    

    private EmissionsProcess createBaseEmissionsProcess() {

        EmissionsReport report = new EmissionsReport();
        report.setYear(new Short("2019"));
        report.setId(1L);
        
        FacilitySite facility = new FacilitySite();
        facility.setId(1L);
        facility.setEmissionsReport(report);
        
        EmissionsUnit unit = new EmissionsUnit();
        unit.setId(1L);
        unit.setFacilitySite(facility);
        facility.getEmissionsUnits().add(unit);

        EmissionsProcess result = new EmissionsProcess();
        
        OperatingStatusCode os = new OperatingStatusCode();
        os.setCode("OP");
        
        ReleasePoint rp1 = new ReleasePoint();
        ReleasePoint rp2 = new ReleasePoint();
        rp1.setId(1L);
        rp2.setId(2L);
        
        ReleasePointAppt rpa1 = new ReleasePointAppt();
        ReleasePointAppt rpa2 = new ReleasePointAppt();
        rpa1.setReleasePoint(rp1);
        rpa2.setReleasePoint(rp2);
        rpa1.setEmissionsProcess(result);
        rpa2.setEmissionsProcess(result);
        rpa1.setPercent((double) 50);
        rpa2.setPercent((double) 50);
        rpa1.setId(1L);
        rpa2.setId(2L);
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
