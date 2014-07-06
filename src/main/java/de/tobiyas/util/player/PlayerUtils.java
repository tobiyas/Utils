package de.tobiyas.util.player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;

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
		for(Player player : getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(playerName)) return player;
		}
		
		return null;
	}
	
	/**
	 * Returns a player from the Name passed.
	 * This is a loop throught the active player names.
	 * 
	 * @return the players that are online
	 */
	@SuppressWarnings("unchecked")
	public static Collection<? extends Player> getOnlinePlayers(){
		Collection<Player> collection = new HashSet<Player>();
		
		try {
		    if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class)
		        for(Player player : ((Collection<? extends Player>)Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]))){
		        	collection.add(player);
		        }
		    else
		        for(Player player : ((Player[])Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]))){
		        	collection.add(player);
		        }
		}
		catch (NoSuchMethodException ex){} // can never happen
		catch (InvocationTargetException ex){} // can also never happen
		catch (IllegalAccessException ex){} // can still never happen
		
		return collection;
	}
}
