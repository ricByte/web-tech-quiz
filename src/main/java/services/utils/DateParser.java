package services.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateParser {

    static final long ONE_MINUTE_IN_MILLIS = 60000;

    public static Date parseString(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date addMin(Date date, int minutes) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long t = cal.getTimeInMillis();
        return new Date(t + (minutes * ONE_MINUTE_IN_MILLIS));

    }
}
