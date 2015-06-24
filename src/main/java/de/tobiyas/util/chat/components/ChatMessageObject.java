package de.tobiyas.util.chat.components;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;

import de.tobiyas.util.chat.resolver.TextStyleJSONResolver;
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
	
	
	/**
	 * Adds an override Color.
	 * 
	 * @param color to set.
	 */
	public ChatMessageObject addChatColor(ChatColor color){
		TextStyleJSONResolver.addColor(ownObject, color);
		return this;
	}
	
	
	/**
	 * Adds a Strike Through
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject addStrikeThrough(){
		TextStyleJSONResolver.addStrikethrough(ownObject);
		return this;
	}
	
	/**
	 * Adds a Strike Through
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject addUnderlined(){
		TextStyleJSONResolver.addUnderlined(ownObject);
		return this;
	}
	
	/**
	 * Adds a Strike Through
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject addItalic(){
		TextStyleJSONResolver.addItalic(ownObject);
		return this;
	}
	
	/**
	 * Adds Bold
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject addBold(){
		TextStyleJSONResolver.addBold(ownObject);
		return this;
	}
	
	/**
	 * Add Magic
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject addMagic(){
		TextStyleJSONResolver.addMagic(ownObject);
		return this;
	}
	
	/**
	 * Remove a Strike Through
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject removeStrikeThrough(){
		TextStyleJSONResolver.removeStrikethrough(ownObject);
		return this;
	}
	
	/**
	 * Remove a Strike Through
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject removeUnderlined(){
		TextStyleJSONResolver.removeUnderlined(ownObject);
		return this;
	}
	
	/**
	 * Remove Strike Through
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject removeItalic(){
		TextStyleJSONResolver.removeItalic(ownObject);
		return this;
	}
	
	/**
	 * Remove Bold
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject removeBold(){
		TextStyleJSONResolver.removeBold(ownObject);
		return this;
	}
	
	/**
	 * Remove Magic
	 * 
	 * @return this for Building.
	 */
	public ChatMessageObject removeMagic(){
		TextStyleJSONResolver.removeMagic(ownObject);
		return this;
	}
	
	
	protected JsonObject getObject() {
		return ownObject;
	}

	/**
	 * Retrns the Label of the Value.
	 * 
	 * @return the label.
	 */
	public String getLabel() {
		if(!ownObject.has("text")) return "";
		return ownObject.get("text").getAsString();
	}
}
