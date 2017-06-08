package de.tobiyas.util.command;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.util.command.params.AbstractCommandParam;
import de.tobiyas.util.command.params.CommandParamFactory;
import de.tobiyas.util.command.params.ParameterNotSupportedException;

public class SubCommandInfo {

	/**
	 * The Base command class to register to.
	 */
	private final AutoRegisterCommand command;
	
	/**
	 * The Read command interface.
	 */
	private final SubCommand subCommand;
	
	/**
	 * The Method to call if correct.
	 */
	private final Method method;
	
	/**
	 * The Params to use.
	 */
	private final List<AbstractCommandParam> params = new ArrayList<AbstractCommandParam>();
	
	
	
	public SubCommandInfo(AutoRegisterCommand command, Method method) throws ParameterNotSupportedException {
		super();
		
		this.command = command;
		this.subCommand = method.getAnnotation(SubCommand.class);
		this.method = method;
		
		//Generate from the Method passed:
		for(Parameter param : method.getParameters()){
			this.params.add(CommandParamFactory.generateByArg(param));
		}
	}

	
	
	/**
	 * if this is appliable to the Sender passed.
	 * @param sender to check.
	 * @return true if appliable.
	 */
	public boolean isAppliable(CommandSender sender, boolean sendMsgToSender) {		
		//Check for Console / Player:
		if(sender instanceof Player && subCommand.consoleOnly()) {
			if(sendMsgToSender) sender.sendMessage(ChatColor.RED + "Only Console may use this command.");
			return false;
		}
		
		if(sender instanceof ConsoleCommandSender && subCommand.playerOnly()) {
			if(sendMsgToSender) sender.sendMessage(ChatColor.RED + "Only Players may use this command.");
			return false;
		}
		
		//check for Permissions: First check op -> Then check normal:
		boolean hasPerms = subCommand.opBypassedPerm() && sender.isOp();
		if(!hasPerms){
			String perm = command.getMainPerm() + (subCommand.appendPermissionToMainCommand() ? ("." + subCommand.permission()) : "");
			if(!perm.isEmpty()){
				if(!sender.hasPermission(perm)) {
					if(sendMsgToSender) sender.sendMessage(ChatColor.RED + "You do not have the Permission to use this command.");
					return false;
				}
			}
		}
		
		return true;
	}
	


	/**
	 * If the Method is Appliable.
	 * @param args to check
	 * @return true if appliable.
	 */
	public boolean isAppliable(String[] args, CommandSender sender, boolean sendMsgToSender){		
		int argSize = args.length;
		int index = 0;
		for(AbstractCommandParam param : params){
			//We are out of arguments:
			if(index >= argSize) {
				if(param.isOptional()) continue;
				else { if(sendMsgToSender) postHelp(sender); return false;}
			}
			
			//We need to verify that the argument-type are correct:
			String arg = args[index];
			if(!param.isValid(arg)) {
				if(sendMsgToSender) param.sendHelp(arg, sender);
				return false;
			}
			
			index++;
		}
		
		return true;
	}
	
	/**
	 * Posts help for the Params.
	 */
	public void postHelp(CommandSender sender){
		if(!subCommand.help().isEmpty()) {
			sender.sendMessage(ChatColor.RED + subCommand.help());
			return;
		}
		
		
	}
	
	/**
	 * Gets the Sub-COmmand to check:
	 * @return the sub-Command.
	 */
	public String getSubCommand(){
		return subCommand.subCommand();
	}
	
	
	/**
	 * Executes the Command.
	 * @param args the args to execute.
	 */
	public void execute(CommandSender sender, String[] args){
		try{
			Object[] convArgs = new Object[args.length];
			for(int i = 0; i < args.length; i++) convArgs[i] = params.get(i).toType(args[i]);
			method.invoke(command, convArgs);
		}catch (Throwable exp) {
			sender.sendMessage(ChatColor.RED + "Could not execute command " + command.getCommand() + "" + getSubCommand() + "");
			exp.printStackTrace();
		}
	}


	/**
	 * Does an Auto-Complete for the sender / args.
	 * @param sender that executed the auto-complete.
	 * @param args the args, stripped by the Sub-Command.
	 * 
	 * @return the list of Tab-Completes.
	 */
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		//TODO implement This!
		return null;
	}
	
	
}
