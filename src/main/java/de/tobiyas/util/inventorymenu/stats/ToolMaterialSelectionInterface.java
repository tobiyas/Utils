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

public class ToolMaterialSelectionInterface extends MaterialSelectionInterface {

	public ToolMaterialSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config,
			String key, JavaPlugin plugin) {
		super(player, parent, config, key, plugin);
	}

	@Override
	protected boolean filter(ItemStack item) {
		switch(item.getType()){
		
		case WOOD_AXE : 
		case STONE_AXE : 
		case GOLD_AXE : 
		case IRON_AXE : 
		case DIAMOND_AXE : 

		case WOOD_SWORD : 
		case STONE_SWORD : 
		case GOLD_SWORD : 
		case IRON_SWORD : 
		case DIAMOND_SWORD : 
			
		case WOOD_HOE : 
		case STONE_HOE : 
		case GOLD_HOE : 
		case IRON_HOE : 
		case DIAMOND_HOE : 
			
		case WOOD_SPADE : 
		case STONE_SPADE : 
		case GOLD_SPADE : 
		case IRON_SPADE : 
		case DIAMOND_SPADE : 
			
		case WOOD_PICKAXE : 
		case STONE_PICKAXE : 
		case GOLD_PICKAXE : 
		case IRON_PICKAXE : 
		case DIAMOND_PICKAXE : 
			
		case BOW :
		case FISHING_ROD :
		case SHEARS :
		case CARROT_STICK : return true;
		
		default: return false;
		}
	}
}
