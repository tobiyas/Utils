package de.tobiyas.util.command.params;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import de.tobiyas.util.command.CommandParamType;

public class MaterialCommandParam extends AbstractCommandParam {

	
	
	public MaterialCommandParam(String name, boolean optional) {
		super(CommandParamType.Player, name, optional);
	}
	
	
	@Override
	public boolean isValid(String arg) {
		return Material.matchMaterial(arg) != null;
	}
	
	@Override
	public void sendHelp(String param, CommandSender sender) {
		
	}
	
	@Override
	public Object toType(String arg) {
		return Material.matchMaterial(arg);
	}

}
