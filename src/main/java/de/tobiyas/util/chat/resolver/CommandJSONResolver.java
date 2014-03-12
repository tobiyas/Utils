package de.tobiyas.util.chat.resolver;

public class CommandJSONResolver {

	/**
	 * Resolves the Command to an Command to click.
	 * 
	 * @param command to click
	 * 
	 * @param label to click on
	 * 
	 * @return
	 */
	public static String resolveCommand(String command, String label){
		String rawData = ""
				+ "{"
					+ "text:\"" + label + "\","
					+ "clickEvent:{"
						+ "action:run_command,"
						+ "value:\"" + command + "\""
					+"}"
				+ "}";
		
		return rawData;
	}
}
