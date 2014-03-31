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
package de.tobiyas.util.inventorymenu;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemGeneratorInterface extends InventoryView {	
	
	/**
	 * Simply generates an Item with the passed args.
	 * 
	 * @param material
	 * @param name
	 * @param lore
	 * @return
	 */
	protected ItemStack generateItem(Material material, String name, List<String> lore){
		return generateItem(material, (short) 0, name, lore);
	}
	
	/**
	 * Simply generates an Item with the passed args.
	 * 
	 * @param material
	 * @param name
	 * @param lore
	 * @return
	 */
	protected ItemStack generateItem(Material material, String name, String oneLineLore){
		return generateItem(material, (short)0, name, oneLineLore);
	}
	
	
	/**
	 * Simply generates an Item with the passed args.
	 * 
	 * @param material
	 * @param name
	 * @param lore
	 * @return
	 */
	protected ItemStack generateItem(Material material, short damageValue, String name, List<String> lore){
		ItemStack item = new ItemStack(material, 1, damageValue);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	/**
	 * Simply generates an Item with the passed args.
	 * 
	 * @param material
	 * @param name
	 * @param lore
	 * @return
	 */
	protected ItemStack generateItem(Material material, short damageValue, String name, String oneLineLore){
		List<String> lore = new LinkedList<String>();
		lore.add(oneLineLore);
		return generateItem(material, damageValue, name, lore);
	}

}
