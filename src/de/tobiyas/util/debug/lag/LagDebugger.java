package de.tobiyas.util.debug.lag;

import org.bukkit.plugin.java.JavaPlugin;

public class LagDebugger {
	
	private JavaPlugin plugin;
	private LagDebugger lagDebugger = null;
	
	public LagDebugger(JavaPlugin plugin){
		this.plugin = plugin;
		enable();
	}
	
	public void disable(){
		lagDebugger.disable();
		lagDebugger = null;
	}
	
	public void enable(){
		if(lagDebugger == null)
			lagDebugger = new LagDebugger(plugin);
		else{
			disable();
			enable();
		}
	}
	
}
