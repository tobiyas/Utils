/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tobiyas.util.naming;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MCPrettyName {
	
	
	public static final String GE = "de_DE";
	public static final String EN = "en_US";
	

	
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
