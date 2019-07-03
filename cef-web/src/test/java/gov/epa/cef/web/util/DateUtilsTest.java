package gov.epa.cef.web.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {


    @Test
    public void getFiscalYearForDate_Should_ReturnCorrectCurrentFiscalYear_When_ValidDatePassed() {
        
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));

        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2019), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2020), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2020), DateUtils.getFiscalYearForDate(cal.getTime()));
        
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.YEAR, 2019);
        assertEquals(new Integer(2020), DateUtils.getFiscalYearForDate(cal.getTime()));        
    }

}
