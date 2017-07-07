package de.tobiyas.util.evaluations.parts;

public abstract class Abstract2PartOperator implements Operator {

	
	protected Calculation part1;
	protected Calculation part2;
	

	@Override
	public Calculation getPart1() {
		return part1;
	}

	@Override
	public Calculation getPart2() {
		return part2;
	}

	@Override
	public void setPart1(Calculation calculation) {
		this.part1 = calculation;
	}

	@Override
	public void setPart2(Calculation calculation) {
		this.part2 = calculation;
	}
	
	@Override
	public boolean hasBothParts() {
		return part1 != null && part2 != null;
	}
	
	
	@Override
	public String toString() {
		return "(" + part1 + getOperationSymbol() + part2 + ")";
	}
	
	
	/**
	 * Returns the operation symbol.
	 * @return the symbol
	 */
	protected abstract char getOperationSymbol();

}
