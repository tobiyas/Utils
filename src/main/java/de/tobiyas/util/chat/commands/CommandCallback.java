package de.tobiyas.util.chat.commands;

import org.bukkit.entity.Player;

public interface CommandCallback {

	
	/**
	 * The Callback was pressed.
	 * 
	 * @param player that callbacked.
	 */
	public void playerPressedCallback(Player player, String hint);
}
