package eu.squadd.batch.util;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <h1>DateTimeUtils</h1> DateTimeUtils is a utility class that provides
 * convenience routines for date handling.
 * <p>
 */
public class DateTimeUtils {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSZ";
    public static final String SHORT_FORMAT = "yyyy-MM-dd";
    public static final String SHORTDATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static final String LBRACKET = "[";
    
    
    public static String zdtToString(ZonedDateTime zdt, String format) {
        DateTimeFormatter sdtFormat = DateTimeFormatter.ofPattern(format);
        return sdtFormat.format(zdt);
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdtFormat = new SimpleDateFormat(format);
        return sdtFormat.format(date);
    }
    
    /**
     * Formats a ZonedDateTime into a standard string.
     *
     * @param zdt
     * @return
     */
    public static String toStringWithoutText(ZonedDateTime zdt) {
        String zdtOut = zdtToString(zdt, STANDARD_FORMAT);
        int bracketIndex = zdtOut.indexOf(LBRACKET);
        if (bracketIndex != -1) {
            zdtOut = zdtOut.substring(0, bracketIndex);
        }
        return zdtOut;
    }

}
