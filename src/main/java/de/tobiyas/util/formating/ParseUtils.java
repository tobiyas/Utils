package de.tobiyas.util.formating;

public class ParseUtils {

	
	
	
	/**
	 * Parses an Integer from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static int parseInt(String value, int defaultValue){
		try{ return Integer.parseInt(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Short from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static short parseShort(String value, short defaultValue){
		try{ return Short.parseShort(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	
	/**
	 * Parses an Double from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static double parseDouble(String value, double defaultValue){
		try{ return Double.parseDouble(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Boolean from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static boolean parseBoolean(String value, boolean defaultValue){
		try{ return Boolean.parseBoolean(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	
	/**
	 * Parses an Float from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static float parseFloat(String value, float defaultValue){
		try{ return Float.parseFloat(value); }catch(Throwable exp){ return defaultValue; }
	}
	
}
