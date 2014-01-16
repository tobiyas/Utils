package de.tobiyas.util.permissions.plugins;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface PermissionPlugin {
	
	/**
	 * Returns if the Plugin is active
	 * 
	 * @return true if active, false otherwise
	 */
	public boolean isActive();
	
	/**
	 * Checks if the sender has Permission to the Node passed.
	 * 
	 * @param sender to check.
	 * @param permissionNode  to check
	 * @return true if has permission, false otherwise
	 */
	public boolean getPermissions(CommandSender sender, String permissionNode);
	
	/**
	 * Checks if the player has Permission to the Node passed.
	 * 
	 * @param playerName to check.
	 * @param permissionNode  to check
	 * @return true if has permission, false otherwise
	 */
	public boolean getPermissions(String playerName, String permissionNode);
	
	/**
	 * Returns all Groups from the Plugin
	 * 
	 * @return List of all groups
	 */
	public ArrayList<String> getGroups();
	
	/**
	 * Returns the Main Group of a player.
	 * 
	 * @param player to get group
	 * @return
	 */
	public String getGroupOfPlayer(Player player);
	
	/**
	 * Does initialization stuff to hook the plugin
	 */
	public void init();
	
	/**
	 * Returns a Human readable name for the Permission Plugin.
	 */
	public String getName();

	/**
	 * Adds a permission to the player.
	 * 
	 * @param player to add
	 * @param permission to add
	 */
	public void addPermission(Player player, String permission);
}
