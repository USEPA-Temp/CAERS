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
import gov.epa.cef.web.domain.FacilitySite;
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
    public void uniqueIdentifierCheckFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        testData.setUnitIdentifier("test");

        EmissionsUnit em1 = new EmissionsUnit();
        em1.setUnitIdentifier("test");
        em1.setId(2L);

        testData.getFacilitySite().getEmissionsUnits().add(em1);
        testData.getFacilitySite().getEmissionsUnits().add(testData);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_IDENTIFIER.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_IDENTIFIER.value()).size() == 1);
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
    public void legacyUomFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        testData.getUnitOfMeasureCode().setLegacy(true);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_UOM.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_UOM.value()).size() == 1);
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
    
    /**
     * There should be no errors when emissions unit operating status is not PS.
     * There should be one error when emissions unit operating status is PS and emission process operating status is not PS.
     * There should be no errors when emissions unit operating status is PS and emission process operating status is PS.
     */
    @Test
    public void emissionUnitOperatingStatusPSEmissionProcessStatusTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        EmissionsProcess ep = new EmissionsProcess();
        ep.setOperatingStatusCode(opStatCode);
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_STATUS_CODE.value()) && errorMap.get(ValidationField.PROCESS_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        ep.getOperatingStatusCode().setCode("PS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when emissions unit operating status is not TS or PS.
     * There should be one error when emissions unit operating status is TS and emission process operating status is not PS and not TS.
     * There should be no errors when emissions unit operating status is TS and emission process operating status is PS or TS.
     */
    @Test
    public void emissionUnitOperatingStatusTSEmissionUnitStatusTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        EmissionsProcess ep = new EmissionsProcess();
        ep.setOperatingStatusCode(opStatCode);
        ep.setEmissionsUnit(testData);
        testData.getEmissionsProcesses().add(ep);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("TS");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PROCESS_STATUS_CODE.value()) && errorMap.get(ValidationField.PROCESS_STATUS_CODE.value()).size() == 1);
        
        cefContext = createContext();
        ep.getOperatingStatusCode().setCode("PS");
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
    
    /**
     * There should be no errors when emissions unit design capacity uom is a valid uom for eis.
     * There should be no error when emissions unit design capacity uom is not a valid uom for eis and the unit operating status is PS.
     * There should be one error when emissions unit design capacity uom is not a valid uom for eis.
     */
    @Test
    public void designCapacityUomFailTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsUnit testData = createBaseEmissionsUnit();
        testData.getUnitOfMeasureCode().setUnitDesignCapacity(true);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("PS");
        testData.getUnitOfMeasureCode().setUnitDesignCapacity(false);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.getOperatingStatusCode().setCode("OP");
        testData.getUnitOfMeasureCode().setUnitDesignCapacity(false);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.EMISSIONS_UNIT_UOM.value()) && errorMap.get(ValidationField.EMISSIONS_UNIT_UOM.value()).size() == 1);
    }
    

    private EmissionsUnit createBaseEmissionsUnit() {

        EmissionsUnit result = new EmissionsUnit();

        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        UnitTypeCode utc = new UnitTypeCode();
        utc.setCode("100");
        
        UnitMeasureCode capUom = new UnitMeasureCode();
        capUom.setCode("CURIE");
        
        FacilitySite facility = new FacilitySite();
        facility.setId(1L);
        facility.setOperatingStatusCode(opStatCode);
        
        result.setStatusYear((short) 2000);
        result.setOperatingStatusCode(opStatCode);
        result.setUnitTypeCode(utc);
        result.setUnitOfMeasureCode(capUom);
        result.setFacilitySite(facility);
        result.setDesignCapacity(BigDecimal.valueOf(100));
        result.setId(1L);
        
        return result;
    }
}
