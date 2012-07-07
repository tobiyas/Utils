package de.tobiyas.util.metrics;

import java.io.IOException;

import org.bukkit.plugin.Plugin;

public class SendMetrics {

	public static void sendMetrics(Plugin plugin, boolean debug){
		try {
		    Metrics metrics = new Metrics(plugin);
		    metrics.setDebug(debug);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}
	
	public static void sendMetrics(Plugin plugin){
		sendMetrics(plugin, false);
	}
}
