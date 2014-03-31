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
package de.tobiyas.util.inventorymenu.stats;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public abstract class AbstractStatSelectionInterface extends BasicSelectionInterface {

	/**
	 * The Key of the map to save to
	 */
	protected final String key;
	
	/**
	 * The Map to save into when 
	 */
	protected final Map<String, Object> config;
	
	
	public AbstractStatSelectionInterface(Player player,
			BasicSelectionInterface parent, String selectionInventoryName, 
			Map<String, Object> config, String key, JavaPlugin plugin) {
		
		super(player, parent, "Controls", selectionInventoryName, plugin);
		
		this.key = key;
		this.config = config;
	}


	@Override
	protected boolean onBackPressed() {
		return true;
	}

	@Override
	protected void onAcceptPressed() {
		config.put(key, unparseValue());
		closeAndReturnToParent();
	}
	
	
	/**
	 * Gets the Value returned into the Map
	 * @return
	 */
	protected abstract Object unparseValue();
	

}
