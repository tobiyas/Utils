package de.tobiyas.util.quickbarconfiger.defaultbars;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.autocomplete.AutoCompleteUtils;
import de.tobiyas.util.quickbarconfiger.Bar;
import de.tobiyas.util.quickbarconfiger.BarHandler;

public class BarStringChooser extends Bar implements Listener {

	/**
	 * The Callback to use.
	 */
	private final StringChosenCallback callback;
	
	/**
	 * The Old value.
	 */
	private final String oldValue;
	
	/**
	 * The Current value to use.
	 */
	private String currentValue;
	
	/**
	 * The collection of auto-completes.
	 */
	private final Collection<String> autoCompletes = new HashSet<String>();
	
	
	
	public BarStringChooser(JavaPlugin plugin, BarHandler barHandler, Player player, String oldValue, StringChosenCallback callback) {
		this(plugin, barHandler, player, oldValue, callback, null);
	}
	
	public BarStringChooser(JavaPlugin plugin, BarHandler barHandler, Player player, String oldValue, StringChosenCallback callback,
			Collection<String> autoCompletes) {
		super(barHandler, player);
		
		this.oldValue = oldValue;
		this.currentValue = oldValue;
		this.callback = callback;
		
		if(autoCompletes != null) this.autoCompletes.addAll(autoCompletes);
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		player.sendMessage(ChatColor.GREEN + "Neuen Text eingeben und auf " + ChatColor.LIGHT_PURPLE + "APPLY" + ChatColor.GREEN + " drücken!");
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void updateItemsIntern() {
		this.clickList.set(0, new ItemClickBi(
				generateItem(Material.WOOL, DyeColor.GREEN.getWoolData(), ChatColor.GREEN + "Apply" , ChatColor.GREEN + "Nimmt den Aktuellen Wert."),
				new ItemClickedCallback() { @Override public void itemClicked(Block block) { applyString(); } }
			));
		
		this.clickList.set(3, new ItemShowBi(
				generateItem(Material.WOOL, DyeColor.BLUE.getWoolData(), ChatColor.AQUA + "Value: " + currentValue, ChatColor.AQUA + "Der aktuelle Wert:", currentValue)
			));
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(AsyncPlayerChatEvent event){
		if(event.getPlayer() != player) return;
		
		//Extract the Message.
		String name = event.getMessage();
		if("none".equalsIgnoreCase(name) || "kein".equalsIgnoreCase(name)) name = "";
		
		player.sendMessage(ChatColor.GREEN + "Neuer Text: " + name);
		this.currentValue = name;
		
		event.setFormat("");
		event.setMessage("");
		event.setCancelled(true);
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPreChat(PlayerCommandPreprocessEvent event){
		if(event.getPlayer() != player) return;
		
		//Extract the Message.
		String name = event.getMessage();
		if("none".equalsIgnoreCase(name) || "kein".equalsIgnoreCase(name)) name = "";
		
		player.sendMessage(ChatColor.GREEN + "Neuer Text: " + name);
		this.currentValue = name;
		
		event.setMessage("");
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChatAutoComplete(PlayerChatTabCompleteEvent event){
		if(event.getPlayer() != player) return;
		
		String token = event.getLastToken();
		event.getTabCompletions().clear();
		
		event.getTabCompletions().addAll(AutoCompleteUtils.getAllNamesWith(autoCompletes, token));
	}
	
	
	/**
	 * Applies the current String.
	 */
	protected void applyString(){
		HandlerList.unregisterAll(this);
		
		this.callback.newStringChosen(false, oldValue, currentValue);
		this.barHandler.backPressed();
	}
	
	
	@Override
	protected void back(Block unused) {
		HandlerList.unregisterAll(this);
		
		this.callback.newStringChosen(true, oldValue, oldValue);
		super.back(unused);
	}
	
	
	public static interface StringChosenCallback{
		/** A new String was chosen */
		void newStringChosen(boolean cancled, String oldValue, String newString);
	}

}
