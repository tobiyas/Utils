package de.tobiyas.util.time;

public class TimeNameConstants {

	/**
	 * The Default Constants in English.
	 */
	public static final TimeNameConstants ENGLISH_CONSTANTS = new TimeNameConstants(
			"Day", "Days", 
			"Hour", "Hours", 
			"Minute", "Minutes", 
			"Second", "Seconds", 
			"Milisecond", "Miliseconds");
	
	/**
	 * The Default Constants in German.
	 */
	public static final TimeNameConstants GERMAN_CONSTANTS = new TimeNameConstants(
			"Tag", "Tage", 
			"Stunde", "Stunden", 
			"Minute", "Minuten", 
			"Sekunde", "Sekunden", 
			"Milisekunde", "Milisekunden");
	
	
	private final String daySingularTag;
	private final String dayPluralTag;
	private final String hourSingularTag;
	private final String hourPluralTag;
	private final String minuteSingularTag;
	private final String minutePluralTag;
	private final String secondsSingularTag;
	private final String secondsPluralTag;
	private final String milisecondsSingularTag;
	private final String milisecondsPluralTag;
	
	
	
	public TimeNameConstants(
			String daySingularTag, String dayPluralTag, 
			String hourSingularTag, String hourPluralTag,
			String minuteSingularTag, String minutePluralTag,
			String secondsSingularTag, String secondsPluralTag,
			String milisecondsSingularTag, String milisecondsPluralTag) {
		
		this.daySingularTag = daySingularTag == null ? "" : daySingularTag;
		this.hourSingularTag = hourSingularTag == null ? "" : hourSingularTag;
		this.minuteSingularTag = minuteSingularTag == null ? "" : minuteSingularTag;
		this.secondsSingularTag = secondsSingularTag == null ? "" : secondsSingularTag;
		this.milisecondsSingularTag = milisecondsSingularTag == null ? "" : milisecondsSingularTag;

		this.dayPluralTag = dayPluralTag == null ? "" : dayPluralTag;
		this.hourPluralTag = hourPluralTag == null ? "" : hourPluralTag;
		this.minutePluralTag = minutePluralTag == null ? "" : minutePluralTag;
		this.secondsPluralTag = secondsPluralTag == null ? "" : secondsPluralTag;
		this.milisecondsPluralTag = milisecondsPluralTag == null ? "" : milisecondsPluralTag;
	}

	


	public String getDaySingularTag() {
		return daySingularTag;
	}

	public String getDayPluralTag() {
		return dayPluralTag;
	}

	public String getHourSingularTag() {
		return hourSingularTag;
	}

	public String getHourPluralTag() {
		return hourPluralTag;
	}

	public String getMinuteSingularTag() {
		return minuteSingularTag;
	}

	public String getMinutePluralTag() {
		return minutePluralTag;
	}

	public String getSecondsSingularTag() {
		return secondsSingularTag;
	}

	public String getSecondsPluralTag() {
		return secondsPluralTag;
	}

	public String getMilisecondsSingularTag() {
		return milisecondsSingularTag;
	}

	public String getMilisecondsPluralTag() {
		return milisecondsPluralTag;
	}

	
	
	public String getDaysByNumber(int number){
		if(number == 1) return getDaySingularTag();
		return getDayPluralTag();
	}
	
	public String getHoursByNumber(int number){
		if(number == 1) return getHourSingularTag();
		return getHourPluralTag();
	}
	
	public String getMinutesByNumber(int number){
		if(number == 1) return getMinuteSingularTag();
		return getMinutePluralTag();
	}
	
	public String getSecondsByNumber(int number){
		if(number == 1) return getSecondsSingularTag();
		return getSecondsPluralTag();
	}
	
	public String getMilisecondsByNumber(int number){
		if(number == 1) return getMilisecondsSingularTag();
		return getMilisecondsPluralTag();
	}

	
	
	
}
