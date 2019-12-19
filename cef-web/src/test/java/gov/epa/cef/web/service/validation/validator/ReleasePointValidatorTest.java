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

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
import gov.epa.cef.web.service.validation.validator.federal.ReleasePointValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReleasePointValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private ReleasePointValidator validator;

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void averageHoursPerDayRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();

        cefContext = createContext();
        testData.setExitGasTemperature((short) 29);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_TEMP.value()) && errorMap.get(ValidationField.RP_GAS_TEMP.value()).size() == 1);

        cefContext = createContext();
        testData.setExitGasTemperature((short) 3501);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.RP_GAS_TEMP.value()) && errorMap.get(ValidationField.RP_GAS_TEMP.value()).size() == 1);
    }

    @Test
    public void averageHoursPerDayBoundaryTest() {

        CefValidatorContext cefContext = createContext();
        ReleasePoint testData = createBaseReleasePoint();
        testData.setExitGasTemperature((short) 30);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setExitGasTemperature((short) 3500);

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    private ReleasePoint createBaseReleasePoint() {

        ReleasePoint result = new ReleasePoint();
        result.setExitGasTemperature((short) 50);

        return result;
    }
}
