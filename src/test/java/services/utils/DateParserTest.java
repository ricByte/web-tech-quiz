package services.utils;

import org.junit.Test;

import java.util.GregorianCalendar;
import static org.junit.Assert.*;


public class DateParserTest {

    @Test
    public void isValidDate() throws Exception {
        GregorianCalendar expected = new GregorianCalendar();

        expected.add((GregorianCalendar.MINUTE), 10);


        boolean validDate = DateParser.isValidDate(expected);

        assertTrue(validDate);
    }

    @Test
    public void isNotValidDate() throws Exception {
        GregorianCalendar expected = new GregorianCalendar();

        expected.add((GregorianCalendar.MINUTE), -10);


        boolean validDate = DateParser.isValidDate(expected);

        assertFalse(validDate);
    }

}