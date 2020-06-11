package gov.epa.cef.web.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    private static final int FIRST_FISCAL_MONTH = Calendar.OCTOBER;

    //private constructor to override implicit java constructor - static class should not be instantiable
    private DateUtils() {

        throw new IllegalStateException("Utility class");
    }

    public static XMLGregorianCalendar createGregorianCalendar() {

        ZonedDateTime now = ZonedDateTime.now();
        GregorianCalendar gcal = GregorianCalendar.from(now);
        XMLGregorianCalendar result;
        try {

            result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

        } catch (DatatypeConfigurationException e) {

            throw new IllegalStateException(e);
        }

        return result;
    }

    public static Integer getFiscalYearForDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (month >= FIRST_FISCAL_MONTH) {
            year++;
        }
        return year;
    }

    public static Integer getCurrentReportingYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        //note: current reporting year is always the previous calendar year - like taxes.
        //e.g. in 2020, facilities will be creating a 2019 report.
        calendar.add(Calendar.YEAR, -1);
        return calendar.get(Calendar.YEAR);
    }

}
