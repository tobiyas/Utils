package de.tobiyas.util.formating;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.ChatColor;

import de.tobiyas.util.math.Math2;

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
	
	
	/**
	 * Formats the String to use.
	 * 
	 * @param percent to use
	 * @param barLength to use
	 * @param charToUse to use
	 * @param fullColor to use
	 * @param emptyColor to use.
	 * 
	 * @return the formatted String.
	 */
	public static String formatToPercent(double percent, int barLength, char charToUse, ChatColor fullColor, ChatColor emptyColor){
		if(percent <= 0) percent = 0;
		double presentBarLength = percent * barLength;
		presentBarLength = Math2.clamp(0, presentBarLength, barLength);
		
		String barLeft = fullColor + "";
		for(int i = 0; i < presentBarLength; i++){
			barLeft += charToUse;
			barLength --;
		}
		
		String barRest = emptyColor + "";
		for(; barLength > 0; barLength--){
			barRest += charToUse;
		}
		
		if(percent >= 1){
			barRest += fullColor.toString() + charToUse;
		}else{
			barRest += emptyColor.toString() + charToUse;
		}
		
		
		String chatString = barLeft + barRest;
		return chatString;
	}
	
	
	/**
	 * If the main string equals any ignore cased.
	 * @param main to check
	 * @param part to check against.
	 * @return
	 */
	public static boolean equalsAny(String main, String... part){
		for(String par : part) if(par.equalsIgnoreCase(main)) return true;
		return false;
	}
	
	
	public static String join(String delimiter, Object... data){
		String result = "";
		for(int i = 0; i < data.length; i++) {
			if(i != 0) result += delimiter;
			result += data[i];
		}
		
		return result;
	}
	
	public static String join(String delimiter, Collection<?> data){
		String result = "";
		Iterator<?> it = data.iterator();
		
		while(it.hasNext()){
			if(!result.isEmpty()) result += delimiter;
			result += it.next();
		}
		
		return result;
	}
	
}
