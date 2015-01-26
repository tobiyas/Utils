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
package de.tobiyas.util.inventorymenu;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BasicSelectionInterface extends ItemGeneratorInterface implements Listener{	
	
	/**
	 * The list of open invs.
	 */
	protected final static List<BasicSelectionInterface> openInterfaces = new LinkedList<BasicSelectionInterface>();
	
	/**
	 * Indicates that a new InventoryOpening is going on.
	 */
	protected boolean isOpeningNewInv = false;
	
	/**
	 * Plugin to call Scheduling from
	 */
	protected final JavaPlugin plugin;
	
	
	/**
	 * An Item placed at ItemSlot ((3 * 9) / 2) to register an Back Click.
	 */
	protected final ItemStack BACK_OBJECT;
	
	/**
	 * An Item placed at ItemSlot (((3 * 9) / 2) -1 ) to register an Accepted Click.
	 */
	protected final ItemStack ACCEPT_OBJECT;
	
	
	/**
	 * The Parent interface for the Selection.
	 * Null if ROOT.
	 */
	protected BasicSelectionInterface parent;
	
	/**
	 * The Player constructed for
	 */
	protected final Player player;
	
	/**
	 * The Inventory to show (Top Inventory)
	 */
	protected final Inventory selectionInventory;
	
	/**
	 * The Inventory to show the Controls (Bottom Inventory)
	 */
	protected final Inventory controlInventory;
	
	/**
	 * if also left click is used for selection
	 */
	protected boolean alsoUseLeftClick = false;
	
	
	/**
	 * Creates a Selection Interface.
	 * The control interface is fixed at the bottom.
	 * 
	 * parent is the Parent object to open.
	 * If No Parent is found (parent == null), the Interface is closed.
	 *  
	 * @param player to show
	 * @param parent that it is linked to
	 * @param controlInventoryName name of the control inventory
	 * @param selectionInventoryName name of the selection inventory
	 */
	public BasicSelectionInterface(Player player, BasicSelectionInterface parent, 
			String controlInventoryName, String selectionInventoryName, JavaPlugin plugin) {
		this.player = player;
		this.parent = parent;
		this.plugin = plugin;
		
		if(selectionInventoryName.length() > 32) selectionInventoryName = selectionInventoryName.substring(0, 32);
		if(controlInventoryName.length() > 32) controlInventoryName = controlInventoryName.substring(0, 32);
		
		selectionInventory = Bukkit.createInventory(player, 3 * 9, selectionInventoryName);
		controlInventory = Bukkit.createInventory(player, 4 * 9, controlInventoryName);
		
		BACK_OBJECT = generateBackItem();
		ACCEPT_OBJECT = generateAcceptItem();
		
		
		controlInventory.setItem(0, ACCEPT_OBJECT);
		controlInventory.setItem(8, BACK_OBJECT);
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		openInterfaces.add(this);
	}
	
	
	/**
	 * Creates a Selection Interface.
	 * The control interface is fixed at the bottom.
	 * 
	 * parent is the Parent object to open.
	 * If No Parent is found (parent == null), the Interface is closed.
	 *  
	 * @param player
	 * @param parent
	 */
	public BasicSelectionInterface(Player player, BasicSelectionInterface parent, JavaPlugin plugin) {
		this(player, parent, "Control", "Data", plugin);
	}
	

	@Override
	public Inventory getTopInventory() {
		return selectionInventory;
	}

	@Override
	public Inventory getBottomInventory() {
		return controlInventory;
	}

	@Override
	public HumanEntity getPlayer() {
		return player;
	}

	@Override
	public InventoryType getType() {
		return InventoryType.CHEST;
	}
	
	
	
	@EventHandler
	public void onInterfaceInteract(InventoryClickEvent event){
		if(event.getView() != this) return;
		
		ItemStack itemClicked = event.getCurrentItem();
		if(itemClicked == null || itemClicked.getType() == Material.AIR) return;

		
		event.setCancelled(true);
		
		if(event.getClick() != ClickType.RIGHT){
			if(!(event.getClick() == ClickType.LEFT 
					//&& plugin.getConfigManager().getGeneralConfig().isConfig_alsoUseLeftClickInGuis()
			  )){
				return;
			}
		}
		
		if(this.ACCEPT_OBJECT.equals(itemClicked)){
			onAcceptPressed();
			return;
		}
		
		if(this.BACK_OBJECT.equals(itemClicked)){
			if(onBackPressed()) scheduleCloseOfInventory();
			return;
		}
		
		int slotNumber = event.getSlot();
		
		if(getBottomInventory() == null){
			System.out.println("WTF?!?! bottom Inv == null classe: " + getClass());
		}
		
		if(getBottomInventory().getSize() - 1 >= slotNumber
				&& itemClicked.equals(getBottomInventory().getItem(slotNumber))){
			
			for(ItemStack item : getBottomInventory().getContents()){
				if(item != null && item.getType() != Material.AIR 
						&& item.equals(itemClicked)){
					
					onControlItemPressed(item);	
					return;
				}
			}
		}

		if(getTopInventory() == null){
			System.out.println("WTF?!?! Top Inv == null classe: " + getClass());
		}
		
		if(getTopInventory().getSize() - 1 >= slotNumber
				&& itemClicked.equals(getTopInventory().getItem(slotNumber))){
			
			for(ItemStack item : getTopInventory().getContents()){
				if( item != null && item.getType() != Material.AIR 
						&& item.equals(itemClicked)){
					
					onSelectionItemPressed(item);					
					return;
				}
			}
		}
	}
	
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		if(event.getView() != this) return;
		
		openInterfaces.remove(this);
		if(isOpeningNewInv){
			//We get a close from the new Window opening.
			isOpeningNewInv = false;
			return;
		}
		
		openParent();
		
		HandlerList.unregisterAll(this);
	}


	/**
	 * This is called when the BackItem is pressed.
	 * 
	 * To prevent the View from Returning to last Inventory, 
	 * return false.
	 * 
	 * Returning True indicates returning to parent is okay.
	 */
	protected abstract boolean onBackPressed();
	
	
	/**
	 * This is called when the Accept Item is pressed.
	 */
	protected abstract void onAcceptPressed();
	
	
	/**
	 * This is called when the Player presses an Selection Item (upper inventory)
	 * @param item that was clicked.
	 */
	protected abstract void onSelectionItemPressed(ItemStack item); 
	
	/**
	 * This is called when the Player presses an Control Item (lower inventory)
	 * 
	 * HINT: The Accept / Abort buttons are NOT passed here. They are handled in:
	 * {@link #onBackPressed()} and {@link #onAcceptPressed()}.
	 * 
	 * @param item that was clicked.
	 */
	protected abstract void onControlItemPressed(ItemStack item); 
	
	
	/**
	 * Opens the Parent view.
	 */
	protected void scheduleCloseOfInventory(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				player.closeInventory();
			}
		}, 2);
	}
	
	
	/**
	 * Opens the Parent view.
	 */
	protected void scheduleOpeningOfParent() {
		if(parent == null){
			resyncInv(player);
			return;
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(parent != null){				
					player.openInventory(parent);
					
					openInterfaces.add(BasicSelectionInterface.this);
					
					//register parent Events.
					//Bukkit.getPluginManager().registerEvents(parent, plugin);
					parent.notifyReopened();
				}
			}
		}, 2);
	}
	
	
	/**
	 * This method is called when the Parent is reopened.
	 */
	protected void notifyReopened(){}
	
	
	
	/**
	 * Returns to Parent view
	 */
	private void openParent(){
		scheduleOpeningOfParent();
	}
	
	
	/**
	 * Closes the current view.
	 * On close is called afterwards to return to parent.
	 */
	protected void closeAndReturnToParent(){
		scheduleCloseOfInventory();
	}
	
	
	/**
	 * Safely opens a new View.
	 * This is needed to prevent backPressing bugs.
	 * 
	 * @param newView
	 */
	protected void openNewView(final BasicSelectionInterface newView){
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				BasicSelectionInterface.this.isOpeningNewInv = true;
				player.openInventory(newView);
			}
		}, 1);
	}
	
	
	/**
	 * Generates an Back Item.
	 * 
	 * @return
	 */
	private ItemStack generateBackItem(){
		ItemStack item = new Wool(DyeColor.RED).toItemStack();
		
		ItemMeta meta = item.getItemMeta();
		if(parent == null){
			meta.setDisplayName(ChatColor.RED + "Exit");			
		}else{
			meta.setDisplayName(ChatColor.RED + "ABORT");			
		}
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	
	/**
	 * Generates an Accept Item.
	 * 
	 * @return
	 */
	private ItemStack generateAcceptItem(){
		ItemStack item = new Wool(DyeColor.GREEN).toItemStack();
		
		ItemMeta meta = item.getItemMeta();
		
		if(parent == null){
			meta.setDisplayName(ChatColor.RED + "Save");
		}else{
			meta.setDisplayName(ChatColor.GREEN + "Accept");
		}
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	
	/**
	 * Resyncs the player inv.
	 * 
	 * @param player to sync
	 */
	private void resyncInv(final Player player){
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				player.updateInventory();
			}
		}, 1);
	}
	
	
	/**
	 * This closes all open invs.
	 */
	@SuppressWarnings("deprecation")
	public static void closeAllInvs(){
		if(openInterfaces.isEmpty()) return;
		
		for(BasicSelectionInterface gui : new HashSet<BasicSelectionInterface>(openInterfaces)){
			Player player = (Player) gui.getPlayer();
			if(player != null && player.isOnline()){
				player.closeInventory();
				player.updateInventory();
			}
		}
	}
}
