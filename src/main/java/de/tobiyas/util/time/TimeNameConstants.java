package de.tobiyas.util.time;

public class TimeNameConstants {

	/**
	 * The Default Constants in English.
	 */
	public static final TimeNameConstants ENGLISH_CONSTANTS = new TimeNameConstants("Days", "Hours", "Minutes", "Seconds", "Miliseconds");
	
	/**
	 * The Default Constants in German.
	 */
	public static final TimeNameConstants GERMAN_CONSTANTS = new TimeNameConstants("Tage", "Stunden", "Minuten", "Sekunden", "Milisekunden");
	
	
	private final String dayTag;
	private final String hourTag;
	private final String minuteTag;
	private final String secondsTag;
	private final String milisecondsTag;
	
	
	
	public TimeNameConstants(String dayTag, String hourTag, String minuteTag, String secondsTag, String milisecondsTag) {
		this.dayTag = dayTag == null ? "" : dayTag;
		this.hourTag = hourTag == null ? "" : hourTag;
		this.minuteTag = minuteTag == null ? "" : minuteTag;
		this.secondsTag = secondsTag == null ? "" : secondsTag;
		this.milisecondsTag = milisecondsTag == null ? "" : milisecondsTag;
	}


	public String getDayTag() {
		return dayTag;
	}



	public String getHourTag() {
		return hourTag;
	}



	public String getMinuteTag() {
		return minuteTag;
	}



	public String getSecondsTag() {
		return secondsTag;
	}



	public String getMilisecondsTag() {
		return milisecondsTag;
	}
	
	
}
