package de.tobiyas.util.chat.components;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;

import de.tobiyas.util.chat.resolver.CommandJSONResolver;
import de.tobiyas.util.chat.resolver.CommandSuggestionJSONResolver;
import de.tobiyas.util.chat.resolver.ItemJSONResolver;
import de.tobiyas.util.chat.resolver.PlainTextJSONResolver;
import de.tobiyas.util.chat.resolver.PopupJSONResolver;
import de.tobiyas.util.chat.resolver.URLJSONResolver;

public class ChatMessageObject {

	/**
	 * The Current Object.
	 */
	private final JsonObject ownObject;
	
	
	public ChatMessageObject(String label) {
		this.ownObject = PlainTextJSONResolver.getRawFromPlainTextJSON(label);
	}
	
	/**
	 * adds an Item Hover to the Object.
	 * 
	 * @param item to add
 	 * @param label to set for.
	 */
	public ChatMessageObject addItemHover(ItemStack item){
		ItemJSONResolver.addItemHover(ownObject, item);
		return this;
	}
	
	
	/**
	 * adds an Popup Hover to the Object.
	 * 
	 * @param message to add
	 */
	public ChatMessageObject addPopupHover(String message){
		PopupJSONResolver.addPopupHover(ownObject, message);
		return this;
	}
	
	
	/**
	 * adds an URL Click to the Object.
	 * 
	 * @param url to set.
	 */
	public ChatMessageObject addURLClickable(String url){
		URLJSONResolver.addURLClickable(ownObject, url);
		return this;
	}
	
	
	/**
	 * adds an Command Click to the Object.
	 * 
	 * @param command to set.
	 */
	public ChatMessageObject addCommandClickable(String command){
		if(!command.startsWith("/")) command = "/" + command;
		CommandJSONResolver.addCommandClickable(ownObject, command);
		return this;
	}
	
	
	
	/**
	 * adds an Command Click to the Object.
	 * 
	 * @param command to set.
	 */
	public ChatMessageObject addCommandSuggestionClickable(String command){
		CommandSuggestionJSONResolver.addCommandSuggestionClickable(ownObject, command);
		return this;
	}
	
	
	/**
	 * Changes the Text passed.
	 * 
	 * @param newText to set.
	 */
	public ChatMessageObject changeText(String newText){
		ownObject.addProperty("text", newText);
		return this;
	}
	
	
	
	
	
	protected JsonObject getObject() {
		return ownObject;
	}
}
