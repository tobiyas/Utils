package de.tobiyas.util.chat;

import org.bukkit.entity.Player;

import de.tobiyas.util.vollotile.VollotileCodeManager;

public class SendRaw {

	/**
	 * Sends a raw message to the player passed.
	 * 
	 * @param player to send to
	 * @param message to send
	 */
	public static void sendPlayerRawMessage(Player player, String message){
		try{
			VollotileCodeManager.getVollotileCode().sendRawMessage(player, message);
			/*IChatBaseComponent component = ChatSerializer.a(message);
			((CraftPlayer) player) .getHandle().sendMessage(component);*/
		}catch(Throwable exp){
			player.sendMessage(message);
		}
	}

}
