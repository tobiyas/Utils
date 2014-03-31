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
import org.bukkit.plugin.Plugin;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;
import de.bananaco.bpermissions.api.util.Permission;

public class BPermissionsPermissions implements PermissionPlugin{
	
	private boolean isActive;
	
	
	public BPermissionsPermissions(){
		isActive = false;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean getPermissions(CommandSender sender, String permissionNode) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			return ApiLayer.hasPermission(player.getWorld().getName(), CalculableType.USER, player.getName(), permissionNode);
		}else{
			return ApiLayer.hasPermission(null, CalculableType.USER, sender.getName(), permissionNode);
		}
	}

	@Override
	public ArrayList<String> getGroups() {
		ArrayList<String> groups = new ArrayList<String>();
		//Not supported By BPerms
		return groups;
	}

	@Override
	public String getGroupOfPlayer(Player player) {
		String[] groups = ApiLayer.getGroups(player.getWorld().getName(), CalculableType.GROUP, player.getName());
		if(groups.length == 0) return "";
		return groups[0];
	}

	@Override
	public void init() {
		isActive = checkActive();
	}
	
	private boolean checkActive(){
		try{
			Plugin bPerms = Bukkit.getServer().getPluginManager().getPlugin("bPermissions");
			if(bPerms == null) return false;
			if(!bPerms.isEnabled()) return false;
		}catch(Exception e){
			return false;
		}
		
		return true;
	}

	@Override
	public String getName() {
		return "bPermissions";
	}

	
	@Override
	public boolean getPermissions(String playerName, String permissionNode) {
		Player player = Bukkit.getPlayer(playerName);
		if(player == null) return false;
		
		return getPermissions(player, permissionNode);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addPermission(Player player, String permission) {
		ApiLayer.addPermission(player.getWorld().getName(), CalculableType.USER, player.getName(), Permission.loadFromString(permission));
	}

}
