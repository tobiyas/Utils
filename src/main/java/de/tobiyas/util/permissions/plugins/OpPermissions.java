package de.tobiyas.util.permissions.plugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
	public String getGroupOfPlayer(Player player) {
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
		Player player = Bukkit.getPlayer(playerName);
		if(player == null) return false;
		
		return player.isOp();
	}

	@Override
	public void addPermission(Player player, String permission) {
	}

}
