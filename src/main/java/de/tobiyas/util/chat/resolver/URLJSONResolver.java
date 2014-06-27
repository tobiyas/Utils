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

public class URLJSONResolver {

	
	/**
	 * Creates an Label for an URL
	 * 
	 * @param toParse to parse
	 * @param label to show
	 * 
	 * @return the parsed JSON string
	 */
	public static String resolveURL(String url, String label) {
		String rawData = ""
				+ "{"
					+ "\"text\"" + ":" + "\"" + label + "\","
					+ "\"clickEvent\":{"
						+ "\"action\":\"open_url\","
						+ "\"value\":\"" + url + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
	
	
	public static JsonObject addURLClickable(JsonObject toAddTo, String url){
		JsonObject hover = new JsonObject();
		hover.addProperty("action", "open_url");
		hover.addProperty("value", url);
		
		toAddTo.add("clickEvent", hover);
		return toAddTo;
	}

	
	/**
	 * Creates an Label for an URL
	 * 
	 * @param toParse to parse
	 * @param label to show
	 * 
	 * @return the parsed JSON Object
	 */
	public static JsonObject resolveURLJSON(String url, String label) {
		JsonObject object = PlainTextJSONResolver.getRawFromPlainTextJSON(label);
		return addURLClickable(object, url);
	}

}
