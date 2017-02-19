package services.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateParser {

    static String formatDate = "yyyy-MM-dd HH:mm:ss";

    public static Date parseString(String date) {
        try {
            return new SimpleDateFormat(formatDate).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String parseDate(GregorianCalendar date) {

        SimpleDateFormat fmt = new SimpleDateFormat(formatDate);
        fmt.setCalendar(date);

        String dateFormatted = fmt.format(date.getTime());
        return dateFormatted;

    }

    public static void addMin(GregorianCalendar date, int minutes) {

        date.add((GregorianCalendar.MINUTE), minutes);

    }

    public static boolean isValidDate(GregorianCalendar date){

        GregorianCalendar currentDate = new GregorianCalendar();
        return currentDate.before(date);

    }

    public static Timestamp createTimeStamp() {
        Date now = new Date();

        return new Timestamp(now.getTime());
    }
}
