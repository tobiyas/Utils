package de.tobiyas.util.location;

import org.bukkit.Location;

public class LocationUtils {
	
	/**
	 * Returns if the Chunk passed is loaded.
	 * Returns true if the Chunk is loaded, false if not.
	 * 
	 * @param location to check
	 * @return true if chunk is loaded.
	 */
	public static boolean isChunkLoaded(Location location){
		if(location == null) return false;
		
		return location.getWorld().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4);
	}
	
}

