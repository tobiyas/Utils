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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.util.player.PlayerUtils;

public class GroupManagerPermissions implements PermissionPlugin {

	private GroupManager groupManager;
	private boolean isActive;
	
	public GroupManagerPermissions(){
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
		return hasPermissionGroupManager(player, permissionNode);
	}
	
	private boolean hasPermissionGroupManager(final Player base, final String node){
		final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(base);
		if (handler == null){
			return false;
		}
		return handler.has(base, node);
	}

	@Override
	public ArrayList<String> getGroups() {
		ArrayList<String> groups = new ArrayList<String>();
		if(!isActive()) return groups;
		
		Collection<Group> groupList = groupManager.getWorldsHolder().getDefaultWorld().getGroupList();
		
		for(Group group : groupList){
			groups.add(group.getName());
		}
		
		return groups;
	}

	@Override
	public void init() {
		isActive = initGM();
	}
	
	private boolean initGM(){
		try{
			GroupManager gm = (GroupManager) Bukkit.getServer().getPluginManager().getPlugin("GroupManager");
			if(gm == null) return false;
			groupManager = gm;
		}catch(Exception e){
			return false;
		}
		
		return true;
	}


	@Override
	public String getGroupOfPlayer(OfflinePlayer player) {
		if(!isActive()) return "";
		
		Group group = groupManager.getWorldsHolder().getDefaultWorld().getDefaultGroup();
		if(group == null) return "";
		
		return group.getName();
	}


	@Override
	public String getName() {
		return "GroupManager";
	}


	@Override
	public boolean getPermissions(String playerName, String permissionNode) {
		Player player = PlayerUtils.getPlayer(playerName);
		if(player == null) return false;
		
		return getPermissions(player, permissionNode);
	}


	@Override
	public void addPermission(OfflinePlayer player, String permission) {
		groupManager.getWorldsHolder().getDefaultWorld().getUser(player.getName()).addPermission(permission);
	}


	@Override
	public void removePermission(OfflinePlayer player, String permission) {
		groupManager.getWorldsHolder().getDefaultWorld().getUser(player.getName()).removePermission(permission);
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
	public void addSubgroup(OfflinePlayer player, String subgroup) {
		Group subgroupObj = groupManager.getWorldsHolder().getDefaultWorld().getGroup(subgroup);
		if(subgroupObj == null) return;
		
		groupManager.getWorldsHolder().getDefaultWorld().getUser(player.getName()).addSubGroup(subgroupObj);
	}


	@Override
	public void removeSubgroup(OfflinePlayer player, String subgroup) {
		Group subgroupObj = groupManager.getWorldsHolder().getDefaultWorld().getGroup(subgroup);
		if(subgroupObj == null) return;
		
		groupManager.getWorldsHolder().getDefaultWorld().getUser(player.getName()).removeSubGroup(subgroupObj);
	}


	@Override
	public void addGroup(OfflinePlayer player, String group) {
		Group groupObj = groupManager.getWorldsHolder().getDefaultWorld().getGroup(group);
		if(groupObj == null) return;
		
		groupManager.getWorldsHolder().getDefaultWorld().getUser(player.getName()).setGroup(groupObj);
	}


	@Override
	public void removeGroup(OfflinePlayer player, String group) {
		Group groupObj = groupManager.getWorldsHolder().getDefaultWorld().getGroup(group);
		if(groupObj == null) return;
		
		groupManager.getWorldsHolder().getDefaultWorld().getUser(player.getName()).removeSubGroup(groupObj);
	}


	@Override
	public List<String> listSubgroups(OfflinePlayer player) {
		List<String> ret = new LinkedList<String>();
		for(Group group : groupManager.getWorldsHolder().getDefaultWorld().getGroupList()){
			ret.add(group.getName());
		}
		
		return ret;
	}

}
