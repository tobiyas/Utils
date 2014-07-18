/**
 * @author Toby
 *
 */
package de.tobiyas.util.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import de.tobiyas.util.UtilsUsingPlugin;
import de.tobiyas.util.permissions.plugins.BPermissionsPermissions;
import de.tobiyas.util.permissions.plugins.GroupManagerPermissions;
import de.tobiyas.util.permissions.plugins.OpPermissions;
import de.tobiyas.util.permissions.plugins.PEXPermissions;
import de.tobiyas.util.permissions.plugins.PermissionPlugin;
import de.tobiyas.util.permissions.plugins.PermissionsBukkitPermissions;
import de.tobiyas.util.permissions.plugins.VaultPermissions;

public class PermissionManager implements PermissionPlugin{

	private PermissionPlugin permPlugin;
	private UtilsUsingPlugin plugin;
	
	/**
	 * Sets up the Permission Manager for the Plugin
	 *
	 * @param denialList: a string list of PermissionPlugins to be ignored while check
	 * @param plugin: the Plugin it is using
	 */
	public PermissionManager(UtilsUsingPlugin plugin, ArrayList<String> denialList){
		this.plugin = plugin;
		permPlugin = checkForPermissionsPlugin(denialList);
	}
	
	
	/**
	 * Sets up the Permission Manager for the Plugin
	 *
	 * @param plugin the Plugin it is using
	 */
	public PermissionManager(UtilsUsingPlugin plugin){
		this.plugin = plugin;
		permPlugin = checkForPermissionsPlugin(null);
	}
	
	private PermissionPlugin checkForPermissionsPlugin(ArrayList<String> denialList){
		if(denialList == null)
			denialList = new ArrayList<String>();
		
		PermissionPlugin tempPlugin;
		if(!containsStringIgnoreCase("Vault", denialList))
			try{
				tempPlugin = new VaultPermissions();
				tempPlugin.init();
				if(tempPlugin.isActive()){
					return tempPlugin;
				}
			}catch(NoClassDefFoundError e){}
		
		if(!containsStringIgnoreCase("PermissionsEX", denialList))
			try{
				tempPlugin = new PEXPermissions();
				tempPlugin.init();
				if(tempPlugin.isActive()){
					return tempPlugin;
				}
			}catch(NoClassDefFoundError e){}
		
		if(!containsStringIgnoreCase("GroupManager", denialList))
			try{
				tempPlugin = new GroupManagerPermissions();
				tempPlugin.init();
				if(tempPlugin.isActive()){
					return tempPlugin;
				}
			}catch(NoClassDefFoundError e){}
		
		if(!containsStringIgnoreCase("PermissionsBukkit", denialList))
			try{
				tempPlugin = new PermissionsBukkitPermissions(plugin);
				tempPlugin.init();
				if(tempPlugin.isActive()){
					return tempPlugin;
				}
			}catch(NoClassDefFoundError e){}
		
		if(!containsStringIgnoreCase("BPermissions", denialList))
			try{
				tempPlugin = new BPermissionsPermissions();
				tempPlugin.init();
				if(tempPlugin.isActive()){
					return tempPlugin;
				}
			}catch(NoClassDefFoundError e){}
		
		
		tempPlugin = new OpPermissions();
		plugin.getLogger().log(Level.WARNING,"Info: No Permission-System hooked. Plugin will use Op-State to consider Permissions.");
		return tempPlugin;
	}
	
	private boolean containsStringIgnoreCase(String noCheck, List<String> list){
		for(String name : list){
			if(name.equalsIgnoreCase(noCheck))
				return true;
		}
		return false;
	}

	
	/**
	 * The Check of Permissions on the initialized Permission-System.
	 * It checks the Permission and the lower-cased Permission. 
	 * 
	 * @param player the Player to check
	 * @param permissionNode the String to check
	 * @return if the Player has Permissions
	 */
	private boolean checkPermissionsIntern(CommandSender sender, String permissionNode){
		if(sender == null) return false;
		boolean hasPermissionsNormal = permPlugin.getPermissions(sender, permissionNode);
		boolean hasPermissionsLowercase = permPlugin.getPermissions(sender, permissionNode.toLowerCase());
		return hasPermissionsNormal || hasPermissionsLowercase;
	}
	
	
	/**
	 * The Check of Permissions on the inited Permission-System
	 * It will notice the Player, if he has no Permissions
	 * 
	 * @param sender the Player to check
	 * @param permissionNode the String to check
	 * @return if the Player has Permissions
	 */
	public boolean checkPermissions(CommandSender sender, String permissionNode){
		boolean perm = checkPermissionsIntern(sender, permissionNode);
		if(!perm){
			sender.sendMessage(ChatColor.RED + "You don't have Permissions!");
		}
		
		return perm;
	}
	
