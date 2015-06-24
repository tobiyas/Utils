package de.tobiyas.util.chat.resolver;

import org.bukkit.ChatColor;

import com.google.gson.JsonObject;


public class TextStyleJSONResolver {

	
	
	/**
	 * Adds strike Through.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the object fore Building.
	 */
	public static JsonObject addStrikethrough(JsonObject obj){
		obj.addProperty("strikethrough", "true");
		return obj;
	}
	

	/**
	 * Removes strike Through.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the Object for Building.
	 */
	public static JsonObject removeStrikethrough(JsonObject obj){
		obj.remove("strikethrough");
		return obj;
	}
	
	
	/**
	 * Adds Bold.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the object fore Building.
	 */
	public static JsonObject addBold(JsonObject obj){
		obj.addProperty("bold", "true");
		return obj;
	}
	

	/**
	 * Removes Bold.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the Object for Building.
	 */
	public static JsonObject removeBold(JsonObject obj){
		obj.remove("bold");
		return obj;
	}
	
	
	/**
	 * Adds Italic.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the object fore Building.
	 */
	public static JsonObject addItalic(JsonObject obj){
		obj.addProperty("italic", "true");
		return obj;
	}
	

	/**
	 * Removes Italic.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the Object for Building.
	 */
	public static JsonObject removeItalic(JsonObject obj){
		obj.remove("italic");
		return obj;
	}
	
	
	/**
	 * Adds Underlined.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the object fore Building.
	 */
	public static JsonObject addUnderlined(JsonObject obj){
		obj.addProperty("underlined", "true");
		return obj;
	}
	

	/**
	 * Removes Underlined.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the Object for Building.
	 */
	public static JsonObject removeUnderlined(JsonObject obj){
		obj.remove("underlined");
		return obj;
	}
	
	/**
	 * Adds Magic.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the object fore Building.
	 */
	public static JsonObject addMagic(JsonObject obj){
		obj.addProperty("obfuscated", "true");
		return obj;
	}
	
	
	/**
	 * Removes Magic.
	 * 
	 * @param obj to modify.
	 * 
	 * @return the Object for Building.
	 */
	public static JsonObject removeMagic(JsonObject obj){
		obj.remove("obfuscated");
		return obj;
	}
	
	
	/**
	 * Adds a color to the JSON object.
	 * 
	 * @param toAddTo to add to
	 * @param color to add
	 * 
	 * @return the passed toAddTo for chaining.
	 */
	public static JsonObject addColor(JsonObject toAddTo, ChatColor color){
		String convColor = convert(color);
		if(convColor == null) return toAddTo;
		
		toAddTo.addProperty("color", convColor);
		return toAddTo;
	}

	
	
	/**
	 * Converts a ChatColor to a String to use.
	 * 
	 * @param color to convert.
	 * 
	 * @return the string representation.
	 */
	protected static String convert(ChatColor color){
		switch(color){
			case BOLD:
			case ITALIC:
			case MAGIC:
			case STRIKETHROUGH:
			case RESET:
			case UNDERLINE:
				return null;
			
			default: return color.name().toLowerCase();
		}
	}

	
}
