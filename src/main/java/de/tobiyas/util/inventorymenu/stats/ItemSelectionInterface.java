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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;
import de.tobiyas.util.inventorymenu.stats.ItemEnchantmentSelectionInterface.EnchantmentContainer;

public class ItemSelectionInterface extends AbstractStatSelectionInterface {

	//Consts for Keys
	private final String itemMaterialKey = "itemMaterial";
	private final String itemNameKey = "itemName";
	private final String itemDamagevalueKey = "itemDamagevalue";
	private final String itemAddEnchantmentKey = "itemEnchantment";
	private final String itemPlayerHeadKey= "itemPlayerHead";
	
	
	/**
	 * The map of enchants.
	 */
	private Map<Enchantment,Integer> enchantments = new HashMap<Enchantment, Integer>();
	private ItemStack addEnchantmentItem;
	
	
	/**
	 * The Item to change the Material.
	 */
	private ItemStack materialSelectionItem;
	private Material mat;
	
	
	/**
	 * The ItemName of the Item
	 */
	private ItemStack itemNameItem;
	private String itemName = "";
	
	/**
	 * The Damage Value of the Item.
	 */
	private ItemStack damageValueItem;
	private short damageValue = 0;
	
	/**
	 * the name of a playerhead when present.
	 */
	private ItemStack playerHeadItem;
	private String playerHeadName;
	
	/**
	 * The Current key for new Values.
	 */
	private String currentKey = "";
	
	/**
	 * The Indicator for the current Item.
	 */
	private ItemStack indicatorItem;
	
	/**
	 * The Type to open the SubMenu.
	 */
	private final MaterialType matType;
	
	
	public ItemSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config, 
			String key, JavaPlugin plugin, MaterialType matType) {
		
		super(player, parent, "Build your Item", config, key, plugin);

		this.matType = matType;
		mat = Material.WOOL;
		damageValue = 0;
		itemName = "";
		playerHeadName = "";
		
		if(config.containsKey(key)){
			ItemStack item = (ItemStack) config.get(key);
			
			if(item != null){
				mat = item.getType();
				damageValue = item.getDurability();
				itemName = item.getItemMeta().getDisplayName();
				enchantments = new HashMap<Enchantment,Integer>(item.getEnchantments());
				if(item.getItemMeta() instanceof SkullMeta){
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					if(meta.hasOwner()){
						playerHeadName = meta.getOwner();
					}
				}
			}
			
		}
		
		redraw();
	}
	
	
	protected void redraw(){
		materialSelectionItem = generateItem(mat, "Select Material", "Changes the Material.");
		selectionInventory.setItem(3, materialSelectionItem);
		
		itemNameItem = generateItem(Material.ANVIL, ChatColor.LIGHT_PURPLE + "Select ItemName", "Changes the ItemName.");
		selectionInventory.setItem(4, itemNameItem);
		
		damageValueItem = generateItem(Material.APPLE, ChatColor.AQUA + "Damage Value", new LinkedList<String>());
		selectionInventory.setItem(5, damageValueItem);
		
		addEnchantmentItem = generateItem(Material.PAPER, "Add Enchantment", new LinkedList<String>());
		selectionInventory.setItem(6, addEnchantmentItem);
		
		indicatorItem = generateItem(mat, damageValue, itemName, new LinkedList<String>());
		indicatorItem.addUnsafeEnchantments(enchantments);
		controlInventory.setItem(4, indicatorItem);
		
		playerHeadItem = generateItem(Material.SKULL_ITEM, "PlayerHead", "Head of: " + playerHeadName);
		selectionInventory.setItem(7, "".equals(playerHeadName) ? new ItemStack(Material.AIR) : playerHeadItem);
	}
	

	@Override
	protected Object unparseValue() {
		ItemStack item = new ItemStack(mat);
		item.addUnsafeEnchantments(enchantments);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(itemName);
		item.setItemMeta(meta);
		item.setDurability(damageValue);
		
		return item;
	}

	@Override
	protected void onSelectionItemPressed(ItemStack item) {
		if(item.equals(materialSelectionItem)){
			currentKey = itemMaterialKey;
			config.put(currentKey, mat);
			
			MaterialSelectionInterface toOpen = null;
			switch (matType) {
			case ALL:
				toOpen = new MaterialSelectionInterface(player, this, config, currentKey, plugin);
				break;
				
			case ARMOR:
				toOpen = new ArmorMaterialSelectionInterface(player, this, config, currentKey, plugin);
				break;
				
			case BLOCK:
				toOpen = new BlockMaterialSelectionInterface(player, this, config, currentKey, plugin);
				break;
				
			case TOOLS:
				toOpen = new ToolMaterialSelectionInterface(player, this, config, currentKey, plugin);
				break;
				
			case WEAPONS:
				toOpen = new WeaponMaterialSelectionInterface(player, this, config, currentKey, plugin);
				break;
				
			default:
				toOpen = new MaterialSelectionInterface(player, this, config, currentKey, plugin);
				break;
			}
			
			openNewView(toOpen);
			return;
		}

		if(item.equals(itemNameItem)){
			currentKey = itemNameKey;
			config.put(currentKey, itemName);
			openNewView(new StringSelectionInterface(player, this, config, currentKey, plugin));
			return;
		}
		
		if(item.equals(damageValueItem)){
			currentKey = itemDamagevalueKey;
			config.put(currentKey, (int)damageValue);
			openNewView(new IntegerSelectionInterface(player, this, config, currentKey, plugin));
			return;
		}
		
		if(item.equals(playerHeadItem)){
			currentKey = itemPlayerHeadKey;
			config.put(currentKey, playerHeadName);
			openNewView(new StringSelectionInterface(player, this, config, currentKey, plugin));
		}
		
		if(item.equals(addEnchantmentItem)){
			currentKey = itemAddEnchantmentKey;
			openNewView(new ItemEnchantmentSelectionInterface(player, this, config, currentKey, plugin));
		}
		
	}

	@Override
	protected void onControlItemPressed(ItemStack item) {
		if(item.equals(indicatorItem)){
			//no callback needed.
			return;
		}
	}


	@Override
	protected void notifyReopened() {
		super.notifyReopened();
		
		Object newValue = config.get(currentKey);
		if(newValue == null) return;
		
		if(currentKey.equalsIgnoreCase(itemMaterialKey)){
			mat = (Material) newValue;
			redraw();
			return;
		}
		
		if(currentKey.equalsIgnoreCase(itemNameKey)){
			itemName = ChatColor.translateAlternateColorCodes('&', ((String) newValue));
			redraw();
			return;
		}
		
		if(currentKey.equalsIgnoreCase(itemDamagevalueKey)){
			int newDamageValue = (Integer) newValue;
			damageValue = (short) newDamageValue;
			redraw();
			return;
		}
		
		if(currentKey.equalsIgnoreCase(itemAddEnchantmentKey)){
			EnchantmentContainer container = (EnchantmentContainer) newValue;
			
			if(container != null){
				enchantments.put(container.enchantment, container.level);
				redraw();
			}
			return;
		}
		
		if(currentKey.equalsIgnoreCase(itemPlayerHeadKey)){
			String newPlayerHeadName = (String) newValue;
			playerHeadName = newPlayerHeadName;
			redraw();
		}
	}	
}
