package de.tobiyas.util.evaluations.parts.operators.single;

import java.util.Map;

import de.tobiyas.util.evaluations.parts.Abstract1PartOperator;

public class SquareRoot extends Abstract1PartOperator {

	@Override
	public double calculate(Map<String, Double> variables) {
		return Math.sqrt(part.calculate(variables));
	}
	

	@Override
	protected String getOperationSymbol() {
		return "SQRT";
	}

}
