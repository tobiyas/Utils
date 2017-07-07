package de.tobiyas.util.evaluations.parts.operators;

import java.util.Map;

import de.tobiyas.util.evaluations.parts.AbstractOperator;
import de.tobiyas.util.evaluations.parts.Calculation;

public class Divide extends AbstractOperator {

	
	public Divide(Calculation part1, Calculation part2) {
		this.part1 = part1;
		this.part2 = part2;
	}

	
	public Divide() {}
	
	@Override
	public double calculate(Map<String,Double> variables) {
		return part1.calculate(variables) / part2.calculate(variables);
	}

	@Override
	protected char getOperationSymbol() {
		return '/';
	}
}
