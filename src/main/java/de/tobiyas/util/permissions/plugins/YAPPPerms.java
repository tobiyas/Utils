package de.tobiyas.util.permissions.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.yapp.API.GroupAPI;
import de.tobiyas.yapp.API.PlayerPermAPI;
import de.tobiyas.yapp.perm.Group;
import de.tobiyas.yapp.perm.PermPlayer;

public class YAPPPerms implements PermissionPlugin {

	
	
	@Override
	public boolean isActive() {
		return Bukkit.getPluginManager().isPluginEnabled("YAPP");
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
	public boolean getPermissions(CommandSender sender, String permissionNode) {
		if(sender == Bukkit.getConsoleSender()) return true;
		if(sender instanceof Player) return PlayerPermAPI.hasAnyPermission(((Player)sender).getUniqueId(), permissionNode);
		return false;
	}

	@Override
	@Deprecated
	public boolean getPermissions(String playerName, String permissionNode) {
		return false;
	}

	@Override
	public List<String> getGroups() {
		ArrayList<String> names = new ArrayList<String>();
		for(Group group : GroupAPI.getGroups()) names.add(group.getName());
		
		return names;
	}

	@Override
	public String getGroupOfPlayer(OfflinePlayer player) {
		PermPlayer pl = PlayerPermAPI.getPlayer(player.getUniqueId());
		Set<Group> groups = pl.getGroups();
		if(groups.isEmpty()) return null;
		
		return groups.iterator().next().getName();
	}

	@Override
	public void init() {
		//Nothing to do here.
	}

	@Override
	public String getName() {
		return "YAPP";
	}

	@Override
	public void addPermission(OfflinePlayer player, String permission) {
		PlayerPermAPI.getPlayer(player.getUniqueId()).addSinglePerm(permission);
	}

	@Override
	public void removePermission(OfflinePlayer player, String permission) {
		PlayerPermAPI.getPlayer(player.getUniqueId()).removeSinglePerm(permission);
	}

	@Override
	public void addGroup(OfflinePlayer player, String group) {
		Group grp = GroupAPI.getGroupByName(group);
		if(grp != null) PlayerPermAPI.getPlayer(player.getUniqueId()).addGroup(grp);
	}

	@Override
	public void removeGroup(OfflinePlayer player, String group) {
		Group grp = GroupAPI.getGroupByName(group);
		if(grp != null) PlayerPermAPI.getPlayer(player.getUniqueId()).removeGroup(grp);
	}

	@Override
	public void addSubgroup(OfflinePlayer player, String subgroup) {
		addGroup(player, subgroup);
	}

	@Override
	public void removeSubgroup(OfflinePlayer player, String subgroup) {
		removeGroup(player, subgroup);
	}

	@Override
	public List<String> listSubgroups(OfflinePlayer player) {
		return getGroups();
	}
	
	
}
