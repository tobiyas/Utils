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

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.util.player.PlayerUtils;

public class OpPermissions implements PermissionPlugin {

	private boolean isActive;
	
	public OpPermissions(){
		isActive = true;
	}
	
	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean getPermissions(CommandSender sender, String permissionNode) {
		return sender.isOp();
	}

	@Override
	public ArrayList<String> getGroups() {
		ArrayList<String> emptyList = new ArrayList<String>();
		return emptyList;
	}

	@Override
	public String getGroupOfPlayer(OfflinePlayer player) {
		return "";
	}

	@Override
	public void init() {
		//No inition needed.
	}

	@Override
	public String getName() {
		return "OpPermissions";
	}

	@Override
	public boolean getPermissions(String playerName, String permissionNode) {
		Player player = PlayerUtils.getPlayer(playerName);
		if(player == null) return false;
		
		return player.isOp();
	}

	@Override
	public void addPermission(OfflinePlayer player, String permission) {
	}

	@Override
	public void removePermission(OfflinePlayer player, String permission) {
	}

	@Override
	public boolean hasGroupSupport() {
		return false;
	}

	@Override
	public boolean hasSubgroupSupport() {
		return false;
	}

	@Override
	public void addSubgroup(OfflinePlayer player, String subgroup) {
	}

	@Override
	public void removeSubgroup(OfflinePlayer player, String subgroup) {
	}

}
