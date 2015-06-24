package de.tobiyas.util.chat.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.tobiyas.util.UtilsUsingPlugin;

public class ClickCommandManager implements Listener{

	/**
	 * The Set of clickcommands to use.
	 */
	private final Map<UUID,Map<ClickCommand,Long>> clickCommands = new HashMap<UUID,Map<ClickCommand,Long>>();
	
	
	/**
	 * The set of old commands.
	 */
	private final Map<String,Long> oldCommands = new HashMap<String,Long>();
	
	
	
	public ClickCommandManager(UtilsUsingPlugin plugin){
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	/**
	 * Registers a new Command.
	 * 
	 * @param player the player registering the Command
	 * @param hint to do on callback.
	 * @param callback to call
	 * 
	 * @return the generated command name.
	 */
	public String registerCommand(Player player, String hint, CommandCallback callback){
		ClickCommand command = new ClickCommand(player, hint, callback);
		
		Map<ClickCommand,Long> playerMap = clickCommands.containsKey(player.getUniqueId()) 
				? clickCommands.get(player.getUniqueId())
				: new HashMap<ClickCommand, Long>();
		
		playerMap.put(command, System.currentTimeMillis());
		clickCommands.put(player.getUniqueId(), playerMap);
		
		return command.getName();
	}
	
	
	
	/**
	 * 5 min. Time.
	 */
	private final long MAX_LENGTH = 1000 * 60 * 5;
	
	
	/**
	 * Removes a new Command.
	 * 
	 * @param name to delete.
	 */
	public void removeCommand(String name){
		oldCommands.remove(name);
		
		long currentTime = System.currentTimeMillis();
		for(Map<ClickCommand,Long> element : clickCommands.values()){
			Iterator<Entry<ClickCommand,Long>> it = element.entrySet().iterator();
			while(it.hasNext()){
				Entry<ClickCommand, Long> entry = it.next();
				ClickCommand command = entry.getKey();
				long time = entry.getValue();
				
				if(currentTime - time > MAX_LENGTH) {
					it.remove();
					continue;
				}
				
				String commandName = command.getName();
				
				if(name.equals(commandName)){
					it.remove();
					return;
				}
			}
		}
	}

	
	@EventHandler
	public void playerCommandCheck(PlayerCommandPreprocessEvent event){
		if(event.getMessage().length() < 2) return;
		
		//remove first letter
		String commandName = event.getMessage().substring(1); 
		Player player = event.getPlayer();
		
		//we do not have any 
		if(commandName.contains(" ")) return;
		
		
		//check old Names.
		long currentTime = System.currentTimeMillis();
		Iterator<Entry<String,Long>> oldCommandIt = new HashMap<String,Long>(oldCommands).entrySet().iterator();
		while(oldCommandIt.hasNext()){
			Entry<String, Long> entry = oldCommandIt.next();
			
			long time = entry.getValue();
			
			if(currentTime - time > MAX_LENGTH) {
				//player.sendMessage(ChatColor.RED + "This has already been used.");
				oldCommandIt.remove();
				continue;
			}
		}
		
		//check the Commands here.
		currentTime = System.currentTimeMillis();
		Map<ClickCommand,Long> clickComands = this.clickCommands.get(player.getUniqueId());
		if(clickComands == null) return;
		
		Iterator<Entry<ClickCommand,Long>> commandIt = clickComands.entrySet().iterator();
		while(commandIt.hasNext()){
			Entry<ClickCommand, Long> entry = commandIt.next();
			ClickCommand command = entry.getKey();
			long time = entry.getValue();
			
			if(currentTime - time > MAX_LENGTH) {
				commandIt.remove();
				continue;
			}
			
			String name = command.getName();
			
			if(name.equals(commandName)){
				commandIt.remove();
				oldCommands.put(name, currentTime);
				
				event.setMessage("/");
				event.setCancelled(true);
				
				command.trigger(player);
				return;
			}
		}
		
	}

}
