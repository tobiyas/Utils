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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;
import de.tobiyas.util.inventorymenu.elements.ScrollableItems;

public class ItemEnchantmentSelectionInterface extends
		AbstractStatSelectionInterface {

	/**
	 * A Scroll list containing all Enchantment.
	 */
	private final ScrollableItems enchantmentScrollView;
	
	/**
	 * The map containing the item / enchantment relations.
	 */
	private final Map<ItemStack,Enchantment> enchantmentItemMap = new HashMap<ItemStack, Enchantment>();
	
	
	/**
	 * The selected Enchantment
	 */
	private ItemStack enchantmentItem;
	private Enchantment enchantment;
	
	/**
	 * The Current key for underlying configs.
	 */
	private String currentKey = "";
	
	
	/**
	 * The Item describing the enchantment level.
	 */
	private ItemStack levelItem;
	private int level = 1;
	
	
	public ItemEnchantmentSelectionInterface(Player player,
			BasicSelectionInterface parent,	Map<String, Object> config, String key, JavaPlugin plugin) {
		
		super(player, parent, "Add Enchant", config, key, plugin);

		enchantmentScrollView = new ScrollableItems(selectionInventory, 0, 8, 0);
		
		redraw();
	}
	
	private void redraw(){
		enchantmentScrollView.clear();
		enchantmentItemMap.clear();
		
		for(Enchantment enchantment : Enchantment.values()){
			ItemStack item = generateItem(Material.PAPER, enchantment.getName(), new LinkedList<String>());
			item.addUnsafeEnchantment(enchantment, 1);
			
			enchantmentScrollView.addItem(item);
			enchantmentItemMap.put(item, enchantment);
		}
		
		enchantmentItem = generateItem(Material.PAPER, "The selected Enchantment", new LinkedList<String>());
		if(enchantment != null){
			enchantmentItem.addUnsafeEnchantment(enchantment, level);
		}
		
		levelItem = generateItem(Material.ARROW, "Level: " + level, "The Level of the Enchant");
		
		
		controlInventory.setItem(4, enchantmentItem);
		controlInventory.setItem(3, levelItem);
	}
	

	@Override
	protected Object unparseValue() {
		EnchantmentContainer container = new EnchantmentContainer();
		container.enchantment = enchantment;
		container.level = level;
		
		return container;
	}

	@Override
	protected void onSelectionItemPressed(ItemStack item) {
		if(enchantmentScrollView.checkScrollButtons(item)) return;
		
		for(Entry<ItemStack, Enchantment> entry : enchantmentItemMap.entrySet()){
			ItemStack entryItem = entry.getKey();
			Enchantment enchantment = entry.getValue();
			
			if(entryItem.equals(item)){
				this.enchantment = enchantment;
				redraw();
				return;
			}
		}
	}

	
	private final String LEVEL_KEY = "level";
	
	@Override
	protected void onControlItemPressed(ItemStack item) {
		if(item.equals(levelItem)){
			currentKey = LEVEL_KEY;
			config.put(currentKey, level);
			openNewView(new IntegerSelectionInterface(player, this, config, currentKey, plugin));
			return;
		}

	}

	
	
	
	@Override
	protected void notifyReopened() {
		super.notifyReopened();
		
		if(currentKey.equals(LEVEL_KEY) && config.containsKey(currentKey)){
			int newLevel = (Integer) config.get(currentKey);
			if(level > 0){
				this.level = newLevel;
				redraw();
			}
			
			return;
		}
	}




	/**
	 * A container containing an Enchantment and a Level.
	 * 
	 * @author Tobiyas
	 */
	public class EnchantmentContainer{
		public Enchantment enchantment;
		public int level;
	}
}
