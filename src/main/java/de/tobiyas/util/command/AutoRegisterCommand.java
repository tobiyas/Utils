package de.tobiyas.util.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.autocomplete.AutoCompleteUtils;

public abstract class AutoRegisterCommand implements TabExecutor {

	
	/**
	 * The Main Permission.
	 */
	private final String mainPerm;
	
	/**
	 * The Main command.
	 */
	private final String command;
	
	
	/**
	 * The sub-Commands registered.
	 */
	private final Collection<SubCommandInfo> subCommands = new ArrayList<SubCommandInfo>();
	
	
	
	
	public AutoRegisterCommand(JavaPlugin plugin, String command, String mainPerm) {
		this.mainPerm = mainPerm;
		this.command = command;
		
		PluginCommand pluginCommand = plugin.getCommand(command);
		if(pluginCommand == null){
			plugin.getLogger().log(Level.SEVERE, "Could not register Command: " + command);
			return;
		}
		
		//generate the Sub-Commands:
		for(Method method : getClass().getMethods()){
			if(method.isAnnotationPresent(SubCommand.class)){
				try{
					subCommands.add(new SubCommandInfo(this, method));
				}catch(Throwable exp){
					plugin.getLogger().log(Level.SEVERE, "Could not register Method: '" 
							+ method.getName() + "' in Command: '" + command + "'", exp);
				}
			}
		}
	}



	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String subCommand = args.length == 0 ? "help" : args[0];
		String[] stripped = stripFirst(args);
		
		//Check for the Infos:
		for(SubCommandInfo info : subCommands){
			String ownSub = info.getSubCommand();
			if(ownSub.equalsIgnoreCase(subCommand)){
				if(info.isAppliable(sender, true) && info.isAppliable(stripped, sender, true)) {
					info.execute(sender, stripped);
				}
				
				return true;
			}
		}
		
		//Check for Help:
		postHelp(sender);
		return true;
	}
	
	/**
	 * Strips the first arg.
	 * @param args to strip from.
	 * @return the stripped args.
	 */
	private String[] stripFirst(String[] args){
		return Arrays.copyOfRange(args, 1, args.length);
	}	
	
	/**
	 * Posts the Help to the Player.
	 * @param sender to send to.
	 */
	private void postHelp(CommandSender sender){
		//TODO post help
	}
	
	/**
	 * Returns the Main permission.
	 * @return the main Permission.
	 */
	public String getMainPerm() {
		return mainPerm;
	}
	
	/**
	 * Gets the Main Command.
	 * @return the main command.
	 */
	public String getCommand() {
		return command;
	}
	
	

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length <= 1){
			Set<String> subArgs = new HashSet<String>(Arrays.asList("help"));
			for(SubCommandInfo info : subCommands) {
				if(info.isAppliable(sender, false)) subArgs.add(info.getSubCommand());
			}
			
			return AutoCompleteUtils.getAllNamesWith(subArgs, args.length == 0 ? "" : args[0]);
		}
		
		//Check the Sub-Args:
		if(args.length > 1){
			String subCommand = args[0];
			for(SubCommandInfo info : subCommands){
				if(info.getSubCommand().equalsIgnoreCase(subCommand)){
					if(!info.isAppliable(sender, false)) return null;
					
					return info.onTabComplete(sender, stripFirst(args));
				}
				
			}
		}
		
		return null;
	}

}
