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
package de.tobiyas.util.permissions.plugins;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public interface PermissionPlugin {
	
	/**
	 * Returns if the Plugin is active
	 * 
	 * @return true if active, false otherwise
	 */
	public boolean isActive();
	
	/**
	 * Returns if the Plugin has Suppoer for groups.
	 * 
	 * @return true if active, false otherwise
	 */
	public boolean hasGroupSupport();
	
	/**
	 * Returns if the Plugin is has Support for subgroups.
	 * 
	 * @return true if active, false otherwise
	 */
	public boolean hasSubgroupSupport();
	
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
	public List<String> getGroups();
	
	/**
	 * Returns the Main Group of a player.
	 * 
	 * @param player to get group
	 * @return
	 */
	public String getGroupOfPlayer(OfflinePlayer player);
	
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
	public void addPermission(OfflinePlayer player, String permission);

	/**
	 * Removes a Permission from a player.
	 * 
	 * @param player to remove from
	 * @param permisson to remove
	 */
	public void removePermission(OfflinePlayer player, String permission);
	
	
	/**
	 * Adds a Player to the Group.
	 * 
	 * @param player to add
	 * @param group to add.
	 */
	public void addGroup(OfflinePlayer player, String group);
	
	/**
	 * Removes a Player from the Group.
	 * 
	 * @param player to remove.
	 * @param group to remove.
	 */
	public void removeGroup(OfflinePlayer player, String group);
	
	
	
	//SUBGROUP PART
	
	/**
	 * Adds a Subgroup to the player.
	 * 
	 * @param player to add to
	 * @param subgroup to add
	 */
	public void addSubgroup(OfflinePlayer player, String subgroup);
	
	/**
	 * removes a Subgroup to the player.
	 * 
	 * @param player to remove from
	 * @param subgroup to remove
	 */
	public void removeSubgroup(OfflinePlayer player, String subgroup);
	
	
	/**
	 * Lists all Subgroups.
	 * 
	 * @param player to remove from
	 * 
	 * @return the List of Subgroups.
	 */
	public List<String> listSubgroups(OfflinePlayer player);
}
