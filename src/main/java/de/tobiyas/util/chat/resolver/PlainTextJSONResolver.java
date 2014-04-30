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

public class PlainTextJSONResolver {

	/**
	 * Returns the Raw JSON String from the Plain Text.
	 * 
	 * @param plainText to translate
	 * 
	 * @return the JSOn String
	 */
	public static String getRawFromPlainText(String plainText){
		plainText = plainText.replace("\\", "\\\\");
		plainText = plainText.replace("\"", "\\\"");
		
		return "{\"text\":\"" + plainText + "\"}";
	}
	

	/**
	 * Returns the Raw JSON String from the Plain Text.
	 * 
	 * @param plainText to translate
	 * 
	 * @return the JSOn Object
	 */
	public static JsonObject getRawFromPlainTextJSON(String plainText){
		plainText = plainText.replace("\\", "\\\\");
		plainText = plainText.replace("\"", "\\\"");
		
		JsonObject object = new JsonObject();
		object.addProperty("text", plainText);
		return object;
	}
}
