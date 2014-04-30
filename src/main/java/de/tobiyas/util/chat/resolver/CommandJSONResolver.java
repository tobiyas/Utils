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
package de.tobiyas.util.chat.resolver;

import com.google.gson.JsonObject;

public class CommandJSONResolver {

	/**
	 * Resolves the Command to an Command to click.
	 * 
	 * @param command to click
	 * 
	 * @param label to click on
	 * 
	 * @return
	 */
	public static String resolveCommand(String command, String label){
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "clickEvent:{"
						+ "action:run_command,"
						+ "value:\"" + command + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
	
	

	/**
	 * Resolves the Command to an Command to click.
	 * 
	 * @param command to click
	 * 
	 * @param label to click on
	 * 
	 * @return
	 */
	public static JsonObject resolveCommandJSON(String command, String label){
		JsonObject object = new JsonObject();
		object.addProperty("text", label);
		
		JsonObject clickEvent = new JsonObject();
		clickEvent.addProperty("action", "run_command");
		clickEvent.addProperty("value", command);
		
		object.add("clickEvent", clickEvent);
		return object;
	}
}
