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

public class AchievementJSONResolver {

	
	/**
	 * Resolves an Achievement.
	 * 
	 * @param achievmentName to solve
	 * 
	 * @param label to link to
	 * 
	 * @return the resolved RAW data
	 */
	public static String resolveAchievement(String achievmentName, String label){
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "hoverEvent:{"
						+ "action:show_achievement,"
						+ "value:\"" + achievmentName + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
	
	/**
	 * Resolves an Achievement.
	 * 
	 * @param achievmentName to solve
	 * 
	 * @param label to link to
	 * 
	 * @return the resolved RAW data
	 */
	public static JsonObject resolveAchievementJSON(String achievmentName, String label){
		JsonObject hoverEvent = new JsonObject();
		hoverEvent.addProperty("action", "show_achievement");
		hoverEvent.addProperty("value", achievmentName);
		
		JsonObject object = new JsonObject();
		object.addProperty("text", label);
		object.add("hoverEvent", hoverEvent);		

		return object;
	}
}
