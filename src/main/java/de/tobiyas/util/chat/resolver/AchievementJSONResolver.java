package de.tobiyas.util.chat.resolver;

public class AchievementJSONResolver {

	
	/**
	 * Resolves an Achievement.
	 * 
	 * @param achievmentName to solve
	 * 
	 * @param label to link to
	 * 
	 * @return the resolved RAW data
	 */
	public static String resolveAchievement(String achievmentName, String label){
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "hoverEvent:{"
						+ "action:show_achievement,"
						+ "value:\"" + achievmentName + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
}
