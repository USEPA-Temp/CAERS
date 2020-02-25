package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.ContactTypeCode;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.FipsStateCode;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.repository.EmissionRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.FacilitySiteValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FacilitySiteValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private FacilitySiteValidator validator;
    
    @Mock
    private EmissionsReportRepository reportRepo;
    
    @Mock
    private EmissionRepository emissionRepo;
    
    private Pollutant pollutant;
    
    @Before
    public void init() {
    	
      pollutant = new Pollutant();
      pollutant.setPollutantCode("NO3");
      
      List<EmissionsReport> erList = new ArrayList<EmissionsReport>();
      EmissionsReport er1 = new EmissionsReport();
      EmissionsReport er2 = new EmissionsReport();
      EmissionsReport er3 = new EmissionsReport();
      er1.setId(1L);
      er2.setId(2L);
      er3.setId(3L);
      er1.setYear((short) 2018);
      er2.setYear((short) 2019);
      er3.setYear((short) 2016);
      er1.setEisProgramId("1");
      er2.setEisProgramId("1");
      er3.setEisProgramId("1");
      erList.add(er1);
      erList.add(er2);
      erList.add(er3);
      
      when(reportRepo.findByEisProgramId("1")).thenReturn(erList);
      
      FacilitySite fs = new FacilitySite();
      fs.setEmissionsReport(er1);
      
      EmissionsUnit eu = new EmissionsUnit();
      eu.setFacilitySite(fs);
      fs.getEmissionsUnits().add(eu);
      
      EmissionsProcess ep = new EmissionsProcess();
      ep.setEmissionsUnit(eu);
      ep.setEmissionsProcessIdentifier("ep-test-1");
      eu.getEmissionsProcesses().add(ep);
      
      ReportingPeriod rp = new ReportingPeriod();
      rp.setId(1L);
      rp.setEmissionsProcess(ep);
      ep.getReportingPeriods().add(rp);
      
      List<Emission> eList = new ArrayList<Emission>();
      Emission e1 = new Emission();
      Emission e2 = new Emission();
      e1.setId(2L);
      e2.setId(3L);
      e1.setPollutant(pollutant);
      e2.setPollutant(pollutant);
      e1.setTotalEmissions(new BigDecimal(123.000));
      e1.setReportingPeriod(rp);
      e2.setReportingPeriod(rp);
      eList.add(e1);
      eList.add(e2);
      
      when(emissionRepo.findAllByReportId(1L)).thenReturn(eList);
    }

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void nullEisProgramIdTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setEisProgramId(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_EIS_ID.value()) && errorMap.get(ValidationField.FACILITY_EIS_ID.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateOperationStatusTypeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        OperatingStatusCode opStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("TS");
        testData.setOperatingStatusCode(opStatusCode);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_STATUS.value()) && errorMap.get(ValidationField.FACILITY_STATUS.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateStatusYearRangeTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        testData.setStatusYear((short) 1900);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.setStatusYear((short) 2050);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void simpleValidateStatusYearRangeFailTest() {
	      
        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        testData.setStatusYear((short) 1800);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_STATUS.value()) && errorMap.get(ValidationField.FACILITY_STATUS.value()).size() == 1);
        
        cefContext = createContext();
        testData.setStatusYear((short) 2051);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_STATUS.value()) && errorMap.get(ValidationField.FACILITY_STATUS.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateFacilityNAICSPrimaryFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        for (FacilityNAICSXref naicsFlag: testData.getFacilityNAICS()) {
        	naicsFlag.setPrimaryFlag(false);
        }
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_NAICS.value()) && errorMap.get(ValidationField.FACILITY_NAICS.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateFacilityNAICSCodeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setFacilityNAICS(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_NAICS.value()) && errorMap.get(ValidationField.FACILITY_NAICS.value()).size() == 2);
    }
    
    @Test
    public void simpleValidateContactTypeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();

        for (FacilitySiteContact contact: testData.getContacts()) {
        	ContactTypeCode contactTypeCode = new ContactTypeCode();
        	contactTypeCode.setCode("FAC");
        	contact.setType(contactTypeCode);
        }
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void simpleValidateContactPostalCodeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.getContacts().get(0).setPostalCode("1234567890");
        testData.getContacts().get(0).setMailingPostalCode("1234567890");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
    }
    
    @Test
    public void simpleValidateFacilityPostalCodeFailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setPostalCode("1234567890");
        testData.setMailingPostalCode("1234567890");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);
    }
    
    @Test
    public void nullFacilityContactEmissionsInventoryTypeTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        testData.setContacts(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_CONTACT.value()) && errorMap.get(ValidationField.FACILITY_CONTACT.value()).size() == 1);
    }
    
    @Test
    public void nullFacilityContactEmailTest() {

        CefValidatorContext cefContext = createContext();
        FacilitySite testData = createBaseFacilitySite();
        
        for (FacilitySiteContact contact: testData.getContacts()) {
        	contact.setEmail(null);
        };

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.FACILITY_CONTACT.value()) && errorMap.get(ValidationField.FACILITY_CONTACT.value()).size() == 1);
    }
    
    /**
     * There should be no errors when total emission value for pollutant code is not the same as value from previous report year.
     * There should be one error when total emission value for pollutant code for current report year is
     * the same as the emission value for previous report year
     */
    @Test
    public void copiedEmissions_Warning_Test() {

      CefValidatorContext cefContext = createContext();
      FacilitySite testData = createBaseFacilitySite();
      EmissionsReport er2 = new EmissionsReport();
      er2.setId(2L);
      er2.setYear((short) 2019);
      er2.setEisProgramId("1");
      
      testData.setEmissionsReport(er2);
      
      EmissionsUnit eu2 = new EmissionsUnit();
      eu2.setFacilitySite(testData);
      testData.getEmissionsUnits().add(eu2);
      
      EmissionsProcess ep2 = new EmissionsProcess();
      ep2.setEmissionsUnit(eu2);
      ep2.setEmissionsProcessIdentifier("ep-test-1");
      eu2.getEmissionsProcesses().add(ep2);
      
      ReportingPeriod rp2 = new ReportingPeriod();
      rp2.setId(2L);
      rp2.setEmissionsProcess(ep2);
      ep2.getReportingPeriods().add(rp2);
      
      List<Emission> eList2 = new ArrayList<Emission>();
      Emission e3 = new Emission();
      e3.setId(4L);
      e3.setPollutant(pollutant);
      e3.setTotalEmissions(new BigDecimal(130.000));
      e3.setReportingPeriod(rp2);
      eList2.add(e3);
      
      when(emissionRepo.findAllByReportId(2L)).thenReturn(eList2);
      
      assertTrue(this.validator.validate(cefContext, testData));
      assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
      
      cefContext = createContext();
      e3.setTotalEmissions(new BigDecimal(123.000));
      
      when(emissionRepo.findAllByReportId(1L)).thenReturn(eList2);
      
      assertFalse(this.validator.validate(cefContext, testData));
      assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

      Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
      assertTrue(errorMap.containsKey(ValidationField.EMISSION_TOTAL_EMISSIONS.value()) && errorMap.get(ValidationField.EMISSION_TOTAL_EMISSIONS.value()).size() == 1);

    }


    private FacilitySite createBaseFacilitySite() {

        FacilitySite result = new FacilitySite();
        result.setEisProgramId("1");
        result.setStatusYear(null);
        result.setPostalCode("31750");
        result.setMailingPostalCode("31750");
        
        EmissionsReport er = new EmissionsReport();
        er.setId(2L);
        er.setYear(new Short("2019"));
        er.setEisProgramId("1");
        result.setEmissionsReport(er);
        
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        result.setOperatingStatusCode(opStatCode);
        
        ContactTypeCode contactTypeCode = new ContactTypeCode();
        contactTypeCode.setCode("EI");
        
        FipsStateCode stateCode = new FipsStateCode();
        stateCode.setCode("13");
        stateCode.setUspsCode("GA");
        
        FipsStateCode mailingStateCode = new FipsStateCode();
        mailingStateCode.setCode("13");     
        mailingStateCode.setUspsCode("GA");
        
        List<FacilitySiteContact> contactList = new ArrayList<FacilitySiteContact>();
        FacilitySiteContact contact = new FacilitySiteContact();
        
        contact.setType(contactTypeCode);
        contact.setFirstName("Jane");
        contact.setLastName("Doe");
        contact.setEmail("jane.doe@test.com");
        contact.setPhone("1234567890");
        contact.setPhoneExt("");
        contact.setStreetAddress("123 Test Street");
        contact.setCity("Fitzgerald");
        contact.setStateCode(stateCode);
        contact.setPostalCode("31750");
        contact.setMailingPostalCode("31750");
        contact.setCounty("Whitfield");
        contactList.add(contact);
        
        result.setContacts(contactList);
        
        List<FacilityNAICSXref> naicsList = new ArrayList<FacilityNAICSXref>();
        FacilityNAICSXref facilityNaics = new FacilityNAICSXref();
      	
        NaicsCode naics = new NaicsCode();
        naics.setCode(332116);
        naics.setDescription("Metal Stamping");
        
        facilityNaics.setNaicsCode(naics);
        facilityNaics.setPrimaryFlag(true);
        naicsList.add(facilityNaics);
        
        result.setFacilityNAICS(naicsList);

        return result;
    }
    
}
