package gov.epa.cef.web.service.validation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		p1.setPollutantName("NOX");
		Pollutant p2 = new Pollutant();
		p2.setPollutantName("CO2");
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
		p2.setPollutantName("CO2");
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
	
	
	private Control createBaseControl() {
		
		Control result = new Control();
		result.setId(1L);
		result.setIdentifier("test");
		result.setPercentControl(50.0);
		FacilitySite fs = new FacilitySite();
		fs.getControls().add(result);
		result.setFacilitySite(fs);
		
		ControlAssignment ca = new ControlAssignment();
		ca.setControl(result);
		result.getAssignments().add(ca);
		
		ControlPollutant cp1 = new ControlPollutant();
		Pollutant p1 = new Pollutant();
		p1.setPollutantName("NOX");
		cp1.setPollutant(p1);
		cp1.setPercentReduction(50.0);
		result.getPollutants().add(cp1);
		
		return result;
	}

}
