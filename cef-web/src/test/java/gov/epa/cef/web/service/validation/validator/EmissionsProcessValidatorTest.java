package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySourceTypeCode;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.PointSourceSccCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.repository.AircraftEngineTypeCodeRepository;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.EmissionsProcessRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
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
    
    @Mock
    private EmissionsReportRepository reportRepo;
    
    @Mock
    private EmissionsProcessRepository processRepo;
    
    @Mock
    private EmissionRepository emissionRepo;
    
    private Pollutant pollutant;
    
    @Before
    public void init(){
    	
    	pollutant = new Pollutant();
    	pollutant.setPollutantCode("NO3");
    	
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
      
      List<EmissionsReport> erList = new ArrayList<EmissionsReport>();
      MasterFacilityRecord mfr = new MasterFacilityRecord();
      mfr.setId(1L);
      EmissionsReport er1 = new EmissionsReport();
      EmissionsReport er2 = new EmissionsReport();
      er1.setId(1L);
      er2.setId(2L);
      er1.setYear((short) 2018);
      er2.setYear((short) 2020);
      er1.setEisProgramId("1");
      er2.setEisProgramId("1");
      er1.setMasterFacilityRecord(mfr);
      er2.setMasterFacilityRecord(mfr);
      erList.add(er1);
      erList.add(er2);
      
      OperatingStatusCode os = new OperatingStatusCode();
      os.setCode("OP");
      
      EmissionsProcess p1 = new EmissionsProcess();
      p1.setEmissionsProcessIdentifier("Boiler 001");
      p1.setId(2L);
      p1.setOperatingStatusCode(os);
      p1.setStatusYear((short) 2017);
      
      List<Emission> eList = new ArrayList<Emission>();
      Emission previousE1 = new Emission();
      Emission previousE2 = new Emission();
      previousE1.setPollutant(pollutant);
      previousE1.setTotalEmissions(BigDecimal.valueOf(130.00));
      previousE2.setPollutant(pollutant);
      previousE2.setTotalEmissions(BigDecimal.valueOf(10.00));
      eList.add(previousE1);
      eList.add(previousE2);
	    
      when(reportRepo.findByMasterFacilityRecordId(1L)).thenReturn(erList);
      when(processRepo.retrieveByIdentifierParentFacilityYear(
      		"Boiler 001","test_unit",1L,(short) 2018)).thenReturn(Collections.singletonList(p1));
      when(emissionRepo.findAllByProcessIdReportId(2L,1L)).thenReturn(eList);
      
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
        testData.getReleasePointAppts().get(0).setPercent(BigDecimal.valueOf(49));

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
        
        //Verify QA Checks are NOT run when Unit is NOT Operating
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("TS");
        testData.setOperatingStatusCode(opStatCode);
        
        testData.setSccCode("40500701");
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
        
        testData.setSccCode("2862000000");
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
        
        testData.setSccCode("30700599");
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
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
        rpa3.setPercent(BigDecimal.ZERO);
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
        
        // Verify QA Checks are NOT run when Unit is NOT Operating
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("PS");
        testData.setOperatingStatusCode(opStatCode);
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
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
        
        // Verify QA Checks are NOT run when Unit is NOT Operating
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("PS");
        testData.setOperatingStatusCode(opStatCode);
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
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
        OperatingStatusCode releasePointOpStatus = new OperatingStatusCode();
        releasePointOpStatus.setCode("OP");
        rp1.setOperatingStatusCode(releasePointOpStatus);
        rp1.setReleasePointIdentifier("test");
        rp1.setId(5L);
        rpa1.setPercent(BigDecimal.valueOf(101));
        rpa1.setReleasePoint(rp1);
        
        testData.getReleasePointAppts().add(rpa1);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_RP_PCT.value()) && errorMap.get(ValidationField.PROCESS_RP_PCT.value()).size() == 2);
        
        cefContext = createContext();
        rpa1.setPercent(BigDecimal.valueOf(-1));
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
        
        //Verify QA Checks are NOT run when Unit is NOT Operating
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("I");
        testData.setOperatingStatusCode(opStatCode);
        
        cefContext = createContext();
        assertTrue(this.validator.validate(cefContext, testData));
        assertNull(cefContext.result.getErrors());
    }
    
    @Test
    public void reportingPeriodNoEmissionsFailTest() {
    		
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	testData.getReportingPeriods().get(0).setEmissions(null);
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.PROCESS_PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PROCESS_PERIOD_EMISSION.value()).size() == 1);
    }
    
    @Test
    public void reportingPeriodNoEmissionsPassTest() {
    		
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    	
    	cefContext = createContext();
    	testData = createBaseEmissionsProcess();
    	testData.getReportingPeriods().get(0).setEmissions(null);
    	testData.getOperatingStatusCode().setCode("TS");
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    	
    }
    
    /**
     * There should be no errors when total emission value for pollutant code is not the same as value from previous report year.
     * There should be one error when total emission value for pollutant code for current report year is
     * the same as the emission value for previous report year
     */
    @Test
    public void copiedEmissions_Warning_Test() {
    		
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	List<Emission> eList2 = new ArrayList<Emission>();
    	Emission test1 = new Emission();
    	test1.setPollutant(pollutant);
    	test1.setTotalEmissions(BigDecimal.valueOf(150.00));
    	eList2.add(test1);
    	
    	when(emissionRepo.findAllByProcessIdReportId(testData.getId(),testData.getEmissionsUnit().getFacilitySite().getEmissionsReport().getId())).thenReturn(eList2);
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    	
    	cefContext = createContext();
    	test1.setTotalEmissions(BigDecimal.valueOf(130.00));
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1);
    }
    
    @Test
    public void releasePointApportionedAndNotOperatingFailTest() {
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	
    	List<ReleasePointAppt> appts = new ArrayList<>();
    	ReleasePointAppt rpa1 = new ReleasePointAppt();
        ReleasePoint rp1 = new ReleasePoint();
        OperatingStatusCode releasePointOpStatus = new OperatingStatusCode();
        releasePointOpStatus.setCode("PS");
        rp1.setOperatingStatusCode(releasePointOpStatus);
        rp1.setReleasePointIdentifier("test");
        rp1.setId(5L);
        rpa1.setPercent(BigDecimal.valueOf(100.0));
        rpa1.setReleasePoint(rp1);
        rpa1.setEmissionsProcess(testData);
        appts.add(rpa1);
        testData.setReleasePointAppts(appts);
        
        assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.PROCESS_RP.value()) && errorMap.get(ValidationField.PROCESS_RP.value()).size() == 1);
    }
    
    @Test
    public void releasePointApportionedAndOperatingPassTest() {
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	
    	List<ReleasePointAppt> appts = new ArrayList<>();
    	ReleasePointAppt rpa1 = new ReleasePointAppt();
        ReleasePoint rp1 = new ReleasePoint();
        OperatingStatusCode releasePointOpStatus = new OperatingStatusCode();
        releasePointOpStatus.setCode("OP");
        rp1.setOperatingStatusCode(releasePointOpStatus);
        rp1.setReleasePointIdentifier("test");
        rp1.setId(5L);
        rpa1.setPercent(BigDecimal.valueOf(100.0));
        rpa1.setReleasePoint(rp1);
        rpa1.setEmissionsProcess(testData);
        appts.add(rpa1);
        testData.setReleasePointAppts(appts);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void releasePointApportionedNotOperatingAndProcessNotOperatingPassTest() {
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	
    	List<ReleasePointAppt> appts = new ArrayList<>();
    	ReleasePointAppt rpa1 = new ReleasePointAppt();
        ReleasePoint rp1 = new ReleasePoint();
        OperatingStatusCode opStatus = new OperatingStatusCode();
        opStatus.setCode("PS");
        testData.setOperatingStatusCode(opStatus);
        rp1.setOperatingStatusCode(opStatus);
        rp1.setReleasePointIdentifier("test");
        rp1.setId(5L);
        rpa1.setPercent(BigDecimal.valueOf(100.0));
        rpa1.setReleasePoint(rp1);
        rpa1.setEmissionsProcess(testData);
        appts.add(rpa1);
        testData.setReleasePointAppts(appts);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void previousOpYearCurrentShutdownYear() {
    	// pass when previous and current op status is OP
    	CefValidatorContext cefContext = createContext();
    	EmissionsProcess testData = createBaseEmissionsProcess();
    	testData.setStatusYear((short) 2017);
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    	
    	// fail when previous op status is OP, current op status is PS/TS, and both have same status years
    	cefContext = createContext();
    	testData.getOperatingStatusCode().setCode("PS");
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    	
    	// fail when previous op status is OP, current op status is PS/TS, and current op status year is prior to previous year
    	cefContext = createContext();
    	testData.setStatusYear((short) 2016);
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.PROCESS_STATUS_YEAR.value()) && errorMap.get(ValidationField.PROCESS_STATUS_YEAR.value()).size() == 1);
    	
    	// pass when facility source type code is landfill, QA is not checked if it is landfill
    	cefContext = createContext();
    	testData.getEmissionsUnit().getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getFacilitySourceTypeCode().setCode("104");
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    	
    	// pass when previous op status is OP, current op status is PS/TS, and current op status year is after previous year
    	cefContext = createContext();
    	testData.setStatusYear((short) 2020);
    	testData.getOperatingStatusCode().setCode("PS");
    	testData.getEmissionsUnit().getFacilitySite().getEmissionsReport().getMasterFacilityRecord().getFacilitySourceTypeCode().setCode("137");
    	
    	assertTrue(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    

    private EmissionsProcess createBaseEmissionsProcess() {

        MasterFacilityRecord mfr = new MasterFacilityRecord();
        mfr.setId(1L);
        FacilitySourceTypeCode fstc = new FacilitySourceTypeCode();
        fstc.setCode("137");
        mfr.setFacilitySourceTypeCode(fstc);
        
        EmissionsReport report = new EmissionsReport();
        report.setYear(new Short("2019"));
        report.setId(3L);
        report.setEisProgramId("1");
        report.setMasterFacilityRecord(mfr);
        
        FacilitySite facility = new FacilitySite();
        facility.setId(1L);
        facility.setEmissionsReport(report);
        
        EmissionsUnit unit = new EmissionsUnit();
        unit.setId(1L);
        unit.setFacilitySite(facility);
        unit.setUnitIdentifier("test_unit");
        facility.getEmissionsUnits().add(unit);

        EmissionsProcess result = new EmissionsProcess();
        
        OperatingStatusCode os = new OperatingStatusCode();
        os.setCode("OP");
        
        ReportingPeriod rperiod1 = new ReportingPeriod();
        rperiod1.setId(1L);
        
        Emission e1 = new Emission();
        e1.setId(1L);
        e1.setReportingPeriod(rperiod1);
        Emission e2 = new Emission();
        e2.setId(2L);
        e2.setReportingPeriod(rperiod1);
        rperiod1.getEmissions().add(e1);
        rperiod1.getEmissions().add(e2);
        
        ReleasePoint rp1 = new ReleasePoint();
        ReleasePoint rp2 = new ReleasePoint();
        rp1.setId(1L);
        rp2.setId(2L);
        rp1.setOperatingStatusCode(os);
        rp2.setOperatingStatusCode(os);
        
        ReleasePointAppt rpa1 = new ReleasePointAppt();
        ReleasePointAppt rpa2 = new ReleasePointAppt();
        rpa1.setReleasePoint(rp1);
        rpa2.setReleasePoint(rp2);
        rpa1.setEmissionsProcess(result);
        rpa2.setEmissionsProcess(result);
        rpa1.setPercent(BigDecimal.valueOf(50));
        rpa2.setPercent(BigDecimal.valueOf(50));
        rpa1.setId(1L);
        rpa2.setId(2L);
        result.setStatusYear((short) 2021);
        result.getReleasePointAppts().add(rpa1);
        result.getReleasePointAppts().add(rpa2);
        result.setId(1L);
        result.setEmissionsUnit(unit);
        result.setAircraftEngineTypeCode(null);
        result.setOperatingStatusCode(os);
        result.getReportingPeriods().add(rperiod1);
        result.setEmissionsProcessIdentifier("Boiler 001");
        result.setSccCode("30503506");

        return result;
    }
}
