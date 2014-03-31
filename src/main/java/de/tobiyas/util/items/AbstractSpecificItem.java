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
package de.tobiyas.util.items;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public abstract class AbstractSpecificItem implements Listener {

	protected Material itemWanted;
	protected String itemTagWanted;
	protected String itemNameWanted;
	
	protected final JavaPlugin plugin;
	
	/**
	 * If the item should be consumed afterwards.
	 */
	protected boolean consume = false;
	
	/**
	 * The amount to consume
	 */
	protected int consumeAmount = 1;
	
	protected boolean cancleCrafting = true;
	protected boolean cancleDropping = false;
	protected boolean cancleBrewing = false;
	
	
	public AbstractSpecificItem(JavaPlugin plugin) {
		itemWanted = getItemWanted();
		itemTagWanted = getTagWanted();
		itemNameWanted = getNameWanted();
		
		this.plugin = plugin;
	}
	
	
	/**
	 * Registers this Item as Listener To Bukkit.
	 * 
	 * @return this.
	 */
	public AbstractSpecificItem register(){
		Bukkit.getPluginManager().registerEvents(this, plugin);
		return this;
	}
	
	
	/**
	 * Returns the Material the Item is made of.
	 * 
	 * @return the material of the item
	 */
	protected abstract Material getItemWanted();
	
	/**
	 * Returns the Tag of the Item.
	 * 
	 * @return the tag of the item
	 */
	protected abstract String getTagWanted();
	
	/**
	 * Returns the Name the Item should have
	 * 
	 * @return the Name of the item
	 */
	protected abstract String getNameWanted();
	
	
	@EventHandler
	public void cancleItemCraft(CraftItemEvent event){
		if(!cancleCrafting) return;
		
		for(ItemStack item : event.getInventory().getContents()){
			if(isWantedItem(item)){
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void removeDBItemOnDeath(PlayerDeathEvent event){
		Iterator<ItemStack> it = event.getDrops().iterator();

		while(it.hasNext()){
			if(isWantedItem(it.next())){
				it.remove();
			}
		}
	}
	
	@EventHandler
	public void cancleBrewing(BrewEvent event){
		for(ItemStack item : event.getContents().getContents()){
			if(isWantedItem(item)){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void destroyOnDrop(PlayerDropItemEvent event){
		if(isWantedItem(event.getItemDrop().getItemStack())){
			event.getItemDrop().remove();
		}
	}
	
	/**
	 * Checks if the Item passed is the DB Item
	 * 
	 * @param item passed
	 * 
	 * @return true if it is this item, false otherwise.
	 */
	private boolean isWantedItem(ItemStack item){
		if(item == null || item.getType().equals(Material.AIR)) return false;
		
		if(item.getType() != itemWanted) return false;
		if(!item.hasItemMeta()) return false;
		if(!item.getItemMeta().hasLore()) return false;
		if(item.getItemMeta().getLore().size() != 1) return false;
		
		if(!item.getItemMeta().getDisplayName().equals(itemNameWanted)) return false;
		if(!item.getItemMeta().getLore().get(0).equals(itemTagWanted)) return false;
		
		return true;
	}
	
	/**
	 * Generates an Item for the DB
	 * 
	 * @return the item wanted for the DB
	 */
	public abstract ItemStack getWantedItem();
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerInteract(PlayerInteractEvent event){
		ItemStack item = event.getItem();
		if(!isWantedItem(item)) return;
		if(!consume || item.getAmount() < consumeAmount) return;
		
		if(trigger(event.getPlayer()) && consume){
			item.setAmount(item.getAmount() - consumeAmount);
			event.getPlayer().setItemInHand(item);
		}
		
	}
	
	/**
	 * Triggers the Item
	 * 
	 * @param player to trigger to
	 * @return true if worked.
	 */
	protected abstract boolean trigger(Player player);
}
