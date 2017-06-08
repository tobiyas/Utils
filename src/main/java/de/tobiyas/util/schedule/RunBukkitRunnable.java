package de.tobiyas.util.schedule;

import org.bukkit.scheduler.BukkitRunnable;

public class RunBukkitRunnable extends BukkitRunnable{

	
	/**
	 * The runnable to run.
	 */
	private final Runnable run;
	
	
	public RunBukkitRunnable(Runnable run) {
		this.run = run;
	}
	
	
	@Override
	public void run() {
		run.run();
	}

}
