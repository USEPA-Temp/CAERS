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

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.validation.CefValidatorContext;
import gov.epa.cef.web.service.validation.ValidationField;
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

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_HR_PER_DAY.value()) && errorMap.get(ValidationField.DETAIL_AVG_HR_PER_DAY.value()).size() == 1);
    }

    @Test
    public void averageHoursPerDayRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setAvgHoursPerDay(Double.valueOf(25));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_HR_PER_DAY.value()) && errorMap.get(ValidationField.DETAIL_AVG_HR_PER_DAY.value()).size() == 1);

        cefContext = createContext();
        testData.setAvgHoursPerDay(Double.valueOf(0));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_HR_PER_DAY.value()) && errorMap.get(ValidationField.DETAIL_AVG_HR_PER_DAY.value()).size() == 1);
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

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_DAY_PER_WEEK.value()) && errorMap.get(ValidationField.DETAIL_AVG_DAY_PER_WEEK.value()).size() == 1);
    }

    @Test
    public void averageDaysPerWeekRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setAvgDaysPerWeek(Double.valueOf(8));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_DAY_PER_WEEK.value()) && errorMap.get(ValidationField.DETAIL_AVG_DAY_PER_WEEK.value()).size() == 1);

        cefContext = createContext();
        testData.setAvgDaysPerWeek(Double.valueOf(0));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_DAY_PER_WEEK.value()) && errorMap.get(ValidationField.DETAIL_AVG_DAY_PER_WEEK.value()).size() == 1);
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

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_ACT_HR_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_ACT_HR_PER_PERIOD.value()).size() == 1);
    }

    @Test
    public void actualHoursPerPeriodRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setActualHoursPerPeriod(Short.valueOf("8785"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_ACT_HR_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_ACT_HR_PER_PERIOD.value()).size() == 1);

        cefContext = createContext();
        testData.setActualHoursPerPeriod(Short.valueOf("0"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_ACT_HR_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_ACT_HR_PER_PERIOD.value()).size() == 1);
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

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_SPRING.value()) && errorMap.get(ValidationField.DETAIL_PCT_SPRING.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_SUMMER.value()) && errorMap.get(ValidationField.DETAIL_PCT_SUMMER.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_FALL.value()) && errorMap.get(ValidationField.DETAIL_PCT_FALL.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_WINTER.value()) && errorMap.get(ValidationField.DETAIL_PCT_WINTER.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT.value()) && errorMap.get(ValidationField.DETAIL_PCT.value()).size() == 1);
    }
    
    @Test
    public void seasonalPercentsRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(110));
        testData.setPercentSummer(Double.valueOf(110));
        testData.setPercentFall(Double.valueOf(110));
        testData.setPercentWinter(Double.valueOf(110));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 5);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_SPRING.value()) && errorMap.get(ValidationField.DETAIL_PCT_SPRING.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_SUMMER.value()) && errorMap.get(ValidationField.DETAIL_PCT_SUMMER.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_FALL.value()) && errorMap.get(ValidationField.DETAIL_PCT_FALL.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_WINTER.value()) && errorMap.get(ValidationField.DETAIL_PCT_WINTER.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT.value()) && errorMap.get(ValidationField.DETAIL_PCT.value()).size() == 1);
        
        cefContext = createContext();
        testData = createBaseOperatingDetail();

        testData.setPercentSpring(Double.valueOf(-10));
        testData.setPercentSummer(Double.valueOf(-10));
        testData.setPercentFall(Double.valueOf(-10));
        testData.setPercentWinter(Double.valueOf(-10));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 5);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_SPRING.value()) && errorMap.get(ValidationField.DETAIL_PCT_SPRING.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_SUMMER.value()) && errorMap.get(ValidationField.DETAIL_PCT_SUMMER.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_FALL.value()) && errorMap.get(ValidationField.DETAIL_PCT_FALL.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT_WINTER.value()) && errorMap.get(ValidationField.DETAIL_PCT_WINTER.value()).size() == 1);
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT.value()) && errorMap.get(ValidationField.DETAIL_PCT.value()).size() == 1);
    }
    
    @Test
    public void seasonalPercentsBoundaryTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(100));
        testData.setPercentSummer(Double.valueOf(100));
        testData.setPercentFall(Double.valueOf(100));
        testData.setPercentWinter(Double.valueOf(100));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT.value()) && errorMap.get(ValidationField.DETAIL_PCT.value()).size() == 1);

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(0));
        testData.setPercentSummer(Double.valueOf(0));
        testData.setPercentFall(Double.valueOf(0));
        testData.setPercentWinter(Double.valueOf(0));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT.value()) && errorMap.get(ValidationField.DETAIL_PCT.value()).size() == 1);
    }

    @Test
    public void sumPercentsRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setPercentWinter(Double.valueOf(26));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT.value()) && errorMap.get(ValidationField.DETAIL_PCT.value()).size() == 1);

        cefContext = createContext();
        testData.setPercentWinter(Double.valueOf(24));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_PCT.value()) && errorMap.get(ValidationField.DETAIL_PCT.value()).size() == 1);
    }

    @Test
    public void sumPercentsBoundaryTest() {

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

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()).size() == 1);
    }

    @Test
    public void avgWeeksPerPeriodRangeFailTest() {

        CefValidatorContext cefContext = createContext();
        OperatingDetail testData = createBaseOperatingDetail();

        cefContext = createContext();
        testData.setAvgWeeksPerPeriod(Short.valueOf("53"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()).size() == 1);

        cefContext = createContext();
        testData.setAvgWeeksPerPeriod(Short.valueOf("0"));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()).size() == 1);
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
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        Map<String, List<ValidationError>> errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()).size() == 1);

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(75));
        testData.setPercentSummer(Double.valueOf(25));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()).size() == 1);

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(50));
        testData.setPercentFall(Double.valueOf(25));

        assertFalse(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() != null && cefContext.result.getErrors().size() == 1);

        errorMap = mapErrors(cefContext.result.getErrors());
        assertTrue(errorMap.containsKey(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()) && errorMap.get(ValidationField.DETAIL_AVG_WEEK_PER_PERIOD.value()).size() == 1);

        cefContext = createContext();
        testData.setPercentSpring(Double.valueOf(25));
        testData.setPercentWinter(Double.valueOf(25));

        assertTrue(this.validator.validate(cefContext, testData));
        assertTrue(cefContext.result.getErrors() == null || cefContext.result.getErrors().isEmpty());
    }


    private OperatingDetail createBaseOperatingDetail() {

        OperatingStatusCode opStatCode = new OperatingStatusCode();
        opStatCode.setCode("OP");
        
        ReportingPeriod period = new ReportingPeriod();
        period.setEmissionsProcess(new EmissionsProcess());
        period.getEmissionsProcess().setOperatingStatusCode(opStatCode);
        
        OperatingDetail result = new OperatingDetail();
        result.setAvgHoursPerDay(Double.valueOf(5));
        result.setAvgDaysPerWeek(Double.valueOf(5));
        result.setActualHoursPerPeriod(Short.valueOf("5"));
        result.setPercentSpring(Double.valueOf(25));
        result.setPercentSummer(Double.valueOf(25));
        result.setPercentFall(Double.valueOf(25));
        result.setPercentWinter(Double.valueOf(25));
        result.setAvgWeeksPerPeriod(Short.valueOf("5"));
        result.setReportingPeriod(period);
        period.getOperatingDetails().add(result);

        return result;
    }
}
