/**
 * @author Toby
 *
 */
package de.tobiyas.util.permissions;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.permissions.plugins.BukkitPermissionsPermissions;
import de.tobiyas.util.permissions.plugins.GroupManagerPermissions;
import de.tobiyas.util.permissions.plugins.OpPermissions;
import de.tobiyas.util.permissions.plugins.PEXPermissions;
import de.tobiyas.util.permissions.plugins.PermissionPlugin;
import de.tobiyas.util.permissions.plugins.VaultPermissions;

public class PermissionManager{

	private PermissionPlugin permPlugin;
	private JavaPlugin plugin;
	
	/**
	 * Sets up the Permission Manager for the Plugin
	 *
	 * @param plugin the Plugin it is using
	 */
	public PermissionManager(JavaPlugin plugin){
		this.plugin = plugin;
		permPlugin = checkForPermissionsPlugin();
	}
	
	private PermissionPlugin checkForPermissionsPlugin(){
		PermissionPlugin tempPlugin;
		try{
			tempPlugin = new VaultPermissions();
			tempPlugin.init();
			if(tempPlugin.isActive()){
				return tempPlugin;
			}
		}catch(NoClassDefFoundError e){}
		
		try{
			tempPlugin = new PEXPermissions();
			tempPlugin.init();
			if(tempPlugin.isActive()){
				return tempPlugin;
			}
		}catch(NoClassDefFoundError e){}
		
		try{
			tempPlugin = new GroupManagerPermissions();
			tempPlugin.init();
			if(tempPlugin.isActive()){
				return tempPlugin;
			}
		}catch(NoClassDefFoundError e){}
		
		try{
			tempPlugin = new BukkitPermissionsPermissions();
			tempPlugin.init();
			if(tempPlugin.isActive()){
				return tempPlugin;
			}
		}catch(NoClassDefFoundError e){}
		
		try{
			tempPlugin = new BukkitPermissionsPermissions();
			tempPlugin.init();
			if(tempPlugin.isActive()){
				return tempPlugin;
			}
		}catch(NoClassDefFoundError e){}
		
		
		tempPlugin = new OpPermissions();
		plugin.getLogger().log(Level.WARNING,"Info: No Permission-System hooked. Plugin will use Op-State to consider Permissions.");
		return tempPlugin;
	}

	
	/**
	 * The Check of Permissions on the inited Permission-System
	 * It will notice the Player, if he has no Permissions
	 * 
	 * @param player the Player to check
	 * @param permissionNode the String to check
	 * @return if the Player has Permissions
	 */
	private boolean checkPermissionsIntern(CommandSender sender, String permissionNode){
		if(sender == null) return false;
		return permPlugin.getPermissions(sender, permissionNode);
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
		if(!perm)
			sender.sendMessage(ChatColor.RED + "You don't have Permissions!");
		
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
	public String getGroupOfPlayer(Player player){
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
			if(checkPermissionsIntern(sender, perm))
				return true;
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
	
}
