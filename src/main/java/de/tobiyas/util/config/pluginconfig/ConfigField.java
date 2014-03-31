/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
