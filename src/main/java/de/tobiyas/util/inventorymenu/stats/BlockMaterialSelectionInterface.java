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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public class BlockMaterialSelectionInterface extends MaterialSelectionInterface {

	public BlockMaterialSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config,
			String key, JavaPlugin plugin) {
		super(player, parent, config, key, plugin);
	}

	@Override
	protected boolean filter(ItemStack item) {
		return item.getType().isBlock();
	}
	
}
