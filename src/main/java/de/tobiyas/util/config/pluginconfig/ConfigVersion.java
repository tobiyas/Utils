package de.tobiyas.util.config.pluginconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Version of a Config.
 * 
 * @author tobiyas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface ConfigVersion {

	/**
	 * The int version of the Config.
	 * 
	 * @return int version of the Config.
	 */
	int version();
}
