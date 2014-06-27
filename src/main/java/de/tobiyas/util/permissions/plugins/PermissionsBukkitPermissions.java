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

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;

import de.tobiyas.util.UtilsUsingPlugin;
import de.tobiyas.util.player.PlayerUtils;

public class PermissionsBukkitPermissions implements PermissionPlugin {

	private PermissionsPlugin permissions;
	private boolean isActive;
	private final UtilsUsingPlugin plugin;
	
	public PermissionsBukkitPermissions(UtilsUsingPlugin plugin){
		this.plugin = plugin;
		isActive = false;
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean getPermissions(CommandSender sender, String permissionNode) {
		if(!isActive()) return false;
		if(sender == null) return false;
		if(!(sender instanceof Player)) return true;
		
		Player player = (Player) sender;
		return player.hasPermission(permissionNode);
	}

	@Override
	public ArrayList<String> getGroups() {
		ArrayList<String> groups = new ArrayList<String>();
		if(!isActive()) return groups;
		for(Group group : permissions.getAllGroups()){
			groups.add(group.getName());
		}
		return groups;
	}

	@Override
	public String getGroupOfPlayer(Player player) {
		if(!isActive()) return "";
		if(player == null) return "";
		if(permissions.getPlayerInfo(player.getName()).getGroups().size() == 0) return "";
		return permissions.getPlayerInfo(player.getName()).getGroups().get(0).getName();
	}

	@Override
	public void init() {
		isActive = initBPerms();
	}
	
	private boolean initBPerms(){
		try{
			PermissionsPlugin bPerm = (PermissionsPlugin) Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit");
			if(bPerm == null) return false;
			permissions = bPerm;
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "PermissionsBukkit";
	}

	@Override
	public boolean getPermissions(String playerName, String permissionNode) {
		Player player = PlayerUtils.getPlayer(playerName);
		if(player == null) return false;
		
		return getPermissions(player, permissionNode);
	}

	@Override
	public void addPermission(Player player, String permission) {
		player.addAttachment(plugin, permission, true);
	}
	
	@Override
	public void removePermission(Player player, String permission) {
		player.addAttachment(plugin, permission, false);
	}

	@Override
	public boolean hasGroupSupport() {
		return true;
	}

	@Override
	public boolean hasSubgroupSupport() {
		return true;
	}

	@Override
	public void addSubgroup(Player player, String subgroup) {
		 plugin.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "permissions player addgroup " + player + " " + subgroup);
	}

	@Override
	public void removeSubgroup(Player player, String subgroup) {
		plugin.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "permissions player removegroup " + player + " " + subgroup);
	}

}
