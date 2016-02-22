package de.tobiyas.util.schedule;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.tobiyas.Statistics;

public abstract class DebugBukkitRunnable extends BukkitRunnable {

	/**
	 * The name to use for loggin.
	 */
	private final String name;
	
	/**
	 * The plugin to log for.
	 */
	private Plugin plugin;
	
	/**
	 * If sync or sync.
	 */
	private boolean sync = true;
	
	
	public DebugBukkitRunnable(String name) {
		this.name = name == null ? "UNKNOWN" : name;
	}
	
	public DebugBukkitRunnable() {
		this.name = getClass().getSimpleName();
	}
	
	
	@Override
	public final void run() {
		long startMS = System.currentTimeMillis();
		long startNano = System.nanoTime();
		
		try{
			runIntern();
		}catch(Throwable exp){
			String name = this.name == null ? "unknown" : this.name;
			String pluginName = this.plugin == null ? "unknown-plugin" : plugin.getName();
			
			String message = String.format("Error in Runnable '%s' in plugin '%s'", name, pluginName);
			plugin.getLogger().log(Level.WARNING, message, exp);
		}
		
		startMS = System.currentTimeMillis() - startMS;
		startNano = System.nanoTime() - startNano;
		if(plugin != null) Statistics.log(plugin, sync, name, startMS, startNano);
	}
	
	
	/**
	 * Runs internally.
	 */
	protected abstract void runIntern();


	@Override
	public synchronized BukkitTask runTask(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
		return super.runTask(plugin);
	}


	@Override
	public synchronized BukkitTask runTaskAsynchronously(Plugin plugin)
			throws IllegalArgumentException, IllegalStateException {
		
		this.plugin = plugin;
		this.sync = false;
		return super.runTaskAsynchronously(plugin);
	}


	@Override
	public synchronized BukkitTask runTaskLater(Plugin plugin, long delay)
			throws IllegalArgumentException, IllegalStateException {
		
		this.plugin = plugin;
		this.sync = true;
		return super.runTaskLater(plugin, delay);
	}


	@Override
	public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay)
			throws IllegalArgumentException, IllegalStateException {
		
		this.plugin = plugin;
		this.sync = false;
		return super.runTaskLaterAsynchronously(plugin, delay);
	}


	@Override
	public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period)
			throws IllegalArgumentException, IllegalStateException {
		
		this.plugin = plugin;
		this.sync = true;
		return super.runTaskTimer(plugin, delay, period);
	}


	@Override
	public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period)
			throws IllegalArgumentException, IllegalStateException {
		
		this.plugin = plugin;
		this.sync = false;
		return super.runTaskTimerAsynchronously(plugin, delay, period);
	}
	
	
	

}
