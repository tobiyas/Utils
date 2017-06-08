package de.tobiyas.util.command.params;

import org.bukkit.command.CommandSender;

import de.tobiyas.util.command.CommandParamType;
import de.tobiyas.util.formating.ParseUtils;

public class DoubleCommandParam extends AbstractCommandParam {

	
	private final double DEFAULT_VALUE = -13371337;
	
	
	public DoubleCommandParam(String name, boolean optional) {
		super(CommandParamType.Number, name, optional);
	}
	
	
	@Override
	public boolean isValid(String arg) {
		return ParseUtils.parseDouble(arg, DEFAULT_VALUE) != DEFAULT_VALUE;
	}
	
	@Override
	public void sendHelp(String param, CommandSender sender) {
		//TODO implement me
	}
	
	@Override
	public Object toType(String arg) {
		return ParseUtils.parseDouble(arg, 0);
	}

}
