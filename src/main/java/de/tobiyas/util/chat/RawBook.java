package de.tobiyas.util.chat;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import de.tobiyas.util.chat.components.TellRawChatMessage;

public class RawBook {

	
	/**
	 * This gives the Player passed the Book created by the TellRawMessages.
	 * 
	 * @param player to give
	 * @param pages to use.
	 */
	public static void editBookToPages(ItemStack book, List<TellRawChatMessage> pages){
		if(book == null || book.getType() != Material.WRITTEN_BOOK) return;
		
		try{
			BookMeta meta = (BookMeta) book.getItemMeta();
			Field field = null;
			
			Class<?> clazz = meta.getClass();
			while(clazz != null){
				for(Field declared : clazz.getDeclaredFields()){
					if(declared.getName().equals("pages")){
						field = declared;
						declared.setAccessible(true);
						break;
					}
				}
				
				clazz = clazz.getSuperclass();
				if(field != null) break;
			}
			
			if(field == null) return;
			@SuppressWarnings("unchecked")
			List<IChatBaseComponent> metaPages = (List<IChatBaseComponent>) field.get(meta);
			
			for(TellRawChatMessage page : pages){
				metaPages.add(IChatBaseComponent.ChatSerializer.a(page.buildBook()));
			}
			
			book.setItemMeta(meta);
		}catch(Throwable exp){
			exp.printStackTrace();
		}
	}
	
}
