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

import gov.epa.cef.web.domain.CalculationMaterialCode;
import gov.epa.cef.web.domain.CalculationParameterTypeCode;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsOperatingTypeCode;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.ReportingPeriodValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReportingPeriodValidatorTest extends BaseValidatorTest {
	
	@InjectMocks
	private ReportingPeriodValidator validator;
	
	@Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
	
	@Test
    public void operatingTypeCodeFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        testData.setEmissionsOperatingTypeCode(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_OPERATING_TYPE_CODE.value()) && errorMap.get(ValidationField.PERIOD_OPERATING_TYPE_CODE.value()).size() == 1);
	}
	
	@Test
    public void calculationParameterValueFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        testData.setCalculationParameterValue(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_CALC_VALUE.value()) && errorMap.get(ValidationField.PERIOD_CALC_VALUE.value()).size() == 1);

        cefContext = createContext();
        testData.setCalculationParameterValue(BigDecimal.valueOf(-1));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_CALC_VALUE.value()) && errorMap.get(ValidationField.PERIOD_CALC_VALUE.value()).size() == 1);
	}
	
	@Test
    public void calculationMaterialCodeFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        testData.setCalculationMaterialCode(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_CALC_MAT_CODE.value()) && errorMap.get(ValidationField.PERIOD_CALC_MAT_CODE.value()).size() == 1);
	}
	
	@Test
    public void calculationParameterTypeCodeFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        testData.setCalculationParameterTypeCode(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_CALC_TYPE_CODE.value()) && errorMap.get(ValidationField.PERIOD_CALC_TYPE_CODE.value()).size() == 1);
	}
	
	@Test
    public void calculationParameterUomCodeFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        UnitMeasureCode uom = new UnitMeasureCode();
        uom.setLegacy(true);
        uom.setCode("BTU/HR");
        testData.setCalculationParameterUom(uom);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_CALC_UOM.value()) && errorMap.get(ValidationField.PERIOD_CALC_UOM.value()).size() == 1);
        
        cefContext = createContext();
        testData.setCalculationParameterUom(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_CALC_UOM.value()) && errorMap.get(ValidationField.PERIOD_CALC_UOM.value()).size() == 1);
	}
	
	@Test
    public void duplicatePollutantFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        Pollutant p = new Pollutant();
		p.setPollutantCode("PM10-FIL");
		Emission e1 = new Emission();
		e1.setPollutant(p);
		testData.getEmissions().add(e1);
		Emission e2 = new Emission();
		e2.setPollutant(p);
		testData.getEmissions().add(e2);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
	}
	
	@Test
    public void pm_FilExceedPM_PriFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        Pollutant p1 = new Pollutant();
		p1.setPollutantCode("PM10-FIL");
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("PM10-PRI");
		Emission e1 = new Emission();
		e1.setPollutant(p1);
		e1.setCalculatedEmissionsTons(BigDecimal.valueOf(100));
		testData.getEmissions().add(e1);
		Emission e2 = new Emission();
		e2.setPollutant(p2);
		e2.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e2);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
        
        cefContext = createContext();
        p1.setPollutantCode("PM25-FIL");
		p2.setPollutantCode("PM25-PRI");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
	}
	
	
	@Test
    public void pmConExceedPM_PriFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        Pollutant p1 = new Pollutant();
		p1.setPollutantCode("PM-CON");
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("PM25-PRI");
		Emission e1 = new Emission();
		e1.setPollutant(p1);
		e1.setCalculatedEmissionsTons(BigDecimal.valueOf(100));
		testData.getEmissions().add(e1);
		Emission e2 = new Emission();
		e2.setPollutant(p2);
		e2.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e2);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
        
        cefContext = createContext();
        p1.setPollutantCode("PM-CON");
		p2.setPollutantCode("PM10-PRI");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
	}
	
	@Test
    public void pmConAndPM_FilEqualPM_PriFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        Pollutant p1 = new Pollutant();
		p1.setPollutantCode("PM-CON");
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("PM10-FIL");
		Pollutant p3 = new Pollutant();
		p3.setPollutantCode("PM10-PRI");
		Emission e1 = new Emission();
		e1.setPollutant(p1);
		e1.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e1);
		Emission e2 = new Emission();
		e2.setPollutant(p2);
		e2.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e2);
		Emission e3 = new Emission();
		e3.setPollutant(p3);
		e3.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e3);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
        
        cefContext = createContext();
        p2.setPollutantCode("PM25-FIL");
		p3.setPollutantCode("PM25-PRI");
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
	}
	
	@Test
    public void pm25PriExceedPM10PriFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        Pollutant p1 = new Pollutant();
		p1.setPollutantCode("PM10-PRI");
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("PM25-PRI");
		Emission e1 = new Emission();
		e1.setPollutant(p1);
		e1.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e1);
		Emission e2 = new Emission();
		e2.setPollutant(p2);
		e2.setCalculatedEmissionsTons(BigDecimal.valueOf(100));
		testData.getEmissions().add(e2);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
	}
	
	@Test
    public void pm25FilExceedPM10FilFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        Pollutant p1 = new Pollutant();
		p1.setPollutantCode("PM10-FIL");
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("PM25-FIL");
		Emission e1 = new Emission();
		e1.setPollutant(p1);
		e1.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e1);
		Emission e2 = new Emission();
		e2.setPollutant(p2);
		e2.setCalculatedEmissionsTons(BigDecimal.valueOf(100));
		testData.getEmissions().add(e2);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
	}
	
	@Test
    public void fluoridesFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        Pollutant p1 = new Pollutant();
		p1.setPollutantCode("16984488");
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("7664393");
		Emission e1 = new Emission();
		e1.setPollutant(p1);
		e1.setCalculatedEmissionsTons(BigDecimal.valueOf(10));
		testData.getEmissions().add(e1);
		Emission e2 = new Emission();
		e2.setPollutant(p2);
		e2.setCalculatedEmissionsTons(BigDecimal.valueOf(100));
		testData.getEmissions().add(e2);
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_EMISSION.value()) && errorMap.get(ValidationField.PERIOD_EMISSION.value()).size() == 1);
	}
	
	@Test
    public void fuelUsePassTest() {

        CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        UnitMeasureCode uom = new UnitMeasureCode();
        uom.setCode("BTU");
        
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
        testData.setFuelUseMaterialCode(cmc);
        testData.setFuelUseUom(uom);
        testData.setFuelUseValue(BigDecimal.valueOf(100));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
	
	@Test
    public void fuelUseValuesFailTest() {
		
		CefValidatorContext cefContext = createContext();
        ReportingPeriod testData = createBaseReportingPeriod();
        
        CalculationMaterialCode cmc = new CalculationMaterialCode();
        cmc.setFuelUseMaterial(true);
		cmc.setCode("794");
		
        testData.setFuelUseMaterialCode(cmc);
        testData.setFuelUseUom(null);
        testData.setFuelUseValue(BigDecimal.valueOf(100));
        
        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.PERIOD_FUEL_USE_VALUES.value()) && errorMap.get(ValidationField.PERIOD_FUEL_USE_VALUES.value()).size() == 1);
        
	}
	
	private ReportingPeriod createBaseReportingPeriod() {
		
		OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
		ReportingPeriod result = new ReportingPeriod();
		result.setEmissionsProcess(new EmissionsProcess());
		result.getEmissionsProcess().setOperatingStatusCode(opStatCode);
		result.setId(1L);
		ReportingPeriodCode rpc = new ReportingPeriodCode();
		rpc.setCode("A");
		result.setReportingPeriodTypeCode(rpc);
		EmissionsOperatingTypeCode eotc = new EmissionsOperatingTypeCode();
		eotc.setCode("R");
		result.setEmissionsOperatingTypeCode(eotc);
		result.setCalculationParameterValue(BigDecimal.valueOf(0));
		CalculationMaterialCode cmc = new CalculationMaterialCode();
		cmc.setCode("226");
		result.setCalculationMaterialCode(cmc);
		CalculationParameterTypeCode cptc = new CalculationParameterTypeCode();
		cptc.setCode("O");
		result.setCalculationParameterTypeCode(cptc);
		UnitMeasureCode uom = new UnitMeasureCode();
		uom.setCode("TON");
		result.setCalculationParameterUom(uom);
        
        return result;
	}

}
