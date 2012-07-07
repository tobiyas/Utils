package de.tobiyas.util.pluginreloader;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginReloader extends JavaPlugin {
	
	@Override
	public void onEnable(){
	}
	
	@Override
	public void onDisable(){
	}
	
	private void reloadPluginIntern(JavaPlugin plugin){
		plugin.getLogger().log(Level.INFO, "Reloading...");
		Bukkit.getPluginManager().disablePlugin(plugin);
		Bukkit.getPluginManager().enablePlugin(plugin);
		
	}
	
	public static void reloadPlugin(JavaPlugin plugin){
		PluginReloader reloaderPlugin = new PluginReloader();
		Bukkit.getPluginManager().enablePlugin(reloaderPlugin);
		
		reloaderPlugin.reloadPluginIntern(plugin);
		
		Bukkit.getPluginManager().disablePlugin(reloaderPlugin);
	}
}
