package de.tobiyas.util.debug.logger;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;

import de.tobiyas.util.UtilsUsingPlugin;
import de.tobiyas.util.debug.erroruploader.ErrorUploader;

public class DebugLogger{
	
	private UtilsUsingPlugin plugin;
	private Logger debugLogger;
	private Logger errorLogger;
	
	private ErrorUploader errorUploader;
	
	private FileHandler debugFileHandler;
	private FileHandler errorFileHandler;
	
	private File debugSaveFile;
	private File errorSaveFile;
	
	private boolean enabled;
	private boolean enableUploads;
	private boolean alsoOutputToDefaultLogger;
	
	/**
	 * The Logger for writing to the somewhere
	 * 
	 * @param plugin
	 */
	public DebugLogger(UtilsUsingPlugin plugin){
		this.plugin = plugin;
		this.alsoOutputToDefaultLogger = false;
		this.enabled = true;
		this.enableUploads = false;
		
		createStructur();
		initLoggers();
		
		this.errorUploader = new ErrorUploader(plugin, errorLogger);
	}
	
	private void createStructur(){
		String path = plugin.getDataFolder() + File.separator + "Debug" + File.separator;
		File pathFile = new File(path);
		
		if(!pathFile.exists())
			pathFile.mkdirs();
		
		String debugFileName = "debug.log";
		debugSaveFile = new File(pathFile, debugFileName);

		DateFormat df = new SimpleDateFormat("MM_dd_yyyy");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		
		File backupDir = new File(pathFile, "Backup" + File.separator + "Backup - " + reportDate);
		if(!backupDir.exists()){
			backupDir.mkdirs();
		}
		
		if(debugSaveFile.exists()){
			DateFormat df2 = new SimpleDateFormat("HH_mm_ss");
			String fileName = df2.format(today);
			File backupFile = new File(backupDir, fileName + "_debug.log");
			debugSaveFile.renameTo(backupFile);
			debugSaveFile.delete();
		}
		
		if(!debugSaveFile.exists()){
			try {
				debugSaveFile.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().log(Level.WARNING, ChatColor.YELLOW + "[" + plugin.getName() + "] Debug File could not be created!");
			}
		}
		
		
		//Error logger
		path = plugin.getDataFolder() + File.separator + "Debug" + File.separator;
		pathFile = new File(path);
		
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		
		
		
		String errorFileName = "error.log";
		errorSaveFile = new File(pathFile, errorFileName);
		
		if(errorSaveFile.exists()){
			DateFormat df2 = new SimpleDateFormat("HH_mm_ss");
			String fileName = df2.format(today);
			File backupFile = new File(backupDir, fileName + "error.log");
			errorSaveFile.renameTo(backupFile);
			errorSaveFile.delete();
		}
		
		if(!errorSaveFile.exists()){
			try {
				errorSaveFile.createNewFile();
			} catch (IOException e) {
				plugin.getLogger().log(Level.WARNING, ChatColor.YELLOW + "[" + plugin.getName() + "] Debug File could not be created!");
			}
		}
		
		
	}
	
	private void initLoggers(){
		initDebugLogger();
		initErrorLogger();
	}
	
	private void initDebugLogger(){
		debugLogger = Logger.getLogger("minecraft." + plugin.getName() + ".debug");
		debugLogger.setUseParentHandlers(alsoOutputToDefaultLogger);
		
		try {
			// This block configure the logger with handler and formatter
			debugFileHandler = new FileHandler(debugSaveFile.toString(), true);
			debugLogger.addHandler(debugFileHandler);
			debugLogger.setLevel(Level.ALL);
			debugFileHandler.setFormatter(new ConsoleLogFormatter(true));

			// the following statement is the initiation of the logger displaying version + plugin 
			log("Debugger for Plugin:" + plugin.getName() + " Version: " + plugin.getDescription().getVersion() +   
						" Started! System-Time: " + Calendar.getInstance().getTime().toString());

		 } catch (SecurityException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	private void initErrorLogger(){
		errorLogger = Logger.getLogger("minecraft." + plugin.getName() + ".debug.error");
		errorLogger.setUseParentHandlers(false);
		
		try {
			// This block configure the logger with handler and formatter
			errorFileHandler = new FileHandler(errorSaveFile.toString(), true);
			errorLogger.addHandler(errorFileHandler);
			errorLogger.setLevel(Level.ALL);
			errorFileHandler.setFormatter(new ConsoleLogFormatter(true));

			// the following statement is the initiation of the logger displaying version + plugin 
			errorLogger.log(Level.INFO, "Debugger for Plugin:" + plugin.getName() + " Version: " + plugin.getDescription().getVersion() +   
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
		String pluginPrefix = "[" + plugin.getDescription().getName() + "]";
		debugLogger.log(Level.INFO, pluginPrefix + msg);
	}
	
	/**
	 * Loggs a Message to the logger
	 * Level = WARNING
	 
	 * @param msg
	 */
	public void logWarning(String msg){
		if(enabled){
			String pluginPrefix = "[" + plugin.getDescription().getName() + "]";
			debugLogger.log(Level.WARNING, pluginPrefix + msg);
		}
	}
	
	/**
	 * Loggs a Message to the logger
	 * Level = SEVERE
	 
	 * @param msg
	 */
	public void logError(String msg){
		logError(msg, true);
	}
	
	
	public void logDebug(String message){
		if(enabled){
			String pluginPrefix = "[" + plugin.getDescription().getName() + "]";
			debugLogger.log(Level.INFO, pluginPrefix + message);
		}
	}
	
	private void logError(String msg, boolean logToErrorlogger){
		String pluginPrefix = "[" + plugin.getDescription().getName() + "]";
		if(enabled){
			debugLogger.log(Level.SEVERE, pluginPrefix + msg);
			
		}
		
		if(logToErrorlogger){
			errorLogger.log(Level.SEVERE, pluginPrefix + msg);
		}
	}
	
	public void setAlsoToPlugin(boolean value){
		this.alsoOutputToDefaultLogger = value;
		
		if(debugLogger == null) return;
		debugLogger.setUseParentHandlers(value);
	}

	public void shutDown() {
		debugLogger.removeHandler(debugFileHandler);
		debugFileHandler.close();
		
		errorLogger.removeHandler(errorFileHandler);
		errorFileHandler.close();
	}
	
	/**
	 * Restarts the Debug logger.
	 */
	public void restart(){
		createStructur();
		initLoggers();
	}

	public void disable() {
		this.enabled = false;
	}
	
	public void enable(){
		this.enabled = true;
	}
	
	public void enableUploads(boolean enable){
		this.enableUploads = enable;
	}

	public void logStackTrace(Exception error) {
		logError("Stacktrace in error.log!", false);
		errorLogger.log(Level.SEVERE, "Error: " + error.getClass().getName() + " message: '" + error.getLocalizedMessage() + "'");
		for(StackTraceElement element : error.getStackTrace()){
			errorLogger.log(Level.SEVERE, element.toString());
		}
		
		if(enableUploads){
			errorUploader.uploadStacktrace(error);
		}
	}
}
