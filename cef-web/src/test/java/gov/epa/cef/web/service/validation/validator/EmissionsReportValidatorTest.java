package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsReportValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmissionsReportValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private EmissionsReportValidator validator;

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void nullYearTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setYear(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void oldYearTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setYear(Integer.valueOf(testData.getYear() - 1).shortValue());

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void nullAgencyCodeTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setAgencyCode(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void nullFrsFacilityIdTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setFrsFacilityId(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void nullEisProgramIdTest() {

        CefValidatorContext cefContext = createContext();
        EmissionsReport testData = createBaseReport();
        testData.setEisProgramId(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    private EmissionsReport createBaseReport() {

        EmissionsReport result = new EmissionsReport();
        result.setYear(Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1).shortValue());
        result.setAgencyCode("GA");
        result.setFrsFacilityId("1");
        result.setEisProgramId("1");

        return result;
    }
}
