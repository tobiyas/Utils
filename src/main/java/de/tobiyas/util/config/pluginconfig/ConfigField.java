package de.tobiyas.util.config.pluginconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A representation of a Config field of the Configuration
 * 
 * @author Tobiyas
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigField {

	
	/**
	 * The path to the Value
	 * 
	 * @return the Path to the Value
	 */
	String path();
	
	/**
	 * The Description of the Config Field
	 * 
	 * @return description.
	 */
	String description() default "";
	
	
	/**
	 * The DefaultValue as String.
	 * 
	 * @return the DefaultValue serialized as String
	 */
	String defaultValueAsString() default "";
}
