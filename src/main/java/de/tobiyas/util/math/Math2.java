package de.tobiyas.util.math;

public class Math2 {
	
	/**
	 * Clamps between the 2 values.
	 */
	public static double clamp(double min, double value, double max){
		return Math.max(min, Math.min(value, max));
	}
	
	/**
	 * Clamps between the 2 values.
	 */
	public static int clamp(int min, int value, int max){
		return (int)clamp((double)min, (double)value, (double)max);
	}
	
	/**
	 * Clamps between the 2 values.
	 */
	public static float clamp(float min, float value, float max){
		return (float)clamp((double)min, (double)value, (double)max);
	}
}
