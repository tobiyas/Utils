package de.tobiyas.util.evaluations.parts.leafs;

import java.util.Map;

import de.tobiyas.util.evaluations.parts.Calculation;

public class Variable implements Calculation {

	/**
	 * the variable to use.
	 */
	private final String variable;
	
	
	public Variable(String variable) {
		this.variable = variable.replace("%", "").replace("{", "").replace("}", "").toLowerCase();
	}
	
	
	@Override
	public double calculate(Map<String,Double> variables) {
		return variables.containsKey(variable) ? variables.get(variable) : 0;
	}

	@Override
	public String toString() {
		return "%"+variable.toUpperCase()+"%";
	}
}
