package de.tobiyas.util.vollotile.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.tobiyas.util.vollotile.VollotileCodeManager;

public class PermanentActionBarMessages {

	
	private static PermanentActionBarMessages instance;
	
	/**
	 * The Map of Person -> Message.
	 */
	private final Map<UUID,String> messageMap = new HashMap<UUID,String>();
	
	
	
	
	protected PermanentActionBarMessages(Plugin plugin) {
		new BukkitRunnable() {
			@Override
			public void run() {
				Map<UUID,String> map = new HashMap<UUID, String>();
				synchronized (messageMap) { map.putAll(messageMap); }
				
				Iterator<Map.Entry<UUID,String>> it = map.entrySet().iterator();
				
				while(it.hasNext()){
					Map.Entry<UUID, String> entry = it.next();
					UUID id = entry.getKey();
					String message = entry.getValue();
					
					Player player = Bukkit.getPlayer(id);
					if(player == null || !player.isOnline()){
						it.remove();
						continue;
					}
					
					VollotileCodeManager.getVollotileCode().sendActionBarMessage(player, message);
				}
			}
		}.runTaskTimer(plugin, 10, 10);
	}
	
	
	/**
	 * Sets the Message to a player.
	 * 
	 * @param player to set to.
	 * @param message to set.
	 */
	public void setMessage(Player player, String message){
		if(message == null || message.isEmpty() || !player.isOnline()) {
			removeMessage(player);
			return;
		}
		
		synchronized (messageMap) {
			messageMap.put(player.getUniqueId(), message);
		}
	}
	
	/**
	 * Removes the Message
	 * 
	 * @param player to remove from.
	 */
	public void removeMessage(Player player){
		synchronized (messageMap) {
			messageMap.remove(player.getUniqueId());
		}
		
		VollotileCodeManager.getVollotileCode().sendActionBarMessage(player, "");
	}

	
	/**
	 * Returns the Instance.
	 * 
	 * @return the Instance.
	 */
	public static PermanentActionBarMessages get(Plugin plugin){
		if(instance == null) instance = new PermanentActionBarMessages(plugin);
		return instance;
	}
	
}
