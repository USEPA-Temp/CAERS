package gov.epa.cef.web.service.validation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.baidu.unbiz.fluentvalidator.ValidationError;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.ControlValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ControlValidatorTest extends BaseValidatorTest {
	
	@InjectMocks
	private ControlValidator validator;
	
	@Test
	public void totalDirectEntryFalse_PassTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
		
	}
	
	@Test
	public void duplicateIdentifiersPassTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		Control c1 = new Control();
		c1.setIdentifier("test 1");
		testData.getFacilitySite().getControls().add(c1);
		
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
		
	}
	
	@Test
	public void duplicateIdentifiersFailTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		Control c1 = new Control();
		c1.setIdentifier("test");
		testData.getFacilitySite().getControls().add(c1);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_IDENTIFIER.value()) && errorMap.get(ValidationField.CONTROL_IDENTIFIER.value()).size() == 1);		
		
	}
	
	@Test
    public void statusYearRangePassTest() {

        CefValidatorContext cefContext = createContext();
        Control testData = createBaseControl();
        
        testData.setStatusYear((short) 1900);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
        
        cefContext = createContext();
        testData.setStatusYear((short) 2050);
        
        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }
	
	@Test
    public void statusYearRangeFailTest() {
		
		CefValidatorContext cefContext = createContext();
        Control testData = createBaseControl();
        
        testData.setStatusYear((short) 1800);
        
        assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_STATUS_YEAR.value()) && errorMap.get(ValidationField.CONTROL_STATUS_YEAR.value()).size() == 1);		
	}
	
	@Test
    public void statusYearNullFailTest() {
		
		CefValidatorContext cefContext = createContext();
        Control testData = createBaseControl();
        
        OperatingStatusCode opStatCode = new OperatingStatusCode();
    	opStatCode.setCode("TS");
    	testData.setOperatingStatusCode(opStatCode);
        testData.setStatusYear(null);
        
        assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
        
        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_STATUS_YEAR.value()) && errorMap.get(ValidationField.CONTROL_STATUS_YEAR.value()).size() == 1);		
	}
	
	/**
	 * There should be errors for control pollutant percent reduction when the percent is < 5 or >= 100.
	 */
	@Test
	public void controlPollutantPercentReductionTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		ControlPollutant cp = new ControlPollutant();
		Pollutant p = new Pollutant();
		p.setPollutantName("Nitrogen Oxides");
		p.setPollutantCode("NOX");
		cp.setPollutant(p);
		cp.setPercentReduction(99.9);
		testData.getPollutants().add(cp);
		
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
		
		cefContext = createContext();
		cp.setPercentReduction(0.0);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_POLLUTANT.value()) && errorMap.get(ValidationField.CONTROL_POLLUTANT.value()).size() == 1);
		
		cefContext = createContext();
		cp.setPercentReduction(100.0);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_POLLUTANT.value()) && errorMap.get(ValidationField.CONTROL_POLLUTANT.value()).size() == 1);
		
	}
	
	@Test
	public void duplicateControlPollutantFailTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		ControlPollutant cp1 = new ControlPollutant();
		Pollutant p1 = new Pollutant();
		p1.setPollutantCode("NOX");
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("CO2");
		cp1.setPollutant(p1);
		cp1.setPercentReduction(50.0);
		testData.getPollutants().add(cp1);
		ControlPollutant cp2 = new ControlPollutant();
		cp2.setPercentReduction(50.0);
		testData.getPollutants().add(cp2);
		cp2.setPollutant(p1);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_POLLUTANT.value()) && errorMap.get(ValidationField.CONTROL_POLLUTANT.value()).size() == 1);		
		
	}
	
	@Test
	public void duplicateControlPollutantPassTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		Pollutant p2 = new Pollutant();
		p2.setPollutantCode("CH4");
		ControlPollutant cp2 = new ControlPollutant();
		cp2.setPollutant(p2);
		cp2.setPercentReduction(50.0);
		testData.getPollutants().add(cp2);
		
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
		
	}
	
	@Test
	public void controlPathAssignedFailTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		testData.setAssignments(null);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_PATH_WARNING.value()) && errorMap.get(ValidationField.CONTROL_PATH_WARNING.value()).size() == 1);		
		
	}
	
	@Test
	public void controlPathAssignedPassTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());		
		
	}
	
	@Test
	public void controlPollutantRequiredFailTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		testData.setPollutants(null);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_POLLUTANT.value()) && errorMap.get(ValidationField.CONTROL_POLLUTANT.value()).size() == 1);		
		
	}
	
	@Test
	public void controlPercentControlRangeFailTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		testData.setPercentControl(0.5);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_PERCENT_CONTROL.value()) && errorMap.get(ValidationField.CONTROL_PERCENT_CONTROL.value()).size() == 1);
		
		cefContext = createContext();
		testData.setPercentControl(500.0);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_PERCENT_CONTROL.value()) && errorMap.get(ValidationField.CONTROL_PERCENT_CONTROL.value()).size() == 1);
	}
	
	@Test
	public void controlPercentControlPrecisionFailTest() {
		
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		testData.setPercentControl(10.568);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_PERCENT_CONTROL.value()) && errorMap.get(ValidationField.CONTROL_PERCENT_CONTROL.value()).size() == 1);
	}
	
	@Test
	public void controlDatesPassTest() {
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
	
		LocalDate start = LocalDate.parse("2021-01-01");
		LocalDate end = LocalDate.parse("2021-04-04");
		LocalDate upgrade = LocalDate.parse("2021-02-02");
		testData.setStartDate(start);
		testData.setEndDate(end);
		testData.setUpgradeDate(upgrade);
		
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
	}
	
	@Test
	public void controlDatesFailTest() {
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
	
		LocalDate start = LocalDate.parse("2021-04-04");
		LocalDate end = LocalDate.parse("2021-01-01");
		testData.setStartDate(start);
		testData.setEndDate(end);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_DATE.value()) && errorMap.get(ValidationField.CONTROL_DATE.value()).size() == 1);		
		
		cefContext = createContext();
		end = LocalDate.parse("2021-05-05");
		LocalDate upgrade = LocalDate.parse("2021-06-06");
		testData.setUpgradeDate(upgrade);
		testData.setEndDate(end);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_DATE.value()) && errorMap.get(ValidationField.CONTROL_DATE.value()).size() == 1);
	}
	
	@Test
	public void controlDateRangeFailTest() {
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
	
		LocalDate start = LocalDate.parse("1800-04-04");
		testData.setStartDate(start);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_DATE.value()) && errorMap.get(ValidationField.CONTROL_DATE.value()).size() == 1);		
		
		cefContext = createContext();
		start = LocalDate.parse("1900-04-04");
		LocalDate end = LocalDate.parse("2055-05-05");
		testData.setStartDate(start);
		testData.setEndDate(end);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_DATE.value()) && errorMap.get(ValidationField.CONTROL_DATE.value()).size() == 1);
		
		cefContext = createContext();
		LocalDate upgrade = LocalDate.parse("2055-05-05");
		testData.setUpgradeDate(upgrade);
		testData.setEndDate(null);
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_DATE.value()) && errorMap.get(ValidationField.CONTROL_DATE.value()).size() == 1);
	}
	
	@Test
	public void controlPollutantPassTest() {
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		ControlPollutant cp1 = new ControlPollutant();
		ControlPollutant cp2 = new ControlPollutant();
		Pollutant p1 = new Pollutant();
		Pollutant p2 = new Pollutant();
		p1.setPollutantCode("PM10-FIL");
		p2.setPollutantCode("PM25-FIL");
		cp1.setPollutant(p1);
		cp2.setPollutant(p2);
		cp1.setPercentReduction(50.0);
		cp2.setPercentReduction(50.0);
		testData.getPollutants().add(cp1);
		testData.getPollutants().add(cp2);
	
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
		
		cefContext = createContext();
		ControlPollutant cp3 = new ControlPollutant();
		ControlPollutant cp4 = new ControlPollutant();
		Pollutant p3 = new Pollutant();
		Pollutant p4 = new Pollutant();
		p3.setPollutantCode("PM10-PRI");
		p4.setPollutantCode("PM25-PRI");
		cp3.setPollutant(p3);
		cp4.setPollutant(p4);
		cp3.setPercentReduction(80.0);
		cp4.setPercentReduction(50.0);
		testData.getPollutants().add(cp3);
		testData.getPollutants().add(cp4);
	
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
	}
	
	@Test
	public void controlPollutant_PM25FIL_PM10FIL_FailTest() {
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		ControlPollutant cp1 = new ControlPollutant();
		ControlPollutant cp2 = new ControlPollutant();
		Pollutant p1 = new Pollutant();
		Pollutant p2 = new Pollutant();
		p1.setPollutantCode("PM10-FIL");
		p2.setPollutantCode("PM25-FIL");
		cp1.setPollutant(p1);
		cp2.setPollutant(p2);
		cp1.setPercentReduction(50.0);
		cp2.setPercentReduction(80.0);
		testData.getPollutants().add(cp1);
		testData.getPollutants().add(cp2);
	
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_POLLUTANT.value()) && errorMap.get(ValidationField.CONTROL_POLLUTANT.value()).size() == 1);		
	}
	
	@Test
	public void controlPollutant_PM25PRI_PM10PRI_FailTest() {
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		ControlPollutant cp1 = new ControlPollutant();
		ControlPollutant cp2 = new ControlPollutant();
		Pollutant p1 = new Pollutant();
		Pollutant p2 = new Pollutant();
		p1.setPollutantCode("PM10-PRI");
		p2.setPollutantCode("PM25-PRI");
		cp1.setPollutant(p1);
		cp2.setPollutant(p2);
		cp1.setPercentReduction(50.0);
		cp2.setPercentReduction(80.0);
		testData.getPollutants().add(cp1);
		testData.getPollutants().add(cp2);
	
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_POLLUTANT.value()) && errorMap.get(ValidationField.CONTROL_POLLUTANT.value()).size() == 1);		
		
	}
	
	@Test
    public void operationStatusPSFailTest() {
		CefValidatorContext cefContext = createContext();
		Control testData = createBaseControl();
		
		OperatingStatusCode opStatusCode = new OperatingStatusCode();
        opStatusCode.setCode("PS");
        testData.setOperatingStatusCode(opStatusCode);
        testData.setStatusYear((short) 2021);
        
        assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_STATUS_YEAR.value()) && errorMap.get(ValidationField.CONTROL_STATUS_YEAR.value()).size() == 1);	
	}
	
	
	private Control createBaseControl() {
		
		Control result = new Control();
		OperatingStatusCode opStatCode = new OperatingStatusCode();
    	opStatCode.setCode("OP");
    	result.setOperatingStatusCode(opStatCode);
		result.setId(1L);
		result.setIdentifier("test");
		result.setStatusYear((short) 2020);
		result.setPercentControl(50.0);
		FacilitySite fs = new FacilitySite();
		fs.getControls().add(result);
		result.setFacilitySite(fs);
		
		ControlAssignment ca = new ControlAssignment();
		ca.setControl(result);
		result.getAssignments().add(ca);
		
		ControlPollutant cp1 = new ControlPollutant();
		Pollutant p1 = new Pollutant();
		p1.setPollutantCode("CO2");
		cp1.setPollutant(p1);
		cp1.setPercentReduction(50.0);
		result.getPollutants().add(cp1);
		
		return result;
	}

}
