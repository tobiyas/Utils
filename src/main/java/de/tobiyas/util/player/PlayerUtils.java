package de.tobiyas.util.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerUtils {

	
	
	/**
	 * Returns an Offline Player from the player passed.
	 * <br>This first scans online players.
	 * <br>Then it scanns offline Players.
	 * <br>If nothing found, it will return null.
	 * 
	 * @param playerName to convert.
	 * 
	 * @return the player or null.
	 */
	public static OfflinePlayer getOfflinePlayer(String playerName){
		Player player = getPlayer(playerName);
		if(player != null) return player;
		
		for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
			if(offlinePlayer.getName().equalsIgnoreCase(playerName)) return offlinePlayer;			
		}
		
		return null;
	}
	
	/**
	 * Returns a player from the Name passed.
	 * This is a loop throught the active player names.
	 * 
	 * @param playerName to check.
	 * 
	 * @return the player wanted or null
	 */
	public static Player getPlayer(String playerName){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(playerName)) return player;
		}
		
		return null;
	}
}
