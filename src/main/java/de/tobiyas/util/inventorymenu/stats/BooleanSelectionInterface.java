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

import java.util.LinkedList;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public class BooleanSelectionInterface extends AbstractStatSelectionInterface {

	/**
	 * The Selector for the True value
	 */
	protected ItemStack trueSelector;
	
	/**
	 * The Selector for the false Value
	 */
	protected ItemStack falseSelector;
	
	/**
	 * The selector to indicate the current Value
	 */
	protected ItemStack currentValueIndicator;
	
	/**
	 * The current value as is.
	 */
	protected boolean currentValue = false;
		
	
	public BooleanSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config, String key, JavaPlugin plugin) {
		
		super(player, parent, "Select a Boolean (true, false)", config, key, plugin);
		
		trueSelector = generateItem(Material.WOOL, (short) 13, ChatColor.GREEN + "TRUE", 
				ChatColor.LIGHT_PURPLE + "This indicates the TRUE value.");

		falseSelector = generateItem(Material.WOOL, (short) 14, ChatColor.RED + "FALSE", 
				ChatColor.LIGHT_PURPLE + "This indicates the False value.");
		
		currentValueIndicator = generateItem(Material.WOOL, (short) 14, ChatColor.AQUA +
				"Current Value", new LinkedList<String>());
		
		selectionInventory.setItem(4, trueSelector);
		selectionInventory.setItem(5, falseSelector);
		
		controlInventory.setItem(4, currentValueIndicator);
	}


	@Override
	protected void onSelectionItemPressed(ItemStack item) {
		if(trueSelector.equals(item)){
			selectTrue();
			return;
		}
		
		if(falseSelector.equals(item)){
			selectFalse();
			return;
		}
	}
	
	
	/**
	 * Selects the true value if not selected
	 */
	private void selectTrue(){
		if(currentValue) return;
		currentValue = true;
		
		currentValueIndicator.setDurability((short) 13);

		controlInventory.setItem(4, null);
		controlInventory.setItem(4, currentValueIndicator);
	}
	
	/**
	 * Selects the true value if not selected
	 */
	private void selectFalse(){
		if(!currentValue) return;
		currentValue = false;
		
		currentValueIndicator.setDurability((short) 14);

		controlInventory.setItem(4, null);
		controlInventory.setItem(4, currentValueIndicator);
	}
	

	@Override
	protected void onControlItemPressed(ItemStack item) {
		if(currentValueIndicator.equals(item)){
			//Control Item needs no callbacks
			return;
		}
	}


	@Override
	protected Object unparseValue() {
		return currentValue;
	}

}
