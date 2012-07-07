package de.tobiyas.util.debug.erroruploader;

import org.bukkit.plugin.Plugin;

import de.tobiyas.util.debug.logger.DebugLogger;

public class ErrorUploader {

	private Plugin plugin;
	private DebugLogger logger;
	
	public ErrorUploader(Plugin plugin, DebugLogger logger){
		this.plugin = plugin;
		this.logger = logger;
	}
	
	public void uploadStacktrace(Exception error){
		String pluginVersion = plugin.getDescription().getVersion();
		String pluginName = plugin.getName();
		
		BuildPackage buildPackage = new BuildPackage(pluginVersion, pluginName, error, this);
		buildPackage.start();
	}
	
	public void writeback(String writebackMessage){
		logger.writebackForUploader(writebackMessage);
	}
}
