package de.tobiyas.util.chat.chatmenu.elements;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import de.tobiyas.util.chat.components.TellRawChatMessage;

public abstract class ChatMenuElementContainer extends ChatMenuElement {

	/**
	 * The List of all Elements.
	 */
	protected final List<ChatMenuElement> elements = new LinkedList<ChatMenuElement>();
	
	
	public ChatMenuElementContainer(Player player) {
		super(player, "");
	}

	
	@Override
	protected TellRawChatMessage buildTellRawMessage() {
		TellRawChatMessage message = new TellRawChatMessage();
		
		for(ChatMenuElement element : elements){
			message.append(element.buildTellRawMessage());
		}
		
		return message;
	}
	
}
