package de.tobiyas.util.vollotile;

import java.lang.reflect.Method;

import org.bukkit.World;
import org.bukkit.entity.Entity;

public class ReflectionsHelper {

	/**
	 * Gets the MC entity from the Bukkit entity.
	 * 
	 * @param entity to get from
	 * 
	 * @return the MC entity.
	 */
	public static Object getMCEntityFromBukkitEntity(Entity entity){
		try{
			Method getHandle = entity.getClass().getDeclaredMethod("getHandle");
			Object mcEntity = getHandle.invoke(entity);
			
			return mcEntity;
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * Returns the NMS world.
	 * 
	 * @param world from to fetch from.
	 * @return the world to get.
	 */
	public static Object getWorldFromBukkitWorld(World world){
		try{
			Method getHandle = world.getClass().getDeclaredMethod("getHandle");
			Object mcEntity = getHandle.invoke(world);
			
			return mcEntity;
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
	}

	
	
	
}
