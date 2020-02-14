package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.domain.UnitTypeCode;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsUnitValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsUnitValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private EmissionsUnitValidator validator;

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    @Test
    public void simpleValidateOperationStatusTypeFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        
        OperatingStatusCode opStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("TS");
        testData.setOperatingStatusCode(opStatusCode);
        testData.setStatusYear(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_CODE.value()).size() == 1);
    }
    
    @Test
    public void simpleValidateStatusYearRangeTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        
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
        EmissionsUnit testData = createBaseEmissionsUnit();
        
        testData.setStatusYear((short) 1800);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()).size() == 1);
        
        cefContext = createContext();
        testData.setStatusYear((short) 2051);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_STATUS_YEAR.value()).size() == 1);
    }
    
    @Test
    public void designCapacityCheckCalculationFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();

        cefContext = createContext();
        testData.setDesignCapacity(null);
        testData.setUnitOfMeasureCode(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
    }
    
    @Test
    public void designCapacityFailTest() {
    	
    	CefValidatorContext cefContext = createContext();
    	EmissionsUnit testData = createBaseEmissionsUnit();
    	testData.setDesignCapacity(BigDecimal.valueOf(0.0001));
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null || cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
    	
    	cefContext = createContext();
    	testData.setDesignCapacity(BigDecimal.valueOf(200000000));
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null || cefContext.result.getErrors().size() == 1);
    }
    
    @Test
    public void designCapacityAndUomRequiredFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        UnitTypeCode utc = new UnitTypeCode();
        utc.setCode("200");
        testData.setUnitTypeCode(utc);
        testData.setDesignCapacity(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
        
        cefContext = createContext();
        testData.setUnitOfMeasureCode(null);
        testData.setDesignCapacity(BigDecimal.valueOf(1000.0));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_CAPACITY.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_CAPACITY.value()).size() == 1);
    }
    
    @Test
    public void duplicateProcessIdentifierFailTest() {
    	
    	CefValidatorContext cefContext = createContext();
    	EmissionsUnit testData = createBaseEmissionsUnit();
    	EmissionsProcess ep1 = new EmissionsProcess();
    	EmissionsProcess ep2 = new EmissionsProcess();
    	ep1.setEmissionsProcessIdentifier("ABC");
    	ep2.setEmissionsProcessIdentifier("ABC");
    	ep1.setEmissionsUnit(testData);
    	ep2.setEmissionsUnit(testData);
    	testData.getEmissionsProcesses().add(ep1);
    	testData.getEmissionsProcesses().add(ep2);
    	
    	assertFalse(this.validator.validate(cefContext, testData));
    	assertTrue(cefContext.result.getErrors() != null || cefContext.result.getErrors().size() == 1);
    	
    	Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
    	assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_PROCESS.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_PROCESS.value()).size() == 1);
    }
    

    private EmissionsUnit createBaseEmissionsUnit() {

        EmissionsUnit result = new EmissionsUnit();

        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        UnitTypeCode utc = new UnitTypeCode();
        utc.setCode("100");
        
        UnitMeasureCode capUom = new UnitMeasureCode();
        capUom.setCode("CURIE");
        
        result.setStatusYear((short) 2000);
        result.setOperatingStatusCode(opStatCode);
        result.setUnitTypeCode(utc);
        result.setUnitOfMeasureCode(capUom);
        result.setDesignCapacity(BigDecimal.valueOf(100));
        
        return result;
    }
}
