package de.tobiyas.util.chat.chatmenu.elements;

import org.bukkit.entity.Player;

import de.tobiyas.util.chat.components.TellRawChatMessage;

public class EmptyLineChatMenuElement extends SimpleChatMenuElement {

	public EmptyLineChatMenuElement(Player player) {
		super(player, "");
	}

	
	protected TellRawChatMessage buildTellRawMessage(){
		return new TellRawChatMessage().addSimpleText("   ");
	}
}
