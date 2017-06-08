package de.tobiyas.util.command.params;

import org.bukkit.command.CommandSender;

import de.tobiyas.util.command.CommandParamType;
import de.tobiyas.util.formating.ParseUtils;

public class FloatCommandParam extends AbstractCommandParam {

	
	private final float DEFAULT_VALUE = -13371337;
	
	
	public FloatCommandParam(String name, boolean optional) {
		super(CommandParamType.Number, name, optional);
	}
	
	
	@Override
	public boolean isValid(String arg) {
		return ParseUtils.parseFloat(arg, DEFAULT_VALUE) != DEFAULT_VALUE;
	}
	
	@Override
	public void sendHelp(String param, CommandSender sender) {
		//TODO implement me
	}
	
	@Override
	public Object toType(String arg) {
		return ParseUtils.parseFloat(arg, 0);
	}

}
