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
import gov.epa.cef.web.domain.ControlMeasureCode;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.ControlPathValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ControlPathValidatorTest extends BaseValidatorTest {
	
	@InjectMocks
	private ControlPathValidator validator;
	
	@Test
	public void totalDirectEntryFalse_PassTest() {
		
		CefValidatorContext cefContext = createContext();
		ControlPath testData = createBaseControlPath();
		
		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
		
	}
	
	@Test
	public void duplicateIdentifiersPassTest() {
		
		CefValidatorContext cefContext = createContext();
		ControlPath testData = createBaseControlPath();
		ControlAssignment ca1 = new ControlAssignment();
		Control c1 = new Control();
		ControlMeasureCode cmc = new ControlMeasureCode();
		cmc.setDescription("test");
		c1.setControlMeasureCode(cmc);
		c1.getAssignments().add(ca1);
		testData.getAssignments().add(ca1);

		assertTrue(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
		
	}
	
	@Test
	public void duplicateControlMeasureFailTest() {
		
		CefValidatorContext cefContext = createContext();
		ControlPath testData = createBaseControlPath();
		ControlAssignment ca1 = new ControlAssignment();
		ControlAssignment ca2 = new ControlAssignment();
		Control c1 = new Control();
		ControlMeasureCode cmc = new ControlMeasureCode();
		cmc.setDescription("test");
		c1.setControlMeasureCode(cmc);
		c1.getAssignments().add(ca1);
		ca1.setControl(c1);
		ca2.setControl(c1);
		testData.getAssignments().add(ca1);
		testData.getAssignments().add(ca2);
		
		
		assertFalse(this.validator.validate(cefContext, testData));
		assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
		
		Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
		assertTrue(errorMap.containsKey(ValidationField.CONTROL_PATH_ASSIGNMENT.value()) && errorMap.get(ValidationField.CONTROL_PATH_ASSIGNMENT.value()).size() == 1);		
		
	}
	
	
	
	
	private ControlPath createBaseControlPath() {
		
		ControlPath result = new ControlPath();
		result.setPathId("test control path");
		result.setDescription("test description");
		FacilitySite fs = new FacilitySite();
		fs.getControlPaths().add(result);
		result.setFacilitySite(fs);
		
		return result;
	}

}
