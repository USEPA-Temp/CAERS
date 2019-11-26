package gov.epa.cef.web.service.validation.validator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.validator.federal.FacilitySiteValidator;
import gov.epa.cef.web.service.validation.validator.federal.OperatingDetailValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OperatingDetailValidatorTest extends BaseValidatorTest {

    @InjectMocks
    private OperatingDetailValidator validator;

    @Test
    public void simpleValidatePassTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void averageHoursPerDayNullFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setAvgHoursPerDay(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void averageHoursPerDayRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setAvgHoursPerDay(Double.valueOf(25));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        cefContext = createContext();
        testData.setAvgHoursPerDay(Double.valueOf(0));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void averageHoursPerDayBoundaryTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setAvgHoursPerDay(Double.valueOf(.1));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setAvgHoursPerDay(Double.valueOf(24));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }


    @Test
    public void averageDaysPerWeekNullFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setAvgDaysPerWeek(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void averageDaysPerWeekRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setAvgDaysPerWeek(Double.valueOf(8));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        cefContext = createContext();
        testData.setAvgDaysPerWeek(Double.valueOf(0));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void averageDaysPerWeekBoundaryTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setAvgDaysPerWeek(Double.valueOf(.1));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setAvgDaysPerWeek(Double.valueOf(7));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }


    @Test
    public void actualHoursPerPeriodNullFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setActualHoursPerPeriod(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void actualHoursPerPeriodRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setActualHoursPerPeriod(Short.valueOf("8785"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        cefContext = createContext();
        testData.setActualHoursPerPeriod(Short.valueOf("0"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void actualHoursPerPeriodBoundaryTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setActualHoursPerPeriod(Short.valueOf("1"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setActualHoursPerPeriod(Short.valueOf("8784"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void percentsNullFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setPercentSpring(null);
        testData.setPercentSummer(null);
        testData.setPercentFall(null);
        testData.setPercentWinter(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 5);
    }

    @Test
    public void percentsRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setPercentWinter(Double.valueOf(26));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        cefContext = createContext();
        testData.setPercentWinter(Double.valueOf(24));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void percentsBoundaryTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setPercentWinter(Double.valueOf(25.5));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setPercentWinter(Double.valueOf(24.5));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void avgWeeksPerPeriodNullFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setAvgWeeksPerPeriod(null);

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void avgWeeksPerPeriodRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setAvgWeeksPerPeriod(Short.valueOf("53"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        cefContext = createContext();
        testData.setAvgWeeksPerPeriod(Short.valueOf("0"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);
    }

    @Test
    public void avgWeeksPerPeriodBoundaryTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setAvgWeeksPerPeriod(Short.valueOf("52"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());

        cefContext = createContext();
        testData.setAvgWeeksPerPeriod(Short.valueOf("1"));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }

    @Test
    public void seasonsForAvgWeeksTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();
        testData.setPercentSpring(Double.valueOf(100));
        testData.setPercentSummer(Double.valueOf(0));
        testData.setPercentFall(Double.valueOf(0));
        testData.setPercentWinter(Double.valueOf(0));
        testData.setAvgWeeksPerPeriod(Short.valueOf("45"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 3);

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(75));
        testData.setPercentSummer(Double.valueOf(25));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 2);

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(50));
        testData.setPercentFall(Double.valueOf(25));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(25));
        testData.setPercentWinter(Double.valueOf(25));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }


    private OperatingDetail createBaseOperatingDetail() {

        OperatingDetail result = new OperatingDetail();
        result.setAvgHoursPerDay(Double.valueOf(5));
        result.setAvgDaysPerWeek(Double.valueOf(5));
        result.setActualHoursPerPeriod(Short.valueOf("5"));
        result.setPercentSpring(Double.valueOf(25));
        result.setPercentSummer(Double.valueOf(25));
        result.setPercentFall(Double.valueOf(25));
        result.setPercentWinter(Double.valueOf(25));
        result.setAvgWeeksPerPeriod(Short.valueOf("5"));

        return result;
    }
}
