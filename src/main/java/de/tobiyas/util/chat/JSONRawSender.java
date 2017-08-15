/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tobiyas.util.chat;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.tobiyas.util.chat.resolver.AchievementJSONResolver;
import de.tobiyas.util.chat.resolver.CommandJSONResolver;
import de.tobiyas.util.chat.resolver.ItemJSONResolver;
import de.tobiyas.util.chat.resolver.PlainTextJSONResolver;
import de.tobiyas.util.chat.resolver.PopupJSONResolver;
import de.tobiyas.util.chat.resolver.URLJSONResolver;

public class JSONRawSender {

	public enum ChatResolveType{
		ITEM,
		UNFORMATED_TEXT,
		ACHIEVEMENT,
		URL,
		COMMAND,
		POPUP,
		
		RAW,
	}
	
	public static class ChatResolve{
		private final ChatResolveType type;
		private final Object toParse;
		private final String label;
		
		public ChatResolve(ChatResolveType type, Object toParse, String label) {
			this.type = type;
			this.toParse = toParse;
			this.label = label;
		}

		public ChatResolveType getType() {
			return type;
		}

		public Object getToParse() {
			return toParse;
		}

		public String getLabel() {
			return label;
		}
		
	}
	
	
	/**
	 * The Message to build.
	 */
	private Queue<ChatResolve> messageQueue = new LinkedList<JSONRawSender.ChatResolve>(); 
	
	
	public JSONRawSender() {
	}
	
	
	/**
	 * Appends a message to the resolver.
	 * <br>This unformated and needs to be put to JSON
	 * <br>Empty Messages will not be added
	 * 
	 * @param message to append
	 * 
	 * @return this for building
	 */
	public JSONRawSender append(String message){
		if(!"".equals(message)){
			messageQueue.add(new ChatResolve(ChatResolveType.UNFORMATED_TEXT, message, null));
		}
		return this;
	}
	
	
	/**
	 * Appends a message to the resolver
	 * <br>This adds the RAW text
	 * 
	 * @param message to append
	 * @param type to convert to
	 * 
	 * @return this for building
	 */
	public JSONRawSender appendRaw(String message){
		messageQueue.add(new ChatResolve(ChatResolveType.RAW, message, null));
		return this;
	}
	
	/**
	 * Appends a message to the resolver
	 * <br>This adds the RAW text
	 * 
	 * @param message to append
	 * @param type to convert to
	 * 
	 * @return this for building
	 */
	public JSONRawSender appendCommand(String command, String label){
		messageQueue.add(new ChatResolve(ChatResolveType.COMMAND, command, label));
		return this;
	}
	
	/**
	 * Appends a message to the resolver
	 * <br>This adds the URL text
	 * 
	 * @param url to popup on click
	 * @param label to show
	 * 
	 * @return this for building
	 */
	public JSONRawSender appendURL(String url, String label){
		messageQueue.add(new ChatResolve(ChatResolveType.URL, url, label));
		return this;
	}
	
	/**
	 * Appends a message to the resolver
	 * <br>Appends an Hover Item.
	 * 
	 * @param message to append
	 * @param type to convert to
	 * 
	 * @return this for building
	 */
	public JSONRawSender append(ItemStack item, String customLabel){
		messageQueue.add(new ChatResolve(ChatResolveType.ITEM, item, customLabel));
		return this;
	}
	
	/**
	 * Appends a message to the resolver
	 * <br>Appends an Hover Item.
	 * 
	 * @param message to append
	 * @param type to convert to
	 * 
	 * @return this for building
	 */
	public JSONRawSender append(ItemStack item){
		messageQueue.add(new ChatResolve(ChatResolveType.ITEM, item, null));
		return this;
	}

	/**
	 * Appends a message to the resolver
	 * <br>Appends an Achievement.
	 * 
	 * @param message to append
	 * @param type to convert to
	 * 
	 * @return this for building
	 */
	public JSONRawSender appendAchievement(String achievement, String customLabel){
		messageQueue.add(new ChatResolve(ChatResolveType.ACHIEVEMENT, achievement, customLabel));
		return this;
	}
	
	/**
	 * Appends a message to the resolver
	 * <br>Appends an Popup Message.
	 * 
	 * @param popupMessage to append
	 * @param customLabel to show
	 * 
	 * @return this for building
	 */
	public JSONRawSender appendPopup(String popupMessage, String customLabel){
		messageQueue.add(new ChatResolve(ChatResolveType.POPUP, popupMessage, customLabel));
		return this;
	}

