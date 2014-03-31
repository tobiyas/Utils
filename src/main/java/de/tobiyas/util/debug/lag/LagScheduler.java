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
package de.tobiyas.util.debug.lag;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LagScheduler implements Runnable{

	private int counter;
	private long lastTickTime;
	private JavaPlugin plugin;
	
	private int schedulerTask;
	
	public LagScheduler(JavaPlugin plugin){
		counter = 0;
		this.plugin = plugin;
		schedulerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 1, 1);
		lastTickTime = System.currentTimeMillis();
	}
	
	public void disable(){
		Bukkit.getScheduler().cancelTask(schedulerTask);
	}
	
	@Override
	public void run() {
		counter += 1;
		if(counter == 20){
			long currentTime = System.currentTimeMillis();
			long takenTime = (currentTime - lastTickTime) / 1000;
			
			if(takenTime > (20 * 1.1))
				plugin.getLogger().log(Level.WARNING, "Scheduler problems. Time taken for 1 sec: " + takenTime);
			
			lastTickTime = currentTime;
			counter = 0;
		}
		
	}

}
