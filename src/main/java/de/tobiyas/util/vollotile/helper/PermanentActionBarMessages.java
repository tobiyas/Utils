package de.tobiyas.util.vollotile.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import de.tobiyas.util.schedule.DebugBukkitRunnable;
import de.tobiyas.util.vollotile.VollotileCodeManager;

public class PermanentActionBarMessages {

	
	private static PermanentActionBarMessages instance;
	
	/**
	 * The Map of Person -> Message.
	 */
	private final Map<UUID,String> messageMap = new HashMap<UUID,String>();
	
	/**
	 * the task running.
	 */
	private BukkitTask task;
	
	
	protected PermanentActionBarMessages(Plugin plugin) {
		this.task = new DebugBukkitRunnable("PerformanceActionbarMessager"){
			@Override
			protected void runIntern() {
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
		if(player == null) return;
		
		synchronized (messageMap) { if(messageMap.remove(player.getUniqueId()) == null) return; }
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
	
	/**
	 * Returns true if the Message-Task is running.
	 */
	public static boolean isRunning(){
		return instance != null;
	}
	
	
	/**
	 * Finally kills the display!
	 * <br>Call get again to recreate!
	 */
	public static void kill(){
		if(instance == null) return;
		instance.task.cancel();
		instance = null;
	}
	
}
