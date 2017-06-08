package de.tobiyas.util.chat;

import org.bukkit.ChatColor;

public class ChatColorFormatter {

	
	/**
	 * Formats a Text to be highlighted.
	 * The format is as following: '{0} this {1} text.' with Replacements: [Hello, is a]
	 * 
	 * @param format to use for converting.
	 * @param mainColor to use.
	 * @param highlightColor to use for highlighting replacements.
	 * @param replacements to replace the Stuff.
	 * @return the formattet Text.
	 */
	public static String format(String format, ChatColor mainColor, ChatColor highlightColor, String... replacements){
		for(int i = 0; i < replacements.length; i++){
			format = format.replace("{"+i+"}", highlightColor + replacements[i] + mainColor);
		}
		
		return mainColor + format;
	}
	
}
