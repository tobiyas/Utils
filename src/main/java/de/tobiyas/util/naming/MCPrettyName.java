package de.tobiyas.util.naming;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MCPrettyName {

	
	/**
	 * Returns a pretty name of the wanted Material.
	 * 
	 * @param mat to search for
	 * @param damageValue to search for
	 * 
	 * @return pretty name.
	 */
	public static String getPrettyName(Material mat, short damageValue, String language){
		try{
			ItemStack item = new ItemStack(mat);
			item.setDurability(damageValue);
			
			return NativeMCTranslation.getItemTranslation(item, language);
		}catch(Exception exp){
			return mat.name();
		}
	}

	
	/**
	 * Returns a pretty name of the wanted Item.
	 * <br>This is the Display Name if present or the Material name otherwise.
	 * 
	 * @param itemStack to search for.
	 * 
	 * @return pretty name.
	 */
	public static String getPrettyName(ItemStack itemStack, String language){
		if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()){
			return itemStack.getItemMeta().getDisplayName();
		}
		
		return getPrettyName(itemStack.getType(), itemStack.getDurability(), language);
	}

	/**
	 * Returns a pretty name of the wanted Item.
	 * <br>This is the Display Name if present or the Material name otherwise.
	 * 
	 * @param itemStack to search for.
	 * 
	 * @return pretty name.
	 */
	public static String getPrettyName(EntityType entityType, String language){
		return NativeMCTranslation.getEntityTypeName(entityType, language);
	}
}
