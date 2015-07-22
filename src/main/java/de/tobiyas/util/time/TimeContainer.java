package de.tobiyas.util.time;

import java.util.Arrays;
import java.util.Collection;

public class TimeContainer {

	
	private final int days;
	private final int hours;
	private final int minutes;
	private final int seconds;
	private final int miliseconds;
	
	/**
	 * The Constants to use for output.
	 */
	private TimeNameConstants constants;
	
	

	public TimeContainer(int days, int hours, int minutes, int seconds, int miliseconds){
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.miliseconds = miliseconds;
	}
	
	
	public TimeContainer(int hours, int minutes, int seconds, int miliseconds) {
		this(0, hours, minutes, seconds, miliseconds);
	}
	
	public TimeContainer(int minutes, int seconds, int miliseconds) {
		this(0, minutes, seconds, miliseconds);
	}
	
	public TimeContainer(int seconds, int miliseconds) {
		this(0, seconds, miliseconds);
	}
	
	public TimeContainer(int miliseconds) {
		this(0, miliseconds);
	}
	
	
	public TimeContainer() {
		this(0);
	}
	
	
	
	public int getDays() {
		return days;
	}


	public int getHours() {
		return hours;
	}


	public int getMinutes() {
		return minutes;
	}


	public int getSeconds() {
		return seconds;
	}


	public int getMiliseconds() {
		return miliseconds;
	}


	public void setConstants(TimeNameConstants constants) {
		if(constants == null) return;
		
		this.constants = constants;
	}
	
	
	/**
	 * This Does a Convertion if wanted.
	 * 
	 * @param toDisplay the Times to display.
	 * 
	 * @return the String representation.
	 */
	public String toString(TimeToDisplay... toDisplay) {
		TimeContainer newContainer = changeToDisplayTypes(Arrays.asList(toDisplay));
		return newContainer.toString();
	}
	
	
	/**
	 * Changes the Display Type to avoid the Times not present.
	 * 
	 * @param toDisplay the stuff to display.
	 * 
	 * @return the stuff to display.
	 */
	private TimeContainer changeToDisplayTypes(Collection<TimeToDisplay> toDisplay) {
		if(toDisplay.contains(TimeToDisplay.All)) return this; 
		
		int newDays = days;
		int newHours = hours;
		int newMinutes = minutes;
		int newSeconds = seconds;
		int newMiliseconds = miliseconds;
		
		if(!toDisplay.contains(TimeToDisplay.Days)) {
			newHours += newDays * 24;
			newDays = 0;
		}
		
		if(!toDisplay.contains(TimeToDisplay.Hours)) {
			newMinutes += newHours * 60;
			newHours = 0;
		}
		
		if(!toDisplay.contains(TimeToDisplay.Minutes)) {
			newSeconds += newMinutes * 60;
			newMinutes = 0;
		}
		
		if(!toDisplay.contains(TimeToDisplay.Seconds)) {
			newMiliseconds += newSeconds * 1000;
			newSeconds = 0;
		}
		
		if(!toDisplay.contains(TimeToDisplay.Miliseconds)) {
			newMiliseconds = 0;
		}
		
		
		return new TimeContainer(newDays, newHours, newMinutes, newSeconds, newMiliseconds);
	}

	
	
	@Override
	public String toString() {
		boolean hasDays = days > 0;
		boolean hasHours = hours > 0;
		boolean hasMinutes = minutes > 0;
		boolean hasSeconds = seconds > 0;
		boolean hasMiliseconds = miliseconds > 0;
		
		StringBuilder builder = new StringBuilder();
		if(hasDays) builder.append(days + " " + constants.getDayTag() + ", ");
		if(hasHours) builder.append(hours + " " + constants.getHourTag() + ", ");
		if(hasMinutes) builder.append(minutes + " " + constants.getMinuteTag() + ", ");
		if(hasSeconds) builder.append(seconds + " " + constants.getSecondsTag() + ", ");
		
		if(hasMiliseconds) builder.append(miliseconds + " " + constants.getMilisecondsTag());
		return builder.toString();
	}
	

	@Override
	public int hashCode() {
		return miliseconds 
				+ (1000 * seconds) 
				+ (1000 * 60 * minutes) 
				+ (1000 * 60 * 60 * hours) 
				+ (1000 * 60 * 60 * 24 * days);
	}
	
	
	
	
	public enum TimeToDisplay{
		Days,
		Hours,
		Minutes,
		Seconds,
		Miliseconds,
		All;
	}
	
}
