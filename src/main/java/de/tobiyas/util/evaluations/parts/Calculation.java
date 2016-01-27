package de.tobiyas.util.evaluations.parts;

import java.util.Map;

public interface Calculation {

	/**
	 * calculates the Result.
	 * @param replace variables.
	 * @return the result of the Calculation.
	 */
	public double calculate(Map<String,Double> variables);
	
	
	@Override /** A reader-Friendly to String. */
	public String toString();
	
}
