package de.tobiyas.util.evaluations.parts.leafs;

import java.util.Map;

import de.tobiyas.util.evaluations.parts.Calculation;

public class Number implements Calculation {

	/**
	 * the number to use.
	 */
	private final double number;
	
	
	public Number(double number) {
		this.number = number;
	}
	
	
	@Override
	public double calculate(Map<String,Double> variables) {
		return number;
	}
	
	@Override
	public String toString() {
		return String.valueOf(number);
	}

}