	/**
	 * Constructs the Raw Message.
	 * 
	 * @return the constructed Message.
	 */
	public String buildRawMessage(){
		String rawMessage = "{\"text\":\"\",\"extra\":[";
		
		Queue<ChatResolve> copy = new LinkedList<ChatResolve>(messageQueue);
		while(!copy.isEmpty()){
			ChatResolve resolver = copy.poll();
			switch(resolver.getType()){
				case UNFORMATED_TEXT : rawMessage += PlainTextJSONResolver.getRawFromPlainText((String)resolver.getToParse()); break;
				case ITEM : rawMessage += ItemJSONResolver.getItemRawHoverText((ItemStack) resolver.getToParse(), resolver.getLabel()); break;
				case ACHIEVEMENT: rawMessage += AchievementJSONResolver.resolveAchievement((String)resolver.getToParse(), resolver.getLabel()); break;
				case URL : rawMessage += URLJSONResolver.resolveURL((String)resolver.getToParse(), resolver.getLabel()); break;
				case COMMAND : rawMessage += CommandJSONResolver.resolveCommand((String) resolver.getToParse(), resolver.getLabel()); break;
				case POPUP : rawMessage += PopupJSONResolver.resolve((String) resolver.getToParse(), resolver.getLabel()); break;
				
				
				case RAW : rawMessage += (String)resolver.getToParse(); break;
			}
			
			if(!copy.isEmpty()){
				rawMessage += ",";
			}
		}
		
		
		rawMessage += "]}";
		return rawMessage;
	}
	
	/**
	 * Constructs the Raw Message.
	 * 
	 * @return the constructed Message.
	 */
	public JsonObject buildRawMessageFromJSON(){
		JsonObject object = new JsonObject();
		object.addProperty("text", "");
		JsonArray extra = new JsonArray();
		
		Queue<ChatResolve> copy = new LinkedList<ChatResolve>(messageQueue);
		while(!copy.isEmpty()){
			ChatResolve resolver = copy.poll();
			switch(resolver.getType()){
			case UNFORMATED_TEXT : extra.add(PlainTextJSONResolver.getRawFromPlainTextJSON((String)resolver.getToParse())); break;
			case ITEM : extra.add(ItemJSONResolver.getItemRawHoverTextJSON((ItemStack) resolver.getToParse(), resolver.getLabel())); break;
			case ACHIEVEMENT: extra.add(AchievementJSONResolver.resolveAchievementJSON((String)resolver.getToParse(), resolver.getLabel())); break;
			case URL : extra.add(URLJSONResolver.resolveURLJSON((String)resolver.getToParse(), resolver.getLabel())); break;
			case COMMAND : extra.add(CommandJSONResolver.resolveCommandJSON((String) resolver.getToParse(), resolver.getLabel())); break;
			case POPUP : extra.add(PopupJSONResolver.resolveJSON((String) resolver.getToParse(), resolver.getLabel())); break;
			
			
			case RAW : extra.add(new JsonParser().parse((String)resolver.getToParse())); break;
			}
		}
		
		object.add("extra", extra);
		
		return object;
	}
	
	/**
	 * Return the String representation of the JSON object
	 * 
	 * @return the String representation.
	 */
	public String buildRawMessageFromJSONAsString(){
		return buildRawMessageFromJSON().toString();
	}

	/**
	 * Sends the message to the Player/Players passed
	 * 
	 * @param player to send to
	 */
	public void sendToPlayers(Player... players){
		String message = buildRawMessage();
		
		for(Player player : players){
			SendRaw.sendPlayerRawMessage(player, message);
		}
	}

	/**
	 * Sends the message to the Players passed
	 * 
	 * @param player to send to
	 */
	public void sendToPlayers(Collection<Player> players){
		String message = buildRawMessage();

		for(Player player : players){
			SendRaw.sendPlayerRawMessage(player, message);
		}
	}
	
	/**
	 * Sends the message to the Player/Players passed
	 * 
	 * @param player to send to
	 */
	public void sendJSONToPlayers(Player... players){
		String message = buildRawMessageFromJSONAsString();
		
		for(Player player : players){
			SendRaw.sendPlayerRawMessage(player, message);
		}
	}
	
	/**
	 * Sends the message to the Players passed
	 * 
	 * @param player to send to
	 */
	public void sendJSONToPlayers(Collection<Player> players){
		String message = buildRawMessageFromJSONAsString();
		
		for(Player player : players){
			SendRaw.sendPlayerRawMessage(player, message);
		}
	}
}
