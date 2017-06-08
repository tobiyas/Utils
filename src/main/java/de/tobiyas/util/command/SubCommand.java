package de.tobiyas.util.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

	
	/**
	 * The name of the Sub-Command.
	 * @return the name of the Sub-Command.
	 */
	String subCommand();
	
	/**
	 * The Description of the Sub-Command.
	 * @return the description.
	 */
	String description() default "No description";
	
	/**
	 * The Permission needed to use this sub-Command.
	 * @return the permission needed.
	 */
	String permission() default "";
	
	/**
	 * If the Permission is attached to the Main Command.
	 * @return if appended to Main Permission.
	 */
	boolean appendPermissionToMainCommand() default true;
	
	/**
	 * If only players may use this
	 * @return true if only players may use this.
	 */
	boolean playerOnly() default false;
	
	/**
	 * If only the Console may use this.
	 * @return true if only console.
	 */
	boolean consoleOnly() default false;
	
	
	/**
	 * If op bypasses the Permissions.
	 * @return true if op bypasses the permission.
	 */
	boolean opBypassedPerm() default false;
	
	/**
	 * The Help-Text. If not present, it will be generated.
	 * @return the help text.
	 */
	String help() default "";
	
}
