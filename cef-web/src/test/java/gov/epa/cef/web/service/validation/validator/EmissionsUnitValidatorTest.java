package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.validation.CefValidatorContext;
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

    private EmissionsUnit createBaseEmissionsUnit() {

        // No tests for Emissions Units yet
        EmissionsUnit result = new EmissionsUnit();

        return result;
    }
}
