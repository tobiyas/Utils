package de.tobiyas.util.quickbarconfiger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BarHandler implements Listener {

	/**
	 * The Stack to use.
	 */
	protected final Stack<Bar> barBackStack = new Stack<Bar>();
	
	/**
	 * The current used bar.
	 */
	protected Bar currentBar;
	
	/**
	 * The player used for the Bar.
	 */
	protected final Player player;
	
	/**
	 * The Old hotbar stuff.
	 */
	protected List<ItemStack> oldHotBar = new ArrayList<ItemStack>(9);
	
	/**
	 * This is a quickfix for events beeing thrown multiple times.
	 */
	protected Map<UUID,Long> lastClickEvent = new HashMap<UUID,Long>();
	
	
	
	public BarHandler(Player player, JavaPlugin plugin) {
		this.player = player;
		
		//Register for event-Listening.
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
		//Save old items:
		for(int i = 0; i < 9; i++) oldHotBar.add(player.getInventory().getItem(i));
	}
	
	
	/**
	 * Notifies that the back button was pressed.
	 */
	public void backPressed() {
		if(barBackStack.isEmpty()) exit();
		else {
			currentBar = barBackStack.pop();
			updateCurrentBar();
		}
	}
	
	/**
	 * Notfies that the back button was pressed.
	 */
	public void goToSubBar(Bar newBar) {
		//Never use null bars!
		if(newBar == null) return;
		
		//Push current bar to backstack.
		if(currentBar != null) barBackStack.push(currentBar);
		this.currentBar = newBar;
		
		//Update new Bar:
		updateCurrentBar();
	}
	
	
	/**
	 * Redraws the current Bar.
	 */
	public void redrawCurrentBar(){
		updateCurrentBar();
	}
	
	
	/**
	 * Force closes the Bar.
	 */
	public void forceClose(){
		for(Bar bar : barBackStack) bar.forceClose();
		if(currentBar != null) currentBar.forceClose();
		this.exit();
	}
	
	
	/**
	 * If the bar passed is the root view.
	 * @param bar to check.
	 * @return true if is root view.
	 */
	public boolean isRootView(Bar bar){
		if(bar == null) return false;
		
		if(bar == currentBar && barBackStack.isEmpty()) return true;
		return barBackStack.get(0) == bar;
	}
	
	
	/**
	 * Updates the current bar.
	 */
	private void updateCurrentBar(){
		if(currentBar == null) return; //Sanity check.
		
		currentBar.updateItems();
		List<ItemStack> toShow = currentBar.getItemsToShow();
		for(int i = 0; i < 9; i++) player.getInventory().setItem(i, toShow.get(i));
	}


	@EventHandler
	public void doNotThrowAway(PlayerDropItemEvent event){
		if(event.getPlayer() == player) event.setCancelled(true);
	}
	
	@EventHandler
	public void doNotInventory(InventoryClickEvent event){
		if(event.getView().getPlayer() == player) event.setCancelled(true);
	}

	@EventHandler
	public void doNotPickup(PlayerPickupItemEvent event){
		if(event.getPlayer() == player) event.setCancelled(true);
	}
	
	@EventHandler
	public void doNotPickup(PlayerPickupArrowEvent event){
		if(event.getPlayer() == player) event.setCancelled(true);
	}
	
	
	@EventHandler
	public void playerClickedItem(PlayerInteractEvent event){
		if(event.isCancelled()) return;
		if(event.getPlayer() != player) return;
		
		//Cancle to be sure!
		event.setCancelled(true);
		
		//Quick fix for multiple events in some miliseconds.
		long now = System.currentTimeMillis();
		if(lastClickEvent.containsKey(player.getUniqueId())){
			long last = lastClickEvent.get(player.getUniqueId());
			if(now - last < 100) return;
		}
		
		//Always add the Last click time:
		this.lastClickEvent.put(player.getUniqueId(), now);
		
		//Gets the used slot.
		int slot = event.getPlayer().getInventory().getHeldItemSlot();
		
		//Only reacto on right click Block!
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			Block block = event.getClickedBlock();
			currentBar.slotPressed(slot, block);
		}
	}
	
	@EventHandler
	public void charLogout(PlayerQuitEvent event){
		if(event.getPlayer() != player) return;
		this.forceClose();
	}


	/**
	 * Exits everything.
	 */
	private void exit() {
		HandlerList.unregisterAll(this);
		for(int i = 0; i < 9; i++) player.getInventory().setItem(i, oldHotBar.get(i));
	}

}
