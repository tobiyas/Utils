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

public class PopupJSONResolver {

	
	/**
	 * Resolves the Text to an Popup.
	 * 
	 * @param message to show
	 * 
	 * @param label to hover on
	 * 
	 * @return
	 */
	public static String resolve(String message, String label){
		if(label == null || "".equals(label)) label = message;
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "hoverEvent:{"
						+ "action:show_text,"
						+ "value:\"" + message + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
	

	
	/**
	 * Resolves the Text to an Popup.
	 * 
	 * @param message to show
	 * 
	 * @param label to hover on
	 * 
	 * @return the JSON object.
	 */
	public static JsonObject resolveJSON(String message, String label){
		JsonObject object = new JsonObject();
		
		JsonObject hoverEvent = new JsonObject();
		hoverEvent.addProperty("action", "show_text");
		hoverEvent.addProperty("value", message);
		
		object.addProperty("text", label);
		object.add("hoverEvent", hoverEvent);
		return object;
	}
}
