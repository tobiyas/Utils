package de.tobiyas.util.time;

public class TimeUtils {

	
	/**
	 * Generates a TimeContainer from Miliseconds.
	 * 
	 * @param miliseconds to display.
	 * 
	 * @return a Timecontainer generated from Miliseconds.
	 */
	public static TimeContainer generateFromMiliseconds(long toGenerateFrom){
		long miliseconds = toGenerateFrom % 1000;
		long seconds = (toGenerateFrom / 1000l) % 60;
		long minutes = (toGenerateFrom / (60l * 1000l)) % 60;
		long hours = (toGenerateFrom / (60l * 60l * 1000l)) % 24;
		long days = (toGenerateFrom / (24l * 60l * 60l * 1000l));
		
		return new TimeContainer((int)days, (int)hours, (int)minutes, (int)seconds, (int)miliseconds);
	}
	
	
	
	/**
	 * Generates a TimeContainer from Seconds.
	 * 
	 * @param seconds to display.
	 * 
	 * @return a Timecontainer generated from Seconds.
	 */
	public static TimeContainer generateFromSeconds(int toGenerateFrom){
		return generateFromMiliseconds(toGenerateFrom * 1000l);
	}
	
	
}
