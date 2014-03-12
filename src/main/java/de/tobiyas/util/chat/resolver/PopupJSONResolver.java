package de.tobiyas.util.chat.resolver;

public class PopupJSONResolver {

	
	/**
	 * Resolves the Text to an Popup.
	 * 
	 * @param message to show
	 * 
	 * @param label to hover on
	 * 
	 * @return
	 */
	public static String resolve(String message, String label){
		if(label == null || "".equals(label)) label = message;
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "hoverEvent:{"
						+ "action:show_text,"
						+ "value:\"" + message + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
}
