package de.tobiyas.util.evaluations.parts.operators;

import java.util.Map;

import de.tobiyas.util.evaluations.parts.AbstractOperator;
import de.tobiyas.util.evaluations.parts.Calculation;

public class Multiply extends AbstractOperator {

	
	public Multiply(Calculation part1, Calculation part2) {
		this.part1 = part1;
		this.part2 = part2;
	}
	
	public Multiply() {}
	
	
	@Override
	public double calculate(Map<String,Double> variables) {
		return part1.calculate(variables) * part2.calculate(variables);
	}

	@Override
	protected char getOperationSymbol() {
		return '*';
	}
}
