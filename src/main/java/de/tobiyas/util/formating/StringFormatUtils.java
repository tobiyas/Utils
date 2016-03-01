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
	
	
	
	/**
	 * Reads an int from the String
	 * @param val to parse
	 * @param defaultValue to pass if failed.
	 * @return the parsed / default value.
	 */
	public static int parseInt(String val, int defaultValue){
		try{
			return Integer.parseInt(val);
		}catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Reads an Double from the String
	 * @param val to parse
	 * @param defaultValue to pass if failed.
	 * @return the parsed / default value.
	 */
	public static double parseDouble(String val, double defaultValue){
		try{
			return Double.parseDouble(val);
		}catch(Throwable exp){ return defaultValue; }
	}
	
}
