package de.tobiyas.util.evaluations.parts.leafs;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Pattern;

import de.tobiyas.util.evaluations.parts.Calculation;

public class Variable implements Calculation {
	
	private static final DecimalFormat format = new DecimalFormat("0.0");

	/**
	 * the variable to use.
	 */
	private final String variable;
	
	/**
	 * The default value to use if none found.
	 */
	private final double defaultValue;
	
	
	public Variable(String variable) {
		String var = variable.replace("%", "").replace("{", "").replace("}", "").toLowerCase();
		double def = 0;
		
		//Check if we have a present value:
		if(var.contains("=")) {
			String[] parts = var.split(Pattern.quote("="));
			var = parts[0];
			
			try{
				def = Double.parseDouble(parts[1]);
			}catch (Exception e) {}
		}
		
		this.variable = var;
		this.defaultValue = def;
	}
	
	
	@Override
	public double calculate(Map<String,Double> variables) {
		return variables.containsKey(variable) ? variables.get(variable) : defaultValue;
	}

	
	@Override
	public String toString() {
		String defaultAdd = defaultValue == 0 ? "" : ("="+format.format(defaultValue));
		return "%"+variable.toUpperCase()+defaultAdd+"%";
	}
}