	/**
	 * Checks the Permission of a Sender, without noticing him
	 * 
	 * @param sender the sender person to check
	 * @param permissionNode the Permission to check
	 * @return if the Player has Permissions
	 */
	public boolean checkPermissionsSilent(CommandSender sender, String permissionNode){
		return checkPermissionsIntern(sender, permissionNode);
	}
	
	/**
	 * Returns if the PlayerName has the wanted Permission.
	 * 
	 * @param playerName to check
	 * @param permissionNode to check
	 * 
	 * @return true if the Player has the Access, false otherwise.
	 */
	public boolean checkPermissionsSilent(String playerName, String permissionNode){
		return permPlugin.getPermissions(playerName, permissionNode);
	}
	
	/**
	 * Returns all groups of the current PermissionSystem
	 * 
	 * @return ArrayList with all groups
	 */
	public ArrayList<String> getAllGroups(){
		return permPlugin.getGroups();
	}
	
	/**
	 * Gets the current main Group of a Player
	 * 
	 * @param player
	 * @return the group of this Player
	 */
	@Override
	public String getGroupOfPlayer(OfflinePlayer player){
		return permPlugin.getGroupOfPlayer(player);
	}
	
	/**
	 * Gets the Name of the current PermissionSystem
	 * 
	 * @return name (String)
	 */
	public String getPermissionsName(){
		return permPlugin.getName();
	}
	
	/**
	 * Checks if the Sender has any of the Permissions in the String-Array
	 * Does not give the Player notice about not having
	 * 
	 * @param sender (CommandSender)
	 * @param permissions (String-Array)
	 * @return boolean
	 */
	public boolean hasAnyPermissionSilent(CommandSender sender, String[] permissions){
		for(String perm : permissions){
			if(checkPermissionsIntern(sender, perm)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if the Sender has any of the Permissions in the String-Array
	 * gives the Player notice about not having
	 * 
	 * @param sender (CommandSender)
	 * @param permissions (String-Array)
	 * @return boolean
	 */
	public boolean hasAnyPermissions(CommandSender sender, String[] permissions){
		boolean hasPerm = hasAnyPermissionSilent(sender, permissions);
		if(!hasPerm) sender.sendMessage(ChatColor.RED + "You don't have Permissions!");
		
		return hasPerm;
	}
	
	/**
	 * Returns the underlying Permission Plugin used.
	 * 
	 * WARNING: Only use this when absolutely needed!
	 * 
	 * @return
	 */
	public PermissionPlugin getHandle(){
		return permPlugin;
	}


	
	/**
	 * Adds a permission to a player
	 * 
	 * @param player to add to
	 * @param permission to add
	 */
	@Override
	public void addPermission(OfflinePlayer player, String permission) {
		permPlugin.addPermission(player, permission);
	}


	@Override
	public boolean isActive() {
		return permPlugin.isActive();
	}


	@Override
	public boolean getPermissions(CommandSender sender, String permissionNode) {
		return permPlugin.getPermissions(sender, permissionNode);
	}


	@Override
	public boolean getPermissions(String playerName, String permissionNode) {
		return permPlugin.getPermissions(playerName, permissionNode);
	}


	@Override
	public ArrayList<String> getGroups() {
		return permPlugin.getGroups();
	}


	@Override
	public void init() {
		permPlugin = checkForPermissionsPlugin(null);
	}


	@Override
	public String getName() {
		return permPlugin.getName();
	}


	@Override
	public void removePermission(OfflinePlayer player, String permission) {
		permPlugin.removePermission(player, permission);
	}


	@Override
	public boolean hasGroupSupport() {
		return permPlugin.hasGroupSupport();
	}


	@Override
	public boolean hasSubgroupSupport() {
		return permPlugin.hasSubgroupSupport();
	}


	@Override
	public void addSubgroup(OfflinePlayer player, String subgroup) {
		if(!hasSubgroupSupport()) return;
		permPlugin.addSubgroup(player, subgroup);
	}


	@Override
	public void removeSubgroup(OfflinePlayer player, String subgroup) {
		if(!hasSubgroupSupport()) return;
		permPlugin.removeSubgroup(player, subgroup);
	}

	
}
