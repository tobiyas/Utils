package de.tobiyas.util.math;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;

public class Spiral {

	
	
	/**
	 * Generates a list of Location for the Location passed.
	 * <br>The bottom location is used. as base.
	 * 
	 * @param base to use
	 * @param width to use
	 * @param height to use
	 * @param spiralOffset to use in Percent
	 * 
	 * @return a list of spiraled Elements.
	 */
	public static List<Location> generateSpiral(
			Location base, 
			double width, double height, 
			double distBetween, double spiralOffset){
		
		double yOffset = 0;
		double rotOffset = 360d / (height / distBetween);
		int toUse = (int) (height / distBetween);
		List<Location> list = new LinkedList<Location>();
		for(int i = 0; i < toUse; i++){
			double xOffset = Math.sin(Math.toRadians(spiralOffset)) * width;
			double zOffset = Math.cos(Math.toRadians(spiralOffset)) * width;
			list.add(base.clone().add(xOffset, yOffset, zOffset));
			
			
			//Add offsets:
			yOffset += distBetween;
			spiralOffset += rotOffset;
			spiralOffset %= 360;
		}
		
		return list;
	}	
	
	
}
