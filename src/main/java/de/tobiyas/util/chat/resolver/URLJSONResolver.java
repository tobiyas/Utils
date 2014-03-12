package de.tobiyas.util.chat.resolver;

public class URLJSONResolver {

	
	/**
	 * Creates an Label for an URL
	 * 
	 * @param toParse to parse
	 * @param label to show
	 * 
	 * @return the parsed JSON string
	 */
	public static String resolveURL(String url, String label) {
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "hoverEvent:{"
						+ "action:open_url,"
						+ "value:\"" + url + "\""
					+"}"
				+ "}";
		
		return rawData;
	}

}
