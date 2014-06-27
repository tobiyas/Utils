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

public class CommandSuggestionJSONResolver {

	/**
	 * Resolves the Command to an Command to click.
	 * 
	 * @param command to click
	 * 
	 * @param label to click on
	 * 
	 * @return
	 */
	public static String resolveCommandSuggestion(String command, String label){
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "clickEvent:{"
						+ "action:suggest_command,"
						+ "value:\"" + command + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
	

	/**
	 * Adds a command Clickable.
	 * 
	 * @param toAddto to add to
	 * @param command to add
	 * 
	 * @return
	 */
	public static JsonObject addCommandSuggestionClickable(JsonObject toAddto, String command){
		JsonObject clickEvent = new JsonObject();
		clickEvent.addProperty("action", "suggest_command");
		clickEvent.addProperty("value", command);
		
		toAddto.add("clickEvent", clickEvent);
		return toAddto;
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
	public static JsonObject resolveCommandSuggestionJSON(String command, String label){
		JsonObject object = PlainTextJSONResolver.getRawFromPlainTextJSON(label);
		return addCommandSuggestionClickable(object, command);
	}
}
