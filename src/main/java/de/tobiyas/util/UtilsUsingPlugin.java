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
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.Metrics;

import de.tobiyas.util.chat.commands.ClickCommandManager;
import de.tobiyas.util.debug.logger.DebugLogger;
import de.tobiyas.util.economy.MoneyManager;
import de.tobiyas.util.permissions.PermissionManager;
import de.tobiyas.util.schedule.DebugBukkitRunnable;
import de.tobiyas.util.vollotile.VollotileCodeManager;

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
	 * The ClickCommandManager to use.
	 */
	private ClickCommandManager clickCommandManager;
	
	
	/**
	 * Default constructor
	 */
	public UtilsUsingPlugin() {}
	
	
	
	
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

	
	@Override
	public final void onEnable(){
		//first init Logger.
		getDebugLogger().setAlsoToPlugin(true);
		
		//Now init the Vollotile Code manager.
		VollotileCodeManager.init(this);
		
		//Inits the first Tick.
		new DebugBukkitRunnable("StartTick"){
			@Override
			protected void runIntern() {
				firstTick();
				
				//Call Metrics, if wanted:
				if(useMetrics()){
					try{
						new Metrics(UtilsUsingPlugin.this).start();
					}catch(IOException exp) {}
				}
			};
		}.runTaskLater(this, 1);
		
		//Call the Enable Function. This has to be after scheduling the first tick.
		//This is because otherwise it is not cancelable.
		pluginEnable();
	}
	
	/**
	 * The Plugin is beening enabled.
	 */
	protected abstract void pluginEnable();
	
	
	/**
	 * This is called on the first tick!
	 */
	protected void firstTick(){}
	
	
	/**
	 * Registers the Events for the Plugin.
	 * 
	 * @param listener to register.
	 */
	public void registerEvents(Listener listener){
		if(listener == null) return;
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	
	/**
	 * Registers a Plugin Message Callback.
	 * 
	 * @param listener to register
	 * @param channel to register
	 */
	public void registerPluginMessage(PluginMessageListener listener, String channel){
		if(listener == null || channel == null || channel.isEmpty()) return;
		Bukkit.getMessenger().registerIncomingPluginChannel(this, channel, listener);
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
	 * Returns the {@link ClickCommandManager} for the plugin.
	 * <br>This is a lazy init.
	 * 
	 * @return the {@link ClickCommandManager}
	 */
	public ClickCommandManager getClickCommandManager(){
		if(clickCommandManager == null) clickCommandManager = new ClickCommandManager(this);
		return clickCommandManager;
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
	
	
	/**
	 * If Metrics should be used.
	 * <br>This is called First Tick!
	 * 
	 * @return true if should be used.
	 */
	protected boolean useMetrics(){
		return false;
	}
	
	
	
	/**
	 * Calls an event Sync or Async.
	 * 
	 * @param event to use
	 */
	public void callEvent(final Event event){
		boolean async = event.isAsynchronous();
		BukkitRunnable run = new DebugBukkitRunnable("UtilsCallEvent") {
			@Override
			public void runIntern() {
				Bukkit.getPluginManager().callEvent(event);
			}
		};
		
		
		if( async ) run.runTaskAsynchronously(this);
		else run.runTask(this);
	}
	
	
	//Making public to other Classes!
	@Override
	public void installDDL() {
		super.installDDL();
	}
	
}
