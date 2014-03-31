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
package de.tobiyas.util.chat.resolver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import de.tobiyas.util.naming.MCPrettyName;

public class ItemJSONResolver {

	/**
	 * Parses an ItemStack to an JSON format
	 * 
	 * @param itemToParse the item to parse
	 * 
	 * @return the JSON string.
	 */
	public static String resolveItemToJSON(String textBeforeUnformated, ItemStack itemToParse, String textAfterUnformated){	
		if(itemToParse == null || itemToParse.getType() == Material.AIR) return "";
		
		String itemFormat = getItemRawHoverText(itemToParse, null);
		String format = 
				"{\"text\":\"\",\"extra\":["
						+ ("".equals(textBeforeUnformated) ? "" : ("{text:\"" + textBeforeUnformated + "\"},"))
						+ itemFormat
						+ ("".equals(textAfterUnformated) ? "" : (",{text:\"" + textAfterUnformated + "\"}"))
				+ "]}";
		
		return format;
	}
	
	
	
	/**
	 * Returns an Item Hovering Format.
	 * 
	 * @param itemToParse to parse
	 * @return the RAW text for sending to client
	 */
	public static String getItemRawHoverText(ItemStack itemToParse, String label){
		if(itemToParse == null){
			return "";
		}
		
		
		
		String flatLore = "";
		//Read lore if present.
		if(itemToParse.hasItemMeta() && itemToParse.getItemMeta().hasLore()){
			List<String> lore = itemToParse.getItemMeta().hasLore() ? itemToParse.getItemMeta().getLore() : new LinkedList<String>();
			flatLore = ",Lore:["; 
			Iterator<String> loreIt = lore.iterator();  //Removed: .replace("\\", "\\\\")
			while(loreIt.hasNext()){flatLore += "\\\"" + loreIt.next().replace(":", "-").replace("\"", "\\\"") + "\\\""; if(loreIt.hasNext()) flatLore += ",";}
			flatLore += "]";
		}
		
		String name = MCPrettyName.getPrettyName(itemToParse, "de_DE");
		if(label == null || "".equals(label)) label = name;
		
		int typeID = itemToParse.getTypeId();
		short damage = itemToParse.getDurability();
		
		String enchantFormat = "";
		//Read enchants if present
		if(!itemToParse.getEnchantments().isEmpty()){
			enchantFormat = ",ench:[";
			Iterator<Entry<Enchantment,Integer>> entryIt = itemToParse.getEnchantments().entrySet().iterator();
			while(entryIt.hasNext()){
				Entry<Enchantment, Integer> entry = entryIt.next();
				enchantFormat += "{id:" + entry.getKey().getId() + ",lvl:" + entry.getValue() + "}";
			
				if(entryIt.hasNext()){
					enchantFormat += ",";
				}
			}
			
			enchantFormat += "]";
		}
		
		
		String storedEnchants = "";
		//StoredEnchantments for Enchantment books
		if(itemToParse.getType() == Material.ENCHANTED_BOOK && itemToParse.hasItemMeta()){
			EnchantmentStorageMeta enchantedMeta = (EnchantmentStorageMeta) itemToParse.getItemMeta();
			
			storedEnchants = ",StoredEnchantments:[";
			Iterator<Entry<Enchantment,Integer>> entryIt = enchantedMeta.getStoredEnchants().entrySet().iterator();
			while(entryIt.hasNext()){
				Entry<Enchantment, Integer> entry = entryIt.next();
				storedEnchants += "{id:" + entry.getKey().getId() + ",lvl:" + entry.getValue() + "}";
			
				if(entryIt.hasNext()){
					storedEnchants += ",";
				}
			}
			
			storedEnchants += "]";
		}
		
		
		String potionEffects = "";
		//parse Potion effects
		if(itemToParse.getType() == Material.POTION && itemToParse.hasItemMeta()){
			PotionMeta potionMeta = (PotionMeta) itemToParse.getItemMeta();
			
			potionEffects = ",CustomPotionEffects:[";
			Iterator<PotionEffect> entryIt = potionMeta.getCustomEffects().iterator();
			while(entryIt.hasNext()){
				PotionEffect effect = entryIt.next();
				potionEffects += "{id:" + effect.getType().getId() + ",Amplifier:" + effect.getAmplifier() 
						+ ",Duration:" + effect.getDuration() + ",Ambient:" + (effect.isAmbient() ? 1 : 0) + "}";
			
				if(entryIt.hasNext()){
					potionEffects += ",";
				}
			}
			
			potionEffects += "]";
		}
		
		String skullAdd = "";
		//Skull infos
		if(itemToParse.getType() == Material.SKULL_ITEM && itemToParse.hasItemMeta() && itemToParse.getDurability() == 3
				&& ((SkullMeta)itemToParse.getItemMeta()).hasOwner()){
			skullAdd = ",SkullOwner:\\\"" + ((SkullMeta)itemToParse.getItemMeta()).getOwner() + "\\\"";
		}
		
		//Book infos
		if(itemToParse.getType() == Material.WRITTEN_BOOK && itemToParse.hasItemMeta()){
			
		}
		
		
		String hoverFormat = 
				"{"
					+ "\"action\":\"show_item\",\"value\":\""
					+ "{"
						+ "id:" + typeID + ",Damage:" + damage + ",tag:"
						+ "{"
							+ "display:"
							+ "{"
								+ "Name:\\\"" + name + "\\\"" + flatLore
							+ "}"
							+ enchantFormat
							+ storedEnchants
							+ potionEffects
							+ skullAdd
						+ "}"
					+ "}\""
				+ "}";
		
		
		String colorName = "aqua";
		String itemFormat = "{\"text\":\"" + label + "\"," //TODO add colored braces
				+ "\"color\":\"" + colorName + "\","
				+ "\"hoverEvent\":" + hoverFormat + "}";
		
		return itemFormat;
	}

}
