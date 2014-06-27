package de.tobiyas.util.chat.commands;

import java.util.UUID;

import org.bukkit.entity.Player;

public class ClickCommand {

	private final UUID playerID;
	
	/**
	 * The Name of the Command
	 */
	private final String name;
	
	/**
	 * The Callback to call if this was clicked.
	 */
	private final CommandCallback callback;
	
	
	/**
	 * The Hint that is been given on callback.
	 */
	private final String hint;
	
	
	public ClickCommand(Player player, String hint, CommandCallback callback) {
		this.name = UUID.randomUUID().toString();
		this.playerID = player.getUniqueId();
		this.callback = callback;
		this.hint = hint;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the callback
	 */
	public CommandCallback getCallback() {
		return callback;
	}


	public UUID getPlayerID() {
		return playerID;
	}
	


	/**
	 * @return the hint
	 */
	public String getHint() {
		return hint;
	}
	
	/**
	 * Triggers the Callback.
	 * 
	 * @param player to trigger to.
	 */
	public void trigger(Player player){
		callback.playerPressedCallback(player, hint);
	}
}
