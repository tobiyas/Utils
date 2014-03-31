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

public class CaseInsenesitveMap<V> extends HashMap<String, V> {
	private static final long serialVersionUID = -8806664568740114127L;

	@Override
	public V get(Object key) {
		if(key instanceof String){
			key = ((String) key).toLowerCase();
		}
		
		return super.get(key);
	}

	@Override
	public V put(String key, V value) {
		key = key.toLowerCase();
		return super.put(key, value);
	}

	@Override
	public boolean containsKey(Object key) {
		if(key instanceof String){
			key = ((String) key).toLowerCase();
		}
		
		return super.containsKey(key);
	}
}
