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
		
		message.addProperty("text", "");
		message.add("extra", array);
		return message.toString();
	}

	
	/**
	 * Builds the Normal message without ANY Tellraw stuff.
	 * 
	 * @return normal message
	 */
	public String buildNormal() {
		String returnString = "";
		for(ChatMessageObject obj : message){
			returnString += obj.getLabel();
		}
		
		return returnString;
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
		if(text.contains("§")){
			append(parse(text));
			return this;
		}
		
		message.add(new ChatMessageObject(text));
		return this;
	}
	
	
	/**
	 * Adds a simple text to the Message.
	 * 
	 * @param text to send.
	 * @param bold if it should be bold
	 * @param strikethrough if it should be striked through
	 * @param underlined if it should be striked Through
	 * @param italic if it should be italic
	 * @param color the color to set. null to not use it.
	 */
	public TellRawChatMessage addSimpleText(String text, 
			boolean magic, boolean bold, 
			boolean strikethrough, boolean underlined, 
			boolean italic, ChatColor color){
		
		ChatMessageObject obj = new ChatMessageObject(text);
		if(bold) obj.addBold();
		if(strikethrough) obj.addStrikeThrough();
		if(underlined) obj.addUnderlined();
		if(italic) obj.addItalic();
		if(magic) obj.addMagic();
		
		if(color != null) obj.addChatColor(color);
		
		message.add(obj);
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
	public TellRawChatMessage append(TellRawChatMessage buildTellRawMessage) {
		for(ChatMessageObject obj : buildTellRawMessage.message){
			message.add(obj);
		}
		
		return this;
	}
	
	
	/**
	 * Adds a new Line to the end.
	 */
	public TellRawChatMessage addNewLine() {
		addSimpleText("\n");
		return this;
	}
	
	
	@Override
	public String toString() {
		return buildMessage();
	}

	
	
	/**
	 * This creates a TellRawMessage Dependent on the Message passed.
	 * 
	 * @param message to set.
	 * @return the parsed TellRawMessage
	 */
	public static TellRawChatMessage parse(String message){
		//if we have No ChatColor, ignore the Rest...
		if(!message.contains("§")) return new TellRawChatMessage().addSimpleText(message);
		
		boolean magic = false;
		boolean bold = false;
		boolean strikethrough = false;
		boolean underlined = false;
		boolean italic = false;
		ChatColor currentColor = ChatColor.WHITE;
		
		TellRawChatMessage tellRaw = new TellRawChatMessage();
		String[] split = message.split("§");
		
		for(String sub : split){
			//if we have a 0 lenght, we have to ignore it!
			if(sub.length() == 0) continue;
			
			//first check for current color.
			char toParse = sub.charAt(0);
			
			if((toParse >= '0' && toParse <= '9')
				|| toParse >= 'a' && toParse <= 'f'){
				
				currentColor = ChatColor.getByChar(toParse);
			}
			
			if(toParse == 'k') magic = true;
			if(toParse == 'l') bold = true;
			if(toParse == 'm') strikethrough = true;
			if(toParse == 'n') underlined = true;
			if(toParse == 'o') italic = true;
			if(toParse == 'r'){ bold = false; strikethrough = false; underlined = false; italic = false; }
			
			sub = sub.substring(1);
			tellRaw.addSimpleText(sub, magic, bold, strikethrough, underlined, italic, currentColor);
		}
		
		return tellRaw;
	}
	
}
