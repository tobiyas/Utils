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
