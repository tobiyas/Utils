package de.tobiyas.util.evaluations.parts.operators.single;

import java.util.Map;

import de.tobiyas.util.evaluations.parts.Abstract1PartOperator;

public class Square extends Abstract1PartOperator {

	@Override
	public double calculate(Map<String, Double> variables) {
		double res = part.calculate(variables);
		return res * res;
	}
	

	@Override
	protected String getOperationSymbol() {
		return "SQR";
	}

}
