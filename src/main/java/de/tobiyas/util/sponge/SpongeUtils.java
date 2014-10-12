package de.tobiyas.util.sponge;

import org.bukkit.Bukkit;
import org.spongepowered.api.Game;

public class SpongeUtils {

	
	/**
	 * Checks if currently sponge is used as System.
	 * 
	 * @return
	 */
	public static boolean isSpongeUsed(){
		try{
			Class<?> clazz = Game.class;
			return clazz != null;
		}catch(Throwable exp){
			return false;
		}
	}
	
	/**
	 * Checks if currently sponge is used as System.
	 * 
	 * @return
	 */
	public static boolean isBukkitUsed(){
		try{
			Class<?> clazz = Bukkit.class;
			return clazz != null;
		}catch(Throwable exp){
			return false;
		}
	}
	
}
