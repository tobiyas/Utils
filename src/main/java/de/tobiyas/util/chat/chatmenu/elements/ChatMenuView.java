package de.tobiyas.util.chat.chatmenu.elements;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.tobiyas.util.UtilsUsingPlugin;
import de.tobiyas.util.chat.commands.CommandCallback;

public abstract class ChatMenuView implements CommandCallback {

	
	/**
	 * The Commandname to open this.
	 */
	protected final String commandName;
	
	/**
	 * The Plugin to use.
	 */
	protected final UtilsUsingPlugin plugin;
	
	
	/**
	 * The Player to use.
	 */
	protected final Player player;
	
	
	/**
	 * The Heading to use.
	 */
	protected String heading = ChatColor.YELLOW + "--------------------";
	
	/**
	 * The Footer to use.
	 */
	protected String footer = ChatColor.YELLOW + "--------------------";
	
	
	/**
	 * The List of elements.
	 */
	protected final List<ChatMenuElement> elements = new LinkedList<ChatMenuElement>();

	
	
	public ChatMenuView(Player player, UtilsUsingPlugin plugin) {
		this.player = player;
		this.plugin = plugin;
		
		this.commandName = plugin.getClickCommandManager().registerCommand(player, "", this);
	}
	
	
	
	//Header / Footer.

	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}
	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}
	/**
	 * @return the footer
	 */
	public String getFooter() {
		return footer;
	}
	/**
	 * @param footer the footer to set
	 */
	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	
	/**
	 * Adds an element to the Menu.
	 * 
	 * @param element to add
	 */
	public void addElement(ChatMenuElement element){
		this.elements.add(element);
	}
	
	
	/**
	 * Posts to the player.
	 * 
	 * @param player to post.
	 */
	public void postToPlayer(){
		rebuild();
		if(heading != null && !heading.isEmpty()) player.sendMessage(heading);
		
		for(int i = 0; i < elements.size(); i++){
			elements.get(i).post();
		}
		
		if(footer != null && !footer.isEmpty()) player.sendMessage(footer);
	}
	
	/**
	 * This is called short before the
	 * elements are posted.
	 */
	protected abstract void rebuild();
	

	/**
	 * @return the commandName
	 */
	public String getCommandName() {
		return commandName;
	}
	

	@Override
	public void playerPressedCallback(Player player, String hint) {
		postToPlayer();
	}
	
}
