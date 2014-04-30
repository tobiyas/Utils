/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tobiyas.util;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import de.tobiyas.util.debug.logger.DebugLogger;
import de.tobiyas.util.economy.MoneyManager;
import de.tobiyas.util.permissions.PermissionManager;

public abstract class UtilsUsingPlugin extends JavaPlugin {

	/**
	 * The Permission Manager of the Plugin
	 * <br>Private to have easy Lazy init.
	 */
	private PermissionManager permManager;
	
	/**
	 * The debug logger for the Plugin
	 * 
	 */
	private DebugLogger debugLogger;
	
	
	/**
	 * Returns the Money manager of the Plugin.
	 * <br>Private to have easy Lazy init.
	 */
	private MoneyManager moneyManager;
	
	
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
	 * Returns the {@link MoneyManager} for the Plugin.
	 * 
	 * @return the {@link MoneyManager}.
	 */
	public MoneyManager getMoneyManager(){
		if(moneyManager == null){
			moneyManager = new MoneyManager(this);
		}
		
		return moneyManager;
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
	 * Logs a Message to the Error Channel.
	 * 
	 * @param message to log
	 */
	public void logError(String message){
		getDebugLogger().logError(message);
	}
	
	
	/**
	 * Logs a Message to the Debug Channel
	 * 
	 * @param message to log.
	 */
	public void logDebug(String message){
		getDebugLogger().logDebug(message);
	}

	
	
	/**
	 * Logs an Exception with a message to the error log.
	 * 
	 * @param message to post
	 * @param exp to save
	 */
	public void logStackTrace(String message, Throwable exp){
		if(message == null || "".equals(message)){
			message = exp.getLocalizedMessage();
		}
		
		logError(message);
		getDebugLogger().logStackTrace(exp);
	}
}
