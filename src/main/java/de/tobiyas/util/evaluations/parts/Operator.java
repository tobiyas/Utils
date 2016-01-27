package de.tobiyas.util.evaluations.parts;

public interface Operator extends Calculation {

	/**
	 * Returns the first part.
	 * @return the first part
	 */
	public Calculation getPart1();
	
	/**
	 * Returns the second part.
	 * @return the second part.
	 */
	public Calculation getPart2();
	
	
	/**
	 * Sets the first part.
	 * @param calculation to set.
	 */
	public void setPart1(Calculation calculation);
	
	/**
	 * Sets the first part.
	 * @param calculation to set.
	 */
	public void setPart2(Calculation calculation);
	
	/**
	 * Returns if has both parts.
	 * @return true if has both parts.
	 */
	public boolean hasBothParts();
	
}
