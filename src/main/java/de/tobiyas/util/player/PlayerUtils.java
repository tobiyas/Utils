package de.tobiyas.util.player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.tobiyas.util.schedule.DebugBukkitRunnable;

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
			if(offlinePlayer == null || offlinePlayer.getName() == null) continue;
			
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
			if(player.getName().equalsIgnoreCase(playerName)
					|| ChatColor.stripColor(player.getDisplayName()).equalsIgnoreCase(playerName)) return player;
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
	public static Collection<Player> getOnlinePlayers(){
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
	
	
	/**
	 * Updates the Player-Inventory the next Tick.
	 * 
	 * @param player to update
	 * @param plugin to call on.
	 */
	public static void updateInvNextTick(final UUID player, Plugin plugin){
		new DebugBukkitRunnable("PlayerUpdateInvTick") {
			@SuppressWarnings("deprecation")
			@Override
			protected void runIntern() {
				Player pl = Bukkit.getPlayer(player);
				if(pl != null && pl.isOnline()) pl.updateInventory();
			}
		}.runTaskLater(plugin, 1);
	}

	/**
	 * Returns the Player ONLY from the online Players.
	 * 
	 * @param playerUUID to search
	 * @return the player.
	 */
	public static Player getPlayer(UUID playerUUID) {
		if(playerUUID == null) return null;
		
		for(Player pl : getOnlinePlayers()){
			if(pl.getUniqueId().equals(playerUUID)) return pl;
		}
		
		return null;
	}
	
	
	/**
	 * Gets all Players near the Location.
	 * 
	 * @param location to search around.
	 * @param distance to search.
	 * 
	 * @return the players near.
	 */
	public static Collection<Player> getPlayersNear(Location location, double distance){
		distance *= distance; //sqare the Distance.
		
		Collection<Player> players = new HashSet<Player>();
		if( location == null || distance <= 0 ) return players;
		
		World world = location.getWorld();
		for(Player player : world.getPlayers()){
			if(player.getLocation().distanceSquared(location) < distance){
				players.add(player);
			}
		}
		
		return players;
	}
	
}
