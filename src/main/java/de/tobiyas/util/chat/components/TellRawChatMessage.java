package de.tobiyas.util.chat.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.tobiyas.util.chat.SendRaw;
import de.tobiyas.util.naming.MCPrettyName;

public class TellRawChatMessage {

	/**
	 * The Message split in parts.
	 */
	private final List<ChatMessageObject> message = new LinkedList<ChatMessageObject>();
	
	
	/**
	 * Adds a part to the message.
	 * 
	 * @param messagePart to add
	 */
	public TellRawChatMessage append(ChatMessageObject messagePart){
		message.add(messagePart);
		return this;
	}
	
	
	/**
	 * Sends the Message to a set of players.
	 * 
	 * @param players to send to.
	 */
	public void sendToPlayers(Collection<Player> players){
		if(players.isEmpty()) return;
		
		String buildMessage = buildMessage();
		for(Player player : players){
			SendRaw.sendPlayerRawMessage(player, buildMessage);
		}
	}
	
	
	/**
	 * Builds the message present in this Builder.
	 * 
	 * @return the String message.
	 */
	public String buildMessage() {
		JsonObject message = new JsonObject();
		message.addProperty("text", "");
		
		JsonArray array = new JsonArray();
		for(int i = 0; i < this.message.size(); i++){
			array.add(this.message.get(i).getObject());
		}
		
		message.add("extra", array);
		return message.toString();
	}
	
	/**
	 * Builds the message present in this Builder.
	 * 
	 * @return the String message.
	 */
	public String buildBook() {
		JsonObject message = new JsonObject();
		
		JsonArray array = new JsonArray();
		for(int i = 0; i < this.message.size(); i++){
			array.add(this.message.get(i).getObject());
		}
		
		message.add("text", array);
		return message.toString();
	}



	/**
	 * Sends the Message to the players passed.
	 * 
	 * @param players to send to
	 */
	public void sendToPlayers(Player... players){
		sendToPlayers(Arrays.asList(players));
	}
	
	
	/**
	 * Adds a simple text to the Message.
	 * 
	 * @param text to send.
	 */
	public TellRawChatMessage addSimpleText(String text){
		message.add(new ChatMessageObject(text));
		return this;
	}


	/**
	 * Adds a simple Item tag.
	 * 
	 * @param item to add.
	 */
	public TellRawChatMessage addSimpleItem(ItemStack item) {
		if(item == null) return this;
		
		String name = MCPrettyName.getPrettyName(item, "de_DE");
		String last = ChatColor.getLastColors(name);
		name = last + "[" + name + last + "]";
		
		addSimpleItem(item, name);
		return this;
	}
	
	/**
	 * Adds a simple Item tag.
	 * 
	 * @param item to add.
	 * @param label to set
	 */
	public TellRawChatMessage addSimpleItem(ItemStack item, String label) {
		if(item == null) return this;
		
		ChatMessageObject obj = new ChatMessageObject(label);
		obj.addItemHover(item);
		
		message.add(obj);
		return this;
	}
	
	
	/**
	 * Adds a simple popup.
	 * 
	 * @param label to add
	 * @param message to add
	 */
	public TellRawChatMessage addSimplePopup(String label, String message){
		ChatMessageObject obj = new ChatMessageObject(label);
		obj.addPopupHover(message);
		
		this.message.add(obj);
		return this;
	}
	
	/**
	 * Adds a simple Clickable URL.
	 * 
	 * @param label to add
	 * @param URL to add
	 */
	public TellRawChatMessage addSimpleURL(String label, String URL){
		ChatMessageObject obj = new ChatMessageObject(label);
		obj.addURLClickable(URL);
		
		this.message.add(obj);
		return this;
	}
	
	
	/**
	 * Adds a simple Clickable Command.
	 * 
	 * @param label to add
	 * @param command to add
	 */
	public TellRawChatMessage addSimpleCommand(String label, String command){
		ChatMessageObject obj = new ChatMessageObject(label);
		obj.addCommandClickable(command);
		
		this.message.add(obj);
		return this;
	}
	
	/**
	 * Adds a simple Clickable commandSuggestion.
	 * 
	 * @param label to add
	 * @param commandSuggestion to add
	 */
	public TellRawChatMessage addSimpleCommandSuggestion(String label, String commandSuggestion){
		ChatMessageObject obj = new ChatMessageObject(label);
		obj.addCommandSuggestionClickable(commandSuggestion);
		
		this.message.add(obj);
		return this;
	}


	/**
	 * Appends the rest of the TellRaw passed.
	 * 
	 * @param buildTellRawMessage to append
	 */
	public void append(TellRawChatMessage buildTellRawMessage) {
		for(ChatMessageObject obj : buildTellRawMessage.message){
			message.add(obj);
		}
	}
	
	
	@Override
	public String toString() {
		return buildMessage();
	}
}
