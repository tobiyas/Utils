package de.tobiyas.util.evaluations.parts;

public interface SinglePartOperator extends Calculation {

	/**
	 * Returns the part.
	 * @return the part
	 */
	public Calculation getPart();
	
	
	/**
	 * Sets the part.
	 * @param calculation to set.
	 */
	public void setPart(Calculation calculation);
	
	/**
	 * Returns if has it's part.
	 * @return true if has it's part.
	 */
	public boolean hasPart();
	
}
