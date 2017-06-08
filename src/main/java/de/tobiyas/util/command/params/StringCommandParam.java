package de.tobiyas.util.command.params;

import org.bukkit.command.CommandSender;

import de.tobiyas.util.command.CommandParamType;

public class StringCommandParam extends AbstractCommandParam {

	
	
	public StringCommandParam(String name, boolean optional) {
		super(CommandParamType.Any, name, optional);
	}
	
	
	@Override
	public boolean isValid(String arg) {
		return true;
	}
	
	@Override
	public void sendHelp(String param, CommandSender sender) {
		//TODO implement me
	}
	
	@Override
	public Object toType(String arg) {
		return arg;
	}

}
