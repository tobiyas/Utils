package de.tobiyas.util.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import de.tobiyas.util.collections.CaseInsenesitveMap;
import org.apache.commons.codec.binary.Base64;

public class AutoConfigSerializable implements ConfigurationSerializable {
	
	
	/**
	 * Deserializes the Config.
	 * @param serialized to use.
	 */
	public void fillWithData(Map<String,Object> serialized) {
		//First, lowercase the map:
		Map<String,Object> lowercasedMap = new CaseInsenesitveMap<Object>(serialized);
		
		//Set the fields:
		Class<? extends AutoConfigSerializable> clazz = getClass();
		for(Field field : clazz.getDeclaredFields()){
			//Should not happen, but happens in old Java versions.
			if(field == null) continue;
			
			//Do not serialize static fields!
			if(isFieldStatic(field)) continue;

			//if it should not serialize, it has an Annotation:
			if(field.getAnnotation(DoNotSerialize.class) != null) continue;
			
			try{
				String name = field.getName();
				Object toSet = lowercasedMap.get(name);
				if(toSet == null) continue;
				
				//Deserialize special Stuff:
				field.setAccessible(true);
				try{ field.set(this, specialDeserialize(toSet)); }catch(Throwable exp){ exp.printStackTrace(); }
			}catch(Throwable exp){ exp.printStackTrace(); }
		}
	}
	
	
	
	@Override
	public Map<String, Object> serialize() {
		Map<String,Object> map = new HashMap<String, Object>();
		Class<? extends AutoConfigSerializable> clazz = getClass();
		for(Field field : clazz.getDeclaredFields()){
			//Should not happen, but happens in old Java versions.
			if(field == null) continue;
			
			//Do not serialize static fields!
			if(isFieldStatic(field)) continue;

			//if it should not serialize, it has an Annotation:
			if(field.getAnnotation(DoNotSerialize.class) != null) continue;
			
			try{
				String name = field.getName();

				//Be sure that the field may be accessed!
				field.setAccessible(true);
				Object obj = specialSerialize(field.get(this));
				if(obj == null) continue;
				
				//At last serialize:
				map.put(name, obj);
			}catch(Throwable exp){ exp.printStackTrace(); }
		}
		
		return map;
	}
	
	
	private static final int STATIC_MOD    = 0x0008;
	private static boolean isFieldStatic(Field field){
        return field != null && (field.getModifiers() & STATIC_MOD) != 0;
	}
	
	
	/**
	 * Serializes special stuff.
	 * @param obj to serailize.
	 * @return the special serialized data.
	 */
	private static Object specialSerialize(Object obj){
		if(obj == null) return null;
		if(isDefaultSerializable(obj)) return obj;
		
		//Try serializable deserialization:
		if(obj instanceof Serializable) obj = serializeSerializable((Serializable) obj);
		
		//Do special conversion:
		//if(obj instanceof UUID) obj = "UUID_"+((UUID)obj).toString();
		
		return obj;
	}
	
	/**
	 * Deserializes special Stuff.
	 * @param obj to deserialize.
	 * @return the deserialized special obj or the same as passed here.
	 */
	private static Object specialDeserialize(Object obj){
		if(obj == null) return null;
		if(isDefaultSerializable(obj)) return obj;
		
		//Serialize serializables:
		if(obj instanceof String) obj = deserializeDeserializable(obj);
		
		//Do special conversion:
		//if(obj instanceof String && ((String)obj).startsWith("UUID_")) obj = UUID.fromString(((String)obj).replace("UUID_", ""));
		
		return obj;
	}
	
	
	
	private static boolean isDefaultSerializable(Object obj){
		if(obj instanceof ConfigurationSerializable) return true;
		if(obj instanceof String) return true;
		if(obj instanceof Boolean) return true;
		if(obj instanceof Number) return true;
		if(obj instanceof List) return true;
		
		return false;
	}
	
	
	private static final String SERIALIZED_PRE = "SERIALIZED_";
	
	private static Object serializeSerializable(Serializable ser){
		try{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(ser);
			objOut.flush();
			
			out.flush();
			return SERIALIZED_PRE + Base64.encodeBase64String(out.toByteArray());
		}catch(Throwable exp){ exp.printStackTrace(); }
		return ser;
	}
	
	private static Object deserializeDeserializable(Object toDeserialize){
		if(!(toDeserialize instanceof String)) return toDeserialize;
		String serialized = (String) toDeserialize;
		if(!serialized.startsWith(SERIALIZED_PRE)) return serialized;
		serialized = serialized.substring(SERIALIZED_PRE.length());
		
		try{
			ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeBase64(serialized));
			ObjectInputStream objIn = new ObjectInputStream(in);
			Object obj = objIn.readObject();
			return obj == null ? serialized : obj;
		}catch(Throwable exp){}
		return serialized;
	}
	
}
