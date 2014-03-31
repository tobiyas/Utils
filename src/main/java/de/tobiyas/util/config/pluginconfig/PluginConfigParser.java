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

import java.io.File;
import java.lang.reflect.Field;

import de.tobiyas.util.config.YAMLConfigExtended;

public class PluginConfigParser {

	/**
	 * Fills an Config file to 
	 * 
	 * @param config to fill
	 * @param file to load from
	 * 
	 * @throws ConfigurationFailedException when config fails.
	 */
	public static void fillConfig(PluginConfig config, File file) throws ConfigurationFailedException {
		YAMLConfigExtended ymlConfig = new YAMLConfigExtended(file).load();
		if(!ymlConfig.getValidLoad()){
			throw new ConfigurationFailedException("Could not load Config File.");
		}
		
		Field[] fields = config.getClass().getFields();
		for(Field field : fields){
			try{
				field.setAccessible(true);
				ConfigField configField = field.getAnnotation(ConfigField.class);
				if(configField == null) continue;
				
				Class<?> fieldClass = field.get(config).getClass();
				Object value = ymlConfig.get(configField.path(), configField.defaultValueAsString());
				value = modifyToCorrectValue(fieldClass, value);
				
				field.set(config, value);
				
			}catch(SecurityException exp){
				continue;
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}
			
		}
	}

	private static Object modifyToCorrectValue(Class<?> fieldClass, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
