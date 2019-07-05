package gov.epa.cef.web.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    
    private static final int FIRST_FISCAL_MONTH  = Calendar.OCTOBER;
    
    public static Integer getFiscalYearForDate(Date date) {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        if(month >= FIRST_FISCAL_MONTH) {
            year++;
        }
        return year;
    }

}
