package de.tobiyas.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateComparisor {

	
	/**
	 * Checks if the Date passed is today.
	 * 
	 * @param date to check
	 * 
	 * @return true if the date passed is today
	 */
	public static boolean isToday(Date date){
		Calendar today = Calendar.getInstance();
		Calendar otherDate = Calendar.getInstance();
				otherDate.setTime(date);
				
		return today.get(Calendar.YEAR) == otherDate.get(Calendar.YEAR)
				  && today.get(Calendar.DAY_OF_YEAR) == otherDate.get(Calendar.DAY_OF_YEAR);
	}
}
