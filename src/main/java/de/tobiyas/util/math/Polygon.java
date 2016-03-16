package de.tobiyas.util.math;

import java.util.List;

import org.bukkit.Location;

public class Polygon {

	
	
	/**
	 * Checks if the Point is in the Polygon.
	 * <br>WARNING: This function ignores Y-Coordinates and Worlds!!!!
	 * 
	 * @param polygon to use
	 * @param toCheck to check if in.
	 * @return true if is in.
	 */
	public static boolean isInPoly2d(List<Location> polygon, Location toCheck){
		int i;
		int j;
		Location locI;
		Location locJ;
		boolean result = false;
		for (i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
			locI = polygon.get(i);
			locJ = polygon.get(j);
			
			if ( (locI.getZ() > toCheck.getZ()) != (locJ.getZ() > toCheck.getZ())
					&& (toCheck.getX() < (locJ.getX() - locI.getX()) * (toCheck.getZ() - locI.getZ()) 
							/ (locJ.getZ() - locI.getZ()) + locI.getX())) {
				result = !result;
			}
		}
		return result;
	}
	
}
