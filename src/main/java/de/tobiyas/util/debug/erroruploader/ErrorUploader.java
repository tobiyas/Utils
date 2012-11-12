package de.tobiyas.util.debug.erroruploader;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

public class ErrorUploader {

	private Plugin plugin;
	private Logger logger;
	
	public ErrorUploader(Plugin plugin, Logger errorLogger){
		this.plugin = plugin;
		this.logger = errorLogger;
	}
	
	public void uploadStacktrace(Exception error){
		String pluginVersion = plugin.getDescription().getVersion();
		String pluginName = plugin.getName();
		
		BuildPackage buildPackage = new BuildPackage(pluginVersion, pluginName, error, logger);
		buildPackage.start();
	}
}
