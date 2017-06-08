package de.tobiyas.util.command.params;

import org.bukkit.command.CommandSender;

import de.tobiyas.util.command.CommandParamType;

public abstract class AbstractCommandParam {
	
	
	/**
	 * The Type of the Param.
	 */
	private final CommandParamType type;	
	
	/**
	 * The name of the Command Parameter.
	 */
	private final String name;
	
	/**
	 * If the value is optional.
	 */
	private final boolean optional;
	
	
	
	
	public AbstractCommandParam(CommandParamType type, String name, boolean optional) {
		this.type = type;
		this.name = name;
		this.optional = optional;
	}



	/**
	 * If the Param is optional
	 * @return true if optional.
	 */
	public boolean isOptional() {
		return optional;
	}
	
	public String getName() {
		return name;
	}


	/**
	 * The Type of the Param.
	 * @return the type.
	 */
	public CommandParamType getType() {
		return type;
	}


	/**
	 * Returns if the passed argument is a valid Param.
	 * @param arg to check
	 * @return true if valid.
	 */
	public abstract boolean isValid(String arg);

	
	/**
	 * Sends help to this Type of Param.
	 * @param sender to send to.
	 */
	public abstract void sendHelp(String arg, CommandSender sender);
	
	/**
	 * Converts the arg to the Correct type.
	 * @param arg to convert.
	 * @return the converted Type
	 */
	public abstract Object toType(String arg);

}
