package de.tobiyas.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;

public class CollectionUtils {

	/**
	 * The Random generator to use.
	 */
	private static final Random rand = new Random();
	
	/**
	 * Returns a random item in the collection.
	 * 
	 * @param collection to use
	 * @param rand to use.
	 * @return the random item.
	 */
	public static <T> T getRandomElement(Collection<T> collection, Random rand){
		if(collection == null || collection.isEmpty()) return null;
		if(collection.size() == 1) return collection.iterator().next();
		
		//Now really search:
		int num = rand.nextInt(collection.size());
		Iterator<T> it = collection.iterator();
		for(int i = 0; i < num-1; i++) it.next();
		
		return it.next();
	}
	
	
	/**
	 * Returns a random item in the collection.
	 * 
	 * @param collection to use
	 * @return the random item.
	 */
	public static <T> T getRandomElement(Collection<T> collection){
		return getRandomElement(collection, rand);
	}
	
	
	/**
	 * Translates ChatColors.
	 * @param list to translate
	 * @param symbol to use.
	 * @return the translated List.
	 */
	public static List<String> translateChatColors(List<String> list, char symbol){
		for(int i=0;i<list.size();i++) list.set(i, ChatColor.translateAlternateColorCodes(symbol, list.get(i)));
		return list;
	}
	
	/**
	 * Translates ChatColors.
	 * @param list to translate
	 * @return the translated list.
	 */
	public static List<String> translateChatColors(List<String> list){
		return translateChatColors(list, '&');
	}
	
	
	
	
	/**
	 * Converts to primitive Array.
	 * @param array to use.
	 * @return the primitive Array.
	 */
	public static int[] toPrimitive(Integer[] array){
		int[] array2 = new int[array.length];
		for(int i = 0; i < array.length; i++){
			array2[i] = array[i];
		}
		
		return array2;
	}
	
}
