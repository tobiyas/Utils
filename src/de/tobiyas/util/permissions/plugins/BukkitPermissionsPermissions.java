package de.tobiyas.util.permissions.plugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;

public class BukkitPermissionsPermissions implements PermissionPlugin {

	private PermissionsPlugin permissions;
	private boolean isActive;
	
	public BukkitPermissionsPermissions(){
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
		return  sender.hasPermission(permissionNode);
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
		return "BukkitPermissions";
	}

}
