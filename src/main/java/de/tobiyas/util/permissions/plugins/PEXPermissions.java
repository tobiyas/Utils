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

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import de.tobiyas.util.player.PlayerUtils;

public class PEXPermissions implements PermissionPlugin {
	
	private PermissionsEx pexPlugin;
	private boolean isActive;
	
	public PEXPermissions(){
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean getPermissions(CommandSender sender, String permissionNode) {
		if(!(sender instanceof Player)) return true;
		if(!isActive()) return false;
		
		Player player = (Player) sender;
		
		return pexPlugin.has(player, permissionNode);
	}

	@Override
	public ArrayList<String> getGroups() {
		ArrayList<String> groups = new ArrayList<String>();
		if(!isActive()) return groups;
		
		
		List<PermissionGroup> pexGroups = PermissionsEx.getPermissionManager().getGroupList();
		for(PermissionGroup pexGroup : pexGroups){
			groups.add(pexGroup.getName());
		}
		
		return groups;
	}

	@Override
	public void init() {
		isActive = initPEX();
	}
	
	private boolean initPEX(){
		try{
			PermissionsEx pex = (PermissionsEx) Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");
			if(pex == null) return false;
			pexPlugin = pex;
		}catch(Exception e){
			return false;
		}
		
		return true;
	}

	@Override
	public String getGroupOfPlayer(OfflinePlayer player) {
		if(!isActive()) return "";
		if (PermissionsEx.getPermissionManager().getUser(player.getName()).getGroupsNames().length > 0)
            return PermissionsEx.getPermissionManager().getUser(player.getName()).getGroupsNames()[0];
        else
            return "";
	}

	@Override
	public String getName() {
		return "PermissionsEx";
	}

	@Override
	public boolean getPermissions(String playerName, String permissionNode) {
		Player player = PlayerUtils.getPlayer(playerName);
		if(player == null) return false;
		
		return getPermissions(player, permissionNode);
	}

	@Override
	public void addPermission(OfflinePlayer player, String permission) {
		PermissionsEx.getPermissionManager().getUser(player.getName()).addPermission(permission);
	}
	
	@Override
	public void removePermission(OfflinePlayer player, String permission) {
		PermissionsEx.getPermissionManager().getUser(player.getName()).removePermission(permission);
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
		PermissionsEx.getPermissionManager().getUser(player.getName()).addGroup(subgroup);
	}

	@Override
	public void removeSubgroup(OfflinePlayer player, String subgroup) {
		PermissionsEx.getPermissionManager().getUser(player.getName()).removeGroup(subgroup);
	}

	@Override
	public void addGroup(OfflinePlayer player, String group) {
		PermissionsEx.getPermissionManager().getUser(player.getName()).addGroup(group);
	}

	@Override
	public void removeGroup(OfflinePlayer player, String group) {
		PermissionsEx.getPermissionManager().getUser(player.getName()).removeGroup(group);
	}

	@Override
	public List<String> listSubgroups(OfflinePlayer player) {
		ArrayList<String> groups = new ArrayList<String>();
		if(!isActive()) return groups;
		
		List<PermissionGroup> pexGroups = PermissionsEx.getPermissionManager().getGroupList();
		for(PermissionGroup pexGroup : pexGroups){
			groups.add(pexGroup.getName());
		}
		
		return groups;
	}

}
