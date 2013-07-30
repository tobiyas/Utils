package de.tobiyas.util.metrics;

import java.io.IOException;

import org.bukkit.plugin.Plugin;
import org.mcstats.Metrics;

public class SendMetrics {

	public static void sendMetrics(Plugin plugin){
		try {
		    Metrics metrics = new Metrics(plugin);
		    metrics.start();
		} catch (IOException e) {
			//error on uploading.
		}
	}
	
	public static void sendMetrics(Plugin plugin, boolean useless){
		//to keep compatibility.
		sendMetrics(plugin);
	}
}
