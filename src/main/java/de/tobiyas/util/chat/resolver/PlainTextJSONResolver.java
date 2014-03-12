package de.tobiyas.util.chat.resolver;

public class PlainTextJSONResolver {

	/**
	 * Returns the Raw JSON String from the Plain Text.
	 * 
	 * @param plainText to translate
	 * 
	 * @return the JSOn String
	 */
	public static String getRawFromPlainText(String plainText){
		plainText = plainText.replace("\\", "\\\\");
		plainText = plainText.replace("\"", "\\\"");
		
		return "{text:\"" + plainText + "\"}";
	}
}
