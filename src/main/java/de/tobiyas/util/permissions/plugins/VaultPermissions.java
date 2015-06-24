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
import java.util.List;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.tobiyas.util.player.PlayerUtils;

public class VaultPermissions implements PermissionPlugin {
	
	private Permission vaultPermission;
	private boolean isActive;
	
	public VaultPermissions(){
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean getPermissions(CommandSender sender, String permissionNode) {
		if(sender == null) return false;
		if(permissionNode == null || permissionNode == "") return false;
		if(!isActive()) return false;
		return vaultPermission.has(sender, permissionNode);
	}

	@Override
	public ArrayList<String> getGroups() {
		ArrayList<String> groups = new ArrayList<String>();
		if(!isActive()) return groups;
		
		for(String group : vaultPermission.getGroups()){
			groups.add(group);
		}
		return groups;
	}

	@Override
	public void init() {
		isActive = initVault();
	}
	

	/**
	 * Sets up vault
	 * 
	 * @return if it worked 
	 */
	private boolean initVault(){
		try{
			RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
			if (permissionProvider != null)
				vaultPermission = permissionProvider.getProvider();
			}catch(Exception e){}
		
		return (vaultPermission != null);
	}

	@Override
	public String getGroupOfPlayer(OfflinePlayer player) {
		if(!isActive()) return "";
		return vaultPermission.getPrimaryGroup(null, player);		
	}

	@Override
	public String getName() {
		return "Vault";
	}

	
	/**
	 * Returns the underlying Vault permission system
	 * 
	 * @return
	 */
	public Permission getHandle(){
		return vaultPermission;
	}

	@Override
	public boolean getPermissions(String playerName, String permissionNode) {
		Player player = PlayerUtils.getPlayer(playerName);
		if(player == null) return false;
		
		return getPermissions(player, permissionNode);
	}

	@Override
	public void addPermission(OfflinePlayer player, String permission) {
		vaultPermission.playerAdd(null, player, permission);
	}
	
	@Override
	public void removePermission(OfflinePlayer player, String permission) {
		vaultPermission.playerRemove(null, player, permission);
	}

	@Override
	public boolean hasGroupSupport() {
		return true;
	}

	@Override
	public boolean hasSubgroupSupport() {
		return false;
	}

	@Override
	public void addSubgroup(OfflinePlayer player, String subgroup) {
		//not supported
	}

	@Override
	public void removeSubgroup(OfflinePlayer player, String subgroup) {
		//not supported
	}

	@Override
	public void addGroup(OfflinePlayer player, String group) {
		Player rPlayer = player.getPlayer();
		if(rPlayer == null) return;
		
		vaultPermission.playerAddGroup(rPlayer, group);
	}

	@Override
	public void removeGroup(OfflinePlayer player, String group) {
		Player rPlayer = player.getPlayer();
		if(rPlayer == null) return;
		
		vaultPermission.playerRemoveGroup(rPlayer, group);
	}

	@Override
	public List<String> listSubgroups(OfflinePlayer player) {
		ArrayList<String> groups = new ArrayList<String>();
		if(!isActive()) return groups;
		
		for(String group : vaultPermission.getGroups()){
			groups.add(group);
		}
		return groups;
	}
}
