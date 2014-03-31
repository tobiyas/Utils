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
package de.tobiyas.util.collections;

import java.util.HashMap;
import java.util.Map;

public class HashMapUtils {

	
	/**
	 * Generates a Map (HashMap) with the Entries passed as String[] [].
	 *  
	 * @param entries to parse
	 * 
	 * @return the hashMap filled with the Values.
	 */
	public static Map<String,String> generateStringStringMap(String[] ... entries){
		Map<String,String> returnMap = new HashMap<String,String>();
		
		for(String[] entry : entries){
			if(entry.length !=2 ) continue;
			
			String key = entry[0];
			String value = entry[1];
			
			returnMap.put(key, value);
		}
		
		return returnMap;
	}

	/**
	 * Generates a Map (HashMap) with the Entries passed as Strings.
	 * <br> Synthax: (key,value,key,value,key,value, ....)
	 *  
	 * @param entries to parse
	 * 
	 * @return the hashMap filled with the Values.
	 */
	public static Map<String,String> generateStringStringMap(String ... entries){
		if((entries.length % 2) != 0)  return new HashMap<String, String>();
		String[][] valueArray = new String[entries.length / 2][2];
		
		for(int i = 0; i < entries.length; i+=2){
			valueArray[i / 2][0] = entries[i];
			valueArray[i / 2][1] = entries[i+1];
		}
		
		return generateStringStringMap(valueArray);
	}
}
