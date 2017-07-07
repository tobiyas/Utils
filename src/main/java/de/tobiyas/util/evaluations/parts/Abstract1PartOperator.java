package de.tobiyas.util.evaluations.parts;

public abstract class Abstract1PartOperator implements SinglePartOperator {

	
	protected Calculation part;
	

	@Override
	public Calculation getPart() {
		return part;
	}


	@Override
	public void setPart(Calculation calculation) {
		this.part = calculation;
	}

	@Override
	public boolean hasPart() {
		return part != null;
	}
	
	
	@Override
	public String toString() {
		return "(" + getOperationSymbol() + "(" + part + "))";
	}
	
	
	/**
	 * Returns the operation symbol.
	 * @return the symbol
	 */
	protected abstract String getOperationSymbol();

}
