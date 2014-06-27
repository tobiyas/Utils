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

	/**
	 * Removes a Permission from a player.
	 * 
	 * @param player to remove from
	 * @param permisson to remove
	 */
	public void removePermission(Player player, String permission);
	
	
	//SUBGROUP PART
	
	/**
	 * Adds a Subgroup to the player.
	 * 
	 * @param player to add to
	 * @param subgroup to add
	 */
	public void addSubgroup(Player player, String subgroup);
	
	/**
	 * removes a Subgroup to the player.
	 * 
	 * @param player to remove from
	 * @param subgroup to remove
	 */
	public void removeSubgroup(Player player, String subgroup);
}
