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
