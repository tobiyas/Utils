package de.tobiyas.util.chat.commands;

import org.bukkit.entity.Player;

public class CommandCallbackAsync implements CommandCallback {

	/**
	 * The Runnable to call.
	 */
	private final Runnable runnable;
	
	
	public CommandCallbackAsync(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void playerPressedCallback(Player player, String hint) {
		new Thread(runnable).start();
	}

}
