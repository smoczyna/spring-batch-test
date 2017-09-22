package eu.squadd.batch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <h1>DateTimeUtils</h1> DateTimeUtils is a utility class that provides
 * convenience routines for date handling.
 * <p>
 */
public class ProcessingUtils {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSZ";
    public static final String SHORT_FORMAT = "yyyy-MM-dd";
    public static final String SHORTDATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private static final String LBRACKET = "[";
    
    /**
     * formats zone date according to given format
     * @param zdt
     * @param format
     * @return 
     */
    public static String zdtToString(ZonedDateTime zdt, String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(zdt);
    }

    /**
     * formats regular date according to given format
     * @param date
     * @param format
     * @return 
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
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
    
    public static String getYearAndMonthFromStrDate(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(SHORT_FORMAT);
            Date date = sdf.parse(strDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
            return sdf2.format(date);
        } catch (ParseException ex) {            
            System.out.println("Parsing error occurred: "+ex.getMessage());
            return null;
        }
    }
    
    public static String dateTimeToStringWithourSpaces(Date date) {
        String strDate = dateToString(date, SHORTDATETIME_FORMAT);
        return strDate.replace(" ", ".");
    }
}
