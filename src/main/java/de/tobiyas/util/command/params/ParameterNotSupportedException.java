package de.tobiyas.util.command.params;

public class ParameterNotSupportedException extends Exception {
	private static final long serialVersionUID = -894734929000657814L;
	
	
	/**
	 * The Type not supported.
	 */
	private final Class<?> type;
	
	public ParameterNotSupportedException(Class<?> type){
		this.type = type;
	}
	
	
	/**
	 * Gets the type of the not supported Exception.
	 * @return the type.
	 */
	public Class<?> getType() {
		return type;
	}
	
}
