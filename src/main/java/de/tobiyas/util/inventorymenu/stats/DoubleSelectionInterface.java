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
import de.tobiyas.util.inventorymenu.elements.ItemScrollView;

public class DoubleSelectionInterface extends IntegerSelectionInterface {

	protected ItemScrollView indicator0_1;
	protected ItemScrollView indicator0_01;
	
	
	public DoubleSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config,
			String key, JavaPlugin plugin) {
		super(player, parent, "Select a Double", config, key, plugin);
		
	}
	
	
	@Override
	protected void generateIndicators() {
		super.generateIndicators();
		indicator0_1 = new ItemScrollView("0.1s", 6, selectionInventory);
		indicator0_01 = new ItemScrollView("0.01s", 7, selectionInventory);
	}




	@Override
	protected void totalRedraw() {
		super.totalRedraw();
		
		indicator0_1.redrawInventory();
		indicator0_01.redrawInventory();
	}




	@Override
	protected boolean checkAnyReaction(ItemStack item) {
		if(super.checkAnyReaction(item)) return true;
		if(indicator0_1.react(item)) return true;
		if(indicator0_01.react(item)) return true;
		
		return false;
	}

	
	@Override
	protected void rescanValue() {
		super.rescanValue();
		
		value += indicator0_1.getCurrentValue() * 0.1;
		value += indicator0_01.getCurrentValue() * 0.01;
		
	}


	@Override
	protected void setValue(double value) {
		super.setValue(value);
		
		int value_0_1 = getDigit(value, 0);
		int value_0_01 = getDigit(value, -1);
		
		indicator0_1.setCurrentValue(value_0_1);
		indicator0_01.setCurrentValue(value_0_01);		
	}
	
}
