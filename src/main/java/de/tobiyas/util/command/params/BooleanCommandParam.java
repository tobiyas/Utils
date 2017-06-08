package de.tobiyas.util.command.params;

import org.bukkit.command.CommandSender;

import de.tobiyas.util.command.CommandParamType;
import de.tobiyas.util.formating.ParseUtils;
import de.tobiyas.util.formating.StringFormatUtils;

public class BooleanCommandParam extends AbstractCommandParam {

	
	public BooleanCommandParam(String name, boolean optional) {
		super(CommandParamType.Number, name, optional);
	}
	
	
	@Override
	public boolean isValid(String arg) {
		return StringFormatUtils.equalsAny(arg, "true", "false", "yes", "no", "on", "off");
	}
	
	@Override
	public void sendHelp(String param, CommandSender sender) {
		//TODO implement me
	}
	
	@Override
	public Object toType(String arg) {
		return ParseUtils.parseBoolean(arg, false);
	}

}
