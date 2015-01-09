package de.tobiyas.util.formating;

public class StringFormatUtils {

	
	
	/**
	 * Capitalizes the First Letter, rest is lowercase.
	 * 
	 * @param value to edit
	 * @return the Value with first letter capital, rest lowercase.
	 */
	public static String firstCapitalRestLow(String value){
		return value.substring(0,1).toUpperCase() + value.substring(1).toLowerCase();
	}
	
}
