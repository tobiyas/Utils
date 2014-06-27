package de.tobiyas.util.chat.chatmenu.elements;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tobiyas.util.chat.components.ChatMessageObject;
import de.tobiyas.util.chat.components.TellRawChatMessage;

public abstract class ChatMenuElement {

	/**
	 * The displaying player
	 */
	protected final Player player;
	
	
	/**
	 * The label to use.
	 */
	protected final String label;
	

	/**
	 * The command to click.
	 */
	protected String clickCommand;
	
	
	/**
	 * The command to click for Suggestion.
	 */
	protected String clickCommandSuggestion;
	
	
	/**
	 * The popup to show.
	 */
	protected String popupLabel;
	
	
	/**
	 * The popup to show.
	 */
	protected ItemStack itemPopup;
	
	
	/**
	 * URL to click.
	 */
	protected String urlClick;
	
	/**
	 * The new View to open.
	 */
	protected ChatMenuView newViewToShow;
	
	
	
	/**
	 * Sets the Player to use.
	 * 
	 * @param player to use
	 */
	public ChatMenuElement(Player player, String label) {
		this.player = player;
		this.label = label;
	}
	

	/**
	 * @param clickCommand the clickCommand to set
	 */
	public ChatMenuElement setClickCommand(String clickCommand) {
		this.clickCommand = clickCommand;
		return this;
	}
	
	/**
	 * @param clickCommand the clickCommand to set
	 */
	public ChatMenuElement setClickCommandSuggestion(String clickCommandSuggestion) {
		this.clickCommandSuggestion = clickCommandSuggestion;
		return this;
	}


	/**
	 * @param popupLabel the popupLabel to set
	 */
	public ChatMenuElement setPopupLabel(String popupLabel) {
		this.popupLabel = popupLabel;
		return this;
	}


	/**
	 * @param itemPopup the itemPopup to set
	 */
	public ChatMenuElement setItemPopup(ItemStack itemPopup) {
		this.itemPopup = itemPopup;
		return this;
	}


	/**
	 * @param urlClick the urlClick to set
	 */
	public ChatMenuElement setUrlClick(String urlClick) {
		this.urlClick = urlClick;
		return this;
	}

	/**
	 * @return the newViewToShow
	 */
	public ChatMenuView getNewViewToShow() {
		return newViewToShow;
	}
	


	/**
	 * @param newViewToShow the newViewToShow to set
	 */
	public ChatMenuElement setNewViewToShow(ChatMenuView newViewToShow) {
		this.newViewToShow = newViewToShow;
		return this;
	}
	
	
	/**
	 * This is called short before the message is sent.
	 */
	protected abstract void rebuild();

	

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}



	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}



	/**
	 * @return the clickCommand
	 */
	public String getClickCommand() {
		return clickCommand;
	}



	/**
	 * @return the popupLabel
	 */
	public String getPopupLabel() {
		return popupLabel;
	}



	/**
	 * @return the itemPopup
	 */
	public ItemStack getItemPopup() {
		return itemPopup;
	}



	/**
	 * @return the urlClick
	 */
	public String getUrlClick() {
		return urlClick;
	}
	
	
	/**
	 * Builds a {@link ChatMessageObject} from this.
	 * 
	 * @return {@link ChatMessageObject}.
	 */
	protected TellRawChatMessage buildTellRawMessage(){
		rebuild();
		ChatMessageObject obj = new ChatMessageObject(label);
		
		if(itemPopup != null) obj.addItemHover(itemPopup);
		if(popupLabel != null) obj.addPopupHover(popupLabel);

		if(urlClick != null) obj.addURLClickable(urlClick);
		if(clickCommand != null) obj.addCommandClickable(clickCommand);
		if(clickCommandSuggestion != null) obj.addCommandSuggestionClickable(clickCommandSuggestion);
		if(newViewToShow != null) obj.addCommandClickable(newViewToShow.getCommandName());
		
		return new TellRawChatMessage().append(obj);
	}


	/**
	 * Posts a message to the player.
	 * 
	 * @param player to post to
	 */
	public void post() {
		buildTellRawMessage().sendToPlayers(player);
	}
	
	
}
