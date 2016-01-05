package de.tobiyas.util.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import de.tobiyas.util.collections.CaseInsenesitveMap;

public class AutoConfigSerializable implements ConfigurationSerializable {

	/**
	 * Empty for normal construction:
	 */
	public AutoConfigSerializable() {}
	
	
	/**
	 * Deserializes the Config.
	 * @param serialized to use.
	 */
	public AutoConfigSerializable(Map<String,Object> serialized) {
		//First, lowercase the map:
		Map<String,Object> lowercasedMap = new CaseInsenesitveMap<Object>(serialized);
		
		//Set the fields:
		Class<? extends AutoConfigSerializable> clazz = getClass();
		for(Field field : clazz.getDeclaredFields()){
			try{
				String name = field.getName();
				Object toSet = lowercasedMap.get(name);
				if(toSet == null) continue;
				
				field.setAccessible(true);
				try{ field.set(this, toSet); }catch(Throwable exp){ exp.printStackTrace(); }
			}catch(Throwable exp){ exp.printStackTrace(); }
		}
	}
	
	
	
	@Override
	public Map<String, Object> serialize() {
		Map<String,Object> map = new HashMap<String, Object>();
		Class<? extends AutoConfigSerializable> clazz = getClass();
		for(Field field : clazz.getDeclaredFields()){
			try{
				String name = field.getName();
				Object obj = field.get(this);
				if(obj == null) continue;
				
				//if it should not serialize, it has an Annotation:
				if(field.getAnnotation(DoNotSerialize.class) != null) continue;
				
				//At last serialize:
				map.put(name, obj);
			}catch(Throwable exp){}
		}
		
		return map;
	}
	
}
