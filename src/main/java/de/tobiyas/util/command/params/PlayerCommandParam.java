package de.tobiyas.util.command.params;

import org.bukkit.command.CommandSender;

import de.tobiyas.util.command.CommandParamType;
import de.tobiyas.util.player.PlayerUtils;

public class PlayerCommandParam extends AbstractCommandParam {

	
	
	public PlayerCommandParam(String name, boolean optional) {
		super(CommandParamType.Player, name, optional);
	}
	
	
	@Override
	public boolean isValid(String arg) {
		return PlayerUtils.getPlayer(arg) != null;
	}
	
	@Override
	public void sendHelp(String param, CommandSender sender) {
		//TODO implement me
	}
	
	@Override
	public Object toType(String arg) {
		return PlayerUtils.getPlayer(arg);
	}

}
