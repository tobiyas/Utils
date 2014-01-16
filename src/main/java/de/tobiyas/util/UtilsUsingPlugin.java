package de.tobiyas.util;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import de.tobiyas.util.debug.logger.DebugLogger;
import de.tobiyas.util.permissions.PermissionManager;

public abstract class UtilsUsingPlugin extends JavaPlugin {

	/**
	 * The Permission Manager of the Plugin
	 * <br>Private to have easy Lazy init.
	 */
	private PermissionManager permManager;
	
	/**
	 * The debug logger for the Plugin
	 * <br>Private to have easy Lazy init.
	 */
	private DebugLogger debugLogger;
	
	
	
	/**
	 * Default constructor
	 */
	public UtilsUsingPlugin() {
	}
	
	
	
	
	/**
	 * This constructor is ONLY for Testing purpose.
	 * 
	 * @param loader
	 * @param description
	 * @param dataFolder
	 * @param file
	 */
	@SuppressWarnings("deprecation")
	protected UtilsUsingPlugin(Server server, JavaPluginLoader loader,
			PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, server, description, dataFolder, file);
		
	}





	/**
	 * Gets the Permissionmanager of the Plugin.
	 * 
	 * @return the Permissionmanamger of the Plugin.
	 */
	public PermissionManager getPermissionManager(){
		if(permManager == null){
			permManager = new PermissionManager(this);
		}
		
		return permManager;
	}

	
	/**
	 * Returns the DebugLogger for the Plugin.
	 * 
	 * @return the Debug Logger.
	 */
	public DebugLogger getDebugLogger(){
		if(debugLogger == null){
			debugLogger = new DebugLogger(this);
		}
		
		return debugLogger;
	}
	
	
	/**
	 * Logs a Message to the Info channel
	 * 
	 * @param message to log
	 */
	public void log(String message){
		getDebugLogger().log(message);
	}
	
	/**
	 * Logs a Message to the Warning Channel.
	 * 
	 * @param message to log
	 */
	public void logWarning(String message){
		getDebugLogger().logWarning(message);
	}
	
	
	/**
	 * Logs an Exception with a message to the error log.
	 * 
	 * @param message to post
	 * @param exp to save
	 */
	public void logStackTrace(String message, Exception exp){
		if(message == null || "".equals(message)){
			message = exp.getLocalizedMessage();
		}
		
		getDebugLogger().logError(message);
		getDebugLogger().logStackTrace(exp);
	}
}
