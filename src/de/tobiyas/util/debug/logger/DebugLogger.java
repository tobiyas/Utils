package de.tobiyas.util.debug.logger;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DebugLogger {
	
	private JavaPlugin plugin;
	private Logger debugLogger;
	
	private FileHandler fileHandler;
	private File saveFile;
	
	private boolean enabled;
	private boolean alsoOutputToDefaultLogger;
	
	/**
	 * @param plugin
	 */
	public DebugLogger(JavaPlugin plugin){
		this.plugin = plugin;
		this.alsoOutputToDefaultLogger = false;
		this.enabled = true;
		createStructur();
	
		initLogger();
	}
	
	private void createStructur(){
		String path = plugin.getDataFolder() + File.separator + "Debug" + File.separator;
		File pathFile = new File(path);
		
		if(!pathFile.exists())
			pathFile.mkdirs();
		
		String debugFileName = "debug.log";
		saveFile = new File(pathFile, debugFileName);
		
		if(!saveFile.exists())
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().log(Level.WARNING, ChatColor.YELLOW + "[" + plugin.getName() + "] Debug File could not be created!");
			}
	}
	
	private void initLogger(){
		debugLogger = Logger.getLogger("minecraft." + plugin.getName());
		debugLogger.setUseParentHandlers(alsoOutputToDefaultLogger);
		
		try {
			// This block configure the logger with handler and formatter
			fileHandler = new FileHandler(saveFile.toString(), true);
			debugLogger.addHandler(fileHandler);
			debugLogger.setLevel(Level.ALL);
			fileHandler.setFormatter(new ConsoleLogFormatter(true));

			// the following statement is used to log any messages   
			log("Debugger for Plugin" + plugin.getName() + 
						" Started! System-Time: " + Calendar.getInstance().getTime().toString());

		 } catch (SecurityException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	/**
	 * Loggs a Message to the logger
	 * Level = INFO
	 * 
	 * @param msg
	 */
	public void log(String msg){
		debugLogger.log(Level.INFO, msg);
	}
	
	/**
	 * Loggs a Message to the logger
	 * Level = WARNING
	 
	 * @param msg
	 */
	public void logWarning(String msg){
		if(enabled)
			debugLogger.log(Level.WARNING, msg);
	}
	
	/**
	 * Loggs a Message to the logger
	 * Level = SEVERE
	 
	 * @param msg
	 */
	public void logError(String msg){
		if(enabled)
			debugLogger.log(Level.SEVERE, msg);
	}
	
	public void setAlsoToPlugin(boolean value){
		this.alsoOutputToDefaultLogger = value;
		
		if(debugLogger == null) return;
		debugLogger.setUseParentHandlers(value);
	}

	public void shutDown() {
		debugLogger.removeHandler(fileHandler);
		fileHandler.close();
	}

	public void disable() {
		this.enabled = false;
	}
	
	public void enable(){
		this.enabled = true;
	}
}
