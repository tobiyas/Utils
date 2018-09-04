package de.tobiyas.util.formating;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

public class ParseUtils {

	
	/**
	 * Parses an Integer from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static int parseInt(String value, int defaultValue){
		try{ return Integer.parseInt(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Integer from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static int parseInt(Object value, int defaultValue){
		if(value == null) return defaultValue;
		try{ return Integer.parseInt(value.toString()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Integer from a String.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static int parseInt(String[] args, int index, int defaultValue){
		try{ return Integer.parseInt(args[index]); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Short from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static short parseShort(String value, short defaultValue){
		try{ return Short.parseShort(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Short from a String.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static short parseShort(String[] args, int index, short defaultValue){
		try{ return Short.parseShort(args[index]); }catch(Throwable exp){ return defaultValue; }
	}
	
	
	/**
	 * Parses an Double from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static double parseDouble(String value, double defaultValue){
		try{ return Double.parseDouble(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Double from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static double parseDouble(Object value, double defaultValue){
		if(value == null) return defaultValue;
		try{ return Double.parseDouble(value.toString()); }catch(Throwable exp){ return defaultValue; }
	}

	/**
	 * Parses an Double from a String.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static double parseDouble(String[] args, int index, double defaultValue){
		try{ return Double.parseDouble(args[index]); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Boolean from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static boolean parseBoolean(String value, boolean defaultValue){
		if(value.equalsIgnoreCase("true")) return true;
		if(value.equalsIgnoreCase("yes")) return true;
		if(value.equalsIgnoreCase("ja")) return true;
		if(value.equalsIgnoreCase("on")) return true;
		
		if(value.equalsIgnoreCase("false")) return false;
		if(value.equalsIgnoreCase("no")) return false;
		if(value.equalsIgnoreCase("nein")) return false;
		if(value.equalsIgnoreCase("off")) return false;
		
		return defaultValue;
	}
	
	/**
	 * Parses an Boolean from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static boolean parseBoolean(Object value, boolean defaultValue){
		if(value == null) return defaultValue;
		return parseBoolean(value.toString(), defaultValue);
	}
	
	/**
	 * Parses an Boolean from a String.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static boolean parseBoolean(String[] args, int index, boolean defaultValue){
		return parseBoolean(args[index], defaultValue);
	}
	
	
	/**
	 * Parses an Float from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static float parseFloat(String value, float defaultValue){
		try{ return Float.parseFloat(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Float from a String.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static float parseFloat(String[] args, int index, float defaultValue){
		try{ return Float.parseFloat(args[index]); }catch(Throwable exp){ return defaultValue; }
	}
	
	
	/**
	 * Parses an String from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static String parseString(Object value, String defaultValue){
		if(value == null) return defaultValue;
		return value.toString();
	}
	
	/**
	 * Parses an String from a String[].
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static String parseString(String[] values, int index, String defaultValue){
		try{ return values[index]; }catch(Throwable exp){ return defaultValue; }
	}
	

	/**
	 * Parses an Long from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static long parseLong(String value, long defaultValue){
		try{ return Long.parseLong(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Long from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static long parseLong(Object value, long defaultValue){
		if(value == null) return defaultValue;
		try{ return Long.parseLong(value.toString()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Long from a String.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static long parseLong(String[] args, int index, long defaultValue){
		try{ return Long.parseLong(args[index]); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an DamageCause from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static DamageCause parseDamageCause(String value, DamageCause defaultValue){
		try{ return DamageCause.valueOf(value.toUpperCase()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an DamageCause from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static DamageCause parseDamageCause(Object value, DamageCause defaultValue){
		if(value == null) return defaultValue;
		try{ return DamageCause.valueOf(value.toString().toUpperCase()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an DamageCause from a String.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static DamageCause parseDamageCause(String[] args, int index, DamageCause defaultValue){
		try{ return DamageCause.valueOf(args[index].toUpperCase()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an EntityType from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static EntityType parseEntityType(String value, EntityType defaultValue){
		try{ return EntityType.valueOf(value.toUpperCase()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an EntityType from an Object.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static EntityType parseEntityType(Object value, EntityType defaultValue){
		if(value == null) return defaultValue;
		try{ return EntityType.valueOf(value.toString().toUpperCase()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an EntityType from an String Array.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static EntityType parseEntityType(String[] args, int index, EntityType defaultValue){
		try{ return EntityType.valueOf(args[index].toUpperCase()); }catch(Throwable exp){ return defaultValue; }
	}
	
	
	/**
	 * Parses an Material from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Material parseMaterial(String value, Material defaultValue){
		try{ return Material.matchMaterial(value); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Material from an Object.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Material parseMaterial(Object value, Material defaultValue){
		if(value == null) return defaultValue;
		try{ return Material.matchMaterial(value.toString()); }catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Material from an String Array.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Material parseMaterial(String[] args, int index, Material defaultValue){
		try{ return Material.matchMaterial(args[index]); }catch(Throwable exp){ return defaultValue; }
	}
	
	
	/**
	 * Parses a location from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Location parseLocation(String value, Location defaultValue){
		try{
			String[] parts = value.split( Pattern.quote(";") );
			if( parts.length != 4 ) {
				parts = value.split( Pattern.quote( "#" ) );
				if( parts.length != 4 ) return defaultValue;
			}
			
			World world = Bukkit.getWorld( parts[0] );
			double x = Double.parseDouble( parts[1] );
			double y = Double.parseDouble( parts[2] );
			double z = Double.parseDouble( parts[3] );
			
			if( world == null ) return defaultValue;
			return new Location( world, x, y, z );
		}catch(Throwable exp){ 
			return defaultValue; 
		}
	}
	
	/**
	 * Parses an Location from an Object.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Location parseLocationObj( Object value, Location defaultValue ){
		if(value == null) return defaultValue;
		try{ 
			if( value instanceof Location ) return (Location) value;
			
			return parseLocation( value.toString(), defaultValue ); 
		}catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Location from an String Array.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Location parseLocation( String[] args, int index, Location defaultValue ){
		try{ 
			return parseLocation( args[index], defaultValue );
		}catch(Throwable exp){ return defaultValue; }
	}
	
	
	/**
	 * Parses a Vector from a String.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Vector parseVector(String value, Vector defaultValue){
		try{
			String[] parts = value.split( Pattern.quote(";") );
			if( parts.length != 3 ) {
				parts = value.split( Pattern.quote( "#" ) );
				if( parts.length != 3 ) return defaultValue;
			}
			
			double x = Double.parseDouble( parts[0] );
			double y = Double.parseDouble( parts[1] );
			double z = Double.parseDouble( parts[2] );
			
			return new Vector( x, y, z );
		}catch(Throwable exp){ 
			return defaultValue; 
		}
	}
	
	/**
	 * Parses an Vector from an Object.
	 * @param value to parse
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Vector parseLocation( Object value, Vector defaultValue ){
		if(value == null) return defaultValue;
		try{
			if( value instanceof Vector ) return (Vector) value;
			
			return parseVector( value.toString(), defaultValue ); 
		}catch(Throwable exp){ return defaultValue; }
	}
	
	/**
	 * Parses an Vector from an String Array.
	 * @param args to parse
	 * @param index to use
	 * @param defaultValue to use if failed.
	 * @return the parsed value or default value.
	 */
	public static Vector parseVector( String[] args, int index, Vector defaultValue ){
		try{ 
			return parseVector( args[index], defaultValue );
		}catch(Throwable exp){ return defaultValue; }
	}

}
