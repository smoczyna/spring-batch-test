package com.vzw.booking.ms.batch.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h1>DateTimeUtils</h1> DateTimeUtils is a utility class that provides convenience
 * routines for date handling.
 * <p>
 */
public class DateTimeUtils {
	
	private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSZ";
	private static final String LBRACKET = "[";
	private static final DateTimeFormatter sdtFormat = DateTimeFormatter.ofPattern(STANDARD_FORMAT);
	
	/**
	 * Formats a ZonedDateTime into a standard string.
	 * @param zdt
	 * @return
	 */
	public static String toStringWithoutText(ZonedDateTime zdt) {
		String zdtOut = sdtFormat.format(zdt);
		int bracketIndex = zdtOut.indexOf(LBRACKET);
		if(bracketIndex != -1) {
			zdtOut = zdtOut.substring(0, bracketIndex);
		}
		return sdtFormat.format(zdt);
	}

}
