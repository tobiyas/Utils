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
package de.tobiyas.util.inventorymenu.elements;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.tobiyas.util.vollotile.VollotileCode.MCVersion;
import de.tobiyas.util.vollotile.VollotileCodeManager;

public class ItemScrollView {

	/**
	 * The Item identifying the up button
	 */
	private ItemStack upButton;
	
	/**
	 * The Item identifying the indicator in the middle
	 */
	private ItemStack indicator;
	
	/**
	 * The Item identifying the Down button
	 */
	private ItemStack downButton;
	
	private String name;
	
	private int value;
	
	private int column = -1;
	
	private final Inventory inventory;
	
	
	public ItemScrollView(String name, int column, Inventory inventory) {
		this.name = name;
		this.column = column;
		this.inventory = inventory;
		
		upButton = generateUpItem();
		indicator = generateIndicatorItem();		
		downButton = generateDownItem(); 
	}
	
	
	/**
	 * If inventory heads are resolved.
	 * 
	 * @return true if has support.
	 */
	private boolean hasInventoryHeadSupport(){
		return VollotileCodeManager.getVollotileCode().getVersion().isVersionGreaterOrEqual(MCVersion.v1_8_R1);
	}
	
	
	/**
	 * Generates the Down-Item.
	 * 
	 * @return the down Item.
	 */
	private ItemStack generateDownItem(){
		if(hasInventoryHeadSupport()) return generateHead("MHF_ArrowDown", ChatColor.RED + "Scroll Down " + name, "Scrolls the List down.");
		
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = downButton.getItemMeta();
		List<String> lore = new LinkedList<String>();
		lore.add("LESS");
		meta.setLore(lore);
		meta.setDisplayName(ChatColor.RED + "LESS" + name);
		item.setItemMeta(meta);
		
		return item;
	}
	
	/**
	 * Generates a Head for the playername passed.
	 * 
	 * @param playerHeadName to generate
	 * @param displayname to set
	 * @param lore to set
	 * 
	 * @return the generated Head.
	 */
	private ItemStack generateHead(String playerHeadName, String displayname, String... lore) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM);
		head.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		
		meta.setOwner(playerHeadName);
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(displayname);
		
		head.setItemMeta(meta);
		return head;
	}


	private ItemStack generateIndicatorItem(){
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = indicator.getItemMeta();
		List<String> lore = new LinkedList<String>();
		lore.add(ChatColor.LIGHT_PURPLE + "VALUE" + name);
		meta.setLore(lore);
		meta.setDisplayName(name + " : " + value);
		item.setItemMeta(meta);
		
		return item;
	}
	
	
	private ItemStack generateUpItem(){
		if(hasInventoryHeadSupport()) return generateHead("MHF_ArrowUp", ChatColor.RED + "Scroll Up " + name, "Scrolls the List down.");
		
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = upButton.getItemMeta();
		List<String> lore = new LinkedList<String>();
		lore.add("MORE");
		meta.setLore(lore);
		meta.setDisplayName(ChatColor.GREEN + "More " + name);
		item.setItemMeta(meta);
		//MHF_ArrowUp
		return item;
	}
	
	
	
	/**
	 * sets the buttons needed
	 * 
	 * @param inventory
	 * @param row
	 */
	public void redrawInventory(){
		inventory.setItem(column, null);
		inventory.setItem(9 + column, null);
		inventory.setItem(18 + column, null);
		
		inventory.setItem(column, upButton);
		inventory.setItem(18 + column, downButton);

		if(value != 0){
			ItemMeta newItemMeta = indicator.getItemMeta();
			newItemMeta.setDisplayName(name + " : " + value); 
			indicator.setItemMeta(newItemMeta);
			
			indicator.setAmount(value);
			inventory.setItem(9 + column, indicator);
		}
	}
	
	
	/**
	 * Returns true if click was identified
	 * 
	 * @return true if triggered and worked.
	 */
	public boolean react(ItemStack item){
		if(upButton.equals(item)){
			addOne();
			return true;
		}
		
		if(indicator.equals(item)){
			return true;
		}
		
		if(downButton.equals(item)){
			removeOne();
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Adds one to the current count.
	 * If over 9, 0 is chosen.
	 */
	private void addOne(){
		value ++;
		if(value > 9) value = 0;
		
		redrawInventory();
	}
	
	/**
	 * Removes one from the current count.
	 * If under 0, 9 is chosen.
	 */
	private void removeOne(){
		value --;
		if(value < 0) value = 9;
		
		redrawInventory();
	}

	
	/**
	 * Get the value this represents at the moment
	 * 
	 * @return
	 */
	public int getCurrentValue(){
		return value;
	}


	/**
	 * 
	 * 
	 * @param newValue
	 */
	public void setCurrentValue(int newValue) {
		this.value = newValue;
		
		redrawInventory();
	}
}
