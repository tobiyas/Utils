package de.tobiyas.util.permissions.plugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;

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
		boolean hasPerm = false;
		if(sender instanceof Player){
			Player player = (Player) sender;
			hasPerm = ApiLayer.hasPermission(player.getWorld().getName(), CalculableType.USER, player.getName(), permissionNode);
		}else
			hasPerm = ApiLayer.hasPermission(null, CalculableType.USER, sender.getName(), permissionNode);
			
		return hasPerm;
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

}
